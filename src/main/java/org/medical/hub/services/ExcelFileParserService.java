package org.medical.hub.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.format.CellGeneralFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.medical.hub.common.FileTemplate;
import org.medical.hub.dto.*;
import org.medical.hub.exception.ExcelFileHandlingException;
import org.medical.hub.request.ExcelFileActionRequest;
import org.medical.hub.validators.ExcelFormulaDataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcelFileParserService {

    @Autowired
    ExcelFormulaDataValidation formulaDataValidation;

    public List<ExcelSheetDto> parseExcel(byte[] content) {

        try (InputStream is = new ByteArrayInputStream(content);
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            List<ExcelSheetDto> sheets = new ArrayList<>();

            for (int l = 0; l < workbook.getNumberOfSheets(); l++) {
                XSSFSheet sheet = workbook.getSheetAt(l);
                if (sheet != null) {
                    List<List<String>> data = new ArrayList<>();
                    List<List<ExcelData>> excelData = new ArrayList<>();
                    List<ExcelColumnsDto> cols = new ArrayList<>();
                    XSSFRow row1 = sheet.getRow(0);

                    if (row1 != null) {
                        for (int k = 0; k < row1.getPhysicalNumberOfCells(); k++) {

                            XSSFCell cell = row1.getCell(k, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                            ExcelColumnsDto excelColumns = new ExcelColumnsDto();
                            if (cell != null) {
                                excelColumns.setColumnIndex( CellReference.convertNumToColString(cell.getColumnIndex()));
                                CellType cellType = cell.getCellType();
                                excelColumns.setTitle(cell.getStringCellValue());
                                excelColumns.setType(getCellType(cellType));
                                ExcelDataValidationDto dropdownValues = getDataValidationConstraint(cell);
                                if (dropdownValues != null && dropdownValues.getValues() != null) {
                                    excelColumns.setSource(Arrays.stream(dropdownValues.getValues()).collect(Collectors.toList()));
                                    excelColumns.setType("dropdown");
                                    excelColumns.setFormula1(dropdownValues.getFormula());
                                }
                            }
                            cols.add(excelColumns);
                        }
                    }

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        List<String> rowData = new ArrayList<>();
                        XSSFRow row = sheet.getRow(i);
                        List<ExcelData> excelDatas = new ArrayList<>();
                        if (row != null) {
                            int totalCol = row.getLastCellNum();

                            for (int j = 0; j < totalCol; j++) {
                                XSSFCell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                                String stringCellValue = "";
                                var excelCell = new ExcelData();
                                if (cell != null) {
                                    switch (cell.getCellType()) {
                                        case NUMERIC:
                                            String format = new CellGeneralFormatter().format(cell.getNumericCellValue());
                                            stringCellValue = String.valueOf(format);
                                            break;
                                        case STRING:
                                        default:
                                            stringCellValue = cell.getStringCellValue();
                                            break;
                                    }

                                    String colIndex = CellReference.convertNumToColString(cell.getColumnIndex());
                                    var rowIndex = String.valueOf(cell.getRowIndex());
                                    excelCell.setCellReference(colIndex+rowIndex);

                                }
                                rowData.add(stringCellValue);
                                excelCell.setData(stringCellValue);
                                excelDatas.add(excelCell);
                            }
                        }
                        excelData.add(excelDatas);
                        data.add(rowData);
                    }
                    sheets.add(new ExcelSheetDto(new ExcelDataDto(data, excelData, cols), sheet.getSheetName()));
                }
            }

            return sheets;
        } catch (Exception ex) {
            throw new ExcelFileHandlingException();
        }
    }

    public byte[] createExcelFile(Authentication authentication, byte[] oldExcelFile, ExcelFileActionRequest action) {

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            List<ExcelSheetDto> oldSheets = parseExcel(oldExcelFile);

            XSSFSheet sheet = workbook.createSheet(oldSheets.get(0).getSheetName());
            workbook.setSheetOrder(oldSheets.get(0).getSheetName(), 0);
            Gson gson = new Gson();

            Type colType = new TypeToken<List<ExcelColumnsDto>>() {
            }.getType();
            List<ExcelColumnsDto> columns = gson.fromJson(action.getColumns(), colType);

            Type listType = new TypeToken<List<List<String>>>() {
            }.getType();
            List<List<String>> data = gson.fromJson(action.getData(), listType);

            int rowCount = 1;
            int colCount = 0;
            Row columnRow = sheet.createRow(0);
            XSSFFont defaultFont = workbook.createFont();
            defaultFont.setBold(true);
            defaultFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle style = workbook.createCellStyle();
            style.setFont(defaultFont);
            for (ExcelColumnsDto col : columns) {
                Cell cell = columnRow.createCell(colCount);
                cell.setCellStyle(style);
                switch (col.getType()) {
                    case "numeric":
                        cell.setCellValue(Integer.parseInt(col.getTitle()));
                        break;
                    case "dropdown":
                        // add validators
                        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                        DataValidationConstraint constraint = null;
                        if (StringUtils.isNotBlank(col.getFormula1())) {
                            constraint = validationHelper.createFormulaListConstraint(col.getFormula1());
                        } else {
                            List<String> source = col.getSource();
                            String[] arr = source.toArray(new String[0]);
                            constraint = validationHelper.createExplicitListConstraint(arr);
                        }

                        CellRangeAddressList addressList = new CellRangeAddressList(0, 999, colCount, colCount);
                        DataValidation validation = validationHelper.createValidation(constraint, addressList);
                        validation.setSuppressDropDownArrow(true);
                        validation.setShowErrorBox(true);

                        sheet.addValidationData(validation);
                        cell.setCellValue(col.getTitle());

                        break;
                    case "string":
                    default:
                        cell.setCellValue(col.getTitle());
                        break;
                }
                ++colCount;
            }

            for (List<String> aBook : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                for (String field : aBook) {
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue(field);
                }
            }

            if (!oldSheets.isEmpty() && oldSheets.size() > 1) {
                writeDictionary(oldSheets.get(1), workbook);
            }

            Path tempFile = Files.createTempFile(authentication.getName(), FileTemplate.EXCEL_FILE_EXTENSION);
            FileOutputStream fs = new FileOutputStream(tempFile.toFile());
            workbook.write(fs);
            fs.close();

            Path filePath = Paths.get(tempFile.toUri());
            return Files.readAllBytes(filePath);

        } catch (Exception ex) {
            throw new ExcelFileHandlingException();
        }
    }

    private void writeDictionary(ExcelSheetDto dto, XSSFWorkbook workbook) {

        XSSFSheet sheet = workbook.createSheet(dto.getSheetName());
        workbook.setSheetOrder(dto.getSheetName(), 1);

        int rowCount = 1;
        int colCount = 0;
        Row columnRow = sheet.createRow(0);
        for (ExcelColumnsDto col : dto.getData().getColumns()) {
            Cell cell = columnRow.createCell(colCount);
            switch (col.getType() != null ? col.getType() : "string") {
                case "numeric":
                    cell.setCellValue(Integer.parseInt(col.getTitle()));
                    break;
                case "dropdown":
                    // add validators
                    List<String> source = col.getSource();
                    String[] arr = source.toArray(new String[0]);

                    DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                    DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(arr);
                    CellRangeAddressList addressList = new CellRangeAddressList(1, 999, colCount, colCount);

                    DataValidation validation = validationHelper.createValidation(constraint, addressList);
                    validation.setSuppressDropDownArrow(true);
                    validation.setShowErrorBox(true);

                    sheet.addValidationData(validation);
                    cell.setCellValue(col.getTitle());
                    break;
                case "string":
                default:
                    cell.setCellValue(col.getTitle());
                    break;
            }
            ++colCount;
        }


        for (List<String> aBook : dto.getData().getData()) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            for (String field : aBook) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue(field);
            }
        }
    }

    private String getCellType(CellType cellType) {

        switch (cellType) {
            case NUMERIC:
                return "numeric";
            case BOOLEAN:
                return "boolean";
            case STRING:
            default:
                return "string";
        }
    }

    private ExcelDataValidationDto getDataValidationConstraint(XSSFCell cell) {
        XSSFSheet sheet1 = cell.getSheet();

        List<XSSFDataValidation> dataValidations = sheet1.getDataValidations();

        for (DataValidation dataValidation : dataValidations) {
            CellRangeAddressList addressList = dataValidation.getRegions();
            CellRangeAddress[] addresses = addressList.getCellRangeAddresses();
            for (CellRangeAddress address : addresses) {
                if (address.isInRange(cell)) {
                    String[] explicitListValues = dataValidation.getValidationConstraint().getExplicitListValues();
                    if (explicitListValues != null) {
                        return new ExcelDataValidationDto("explicit", explicitListValues, "");
                    }

                    String formula1 = dataValidation.getValidationConstraint().getFormula1();
                    String[] values = formulaDataValidation.getValues(cell.getSheet().getWorkbook(), formula1);
                    return new ExcelDataValidationDto("formula", values, formula1);
                }
            }
        }

        var formulaValidation = formulaDataValidation.getFormulaDataValidation(cell.getSheet().getWorkbook(), cell);
        for(FormulaDataValidation fbValidation: formulaValidation){
            if(fbValidation.getCellAddresses().isInRange(cell)){
                return new ExcelDataValidationDto("formula", fbValidation.getValues(), fbValidation.getFormula());
            }
        }

        return null;
    }
}
