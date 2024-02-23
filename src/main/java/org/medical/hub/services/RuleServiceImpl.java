package org.medical.hub.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.medical.hub.common.FileTemplate;
import org.medical.hub.datatable.*;
import org.medical.hub.dto.RuleDataTable;
import org.medical.hub.exception.ExcelFileHandlingException;
import org.medical.hub.exception.InternalServerException;
import org.medical.hub.exception.RuleNotFoundException;
import org.medical.hub.models.*;
import org.medical.hub.repository.RuleRepository;
import org.medical.hub.repository.SearchCriteria;
import org.medical.hub.repository.SearchOperation;
import org.medical.hub.repository.eCRFSpecification;
import org.medical.hub.request.CreateRuleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    private Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleRepository ruleRepository;
    private final LoggedinUser loggedinUser;

    @Autowired
    public RuleServiceImpl(RuleRepository ruleRepository, LoggedinUser loggedinUser) {
        this.ruleRepository = ruleRepository;
        this.loggedinUser = loggedinUser;
    }

    @Override
    public void save(CreateRuleRequest request) {

        Rule rule = new Rule();
        rule.setName(request.getName());
        rule.setRuleType(RuleType.fromString(request.getRuleType()));
        rule.setFileType(getFileType(request.getFileType()));
        rule.setFirstColumn(request.getFirstColumn());
        rule.setSecondColumn(request.getSecondColumn());

        if (request.getCompareBetween() != 0) {
            rule.setCompareWith(CompareBetween.fromString(request.getCompareBetween()));
        }

        if (request.getExpression() != null && request.getExpression() != 0) {
            rule.setExpression(Expression.fromString(request.getExpression()));
        }

        rule.setParameter(request.getParameterValue());
        rule.setMethodName(request.getMethodName());
        rule.setErrorMessage(request.getErrorMessage());

        if (request.getCompareBetween() == CompareBetween.VALIDATE_COLUMN.getValue())
            rule.setMethodName(request.getColumnMethodName());

        rule.setVariableName(request.getVariableName());
        rule.setNegativeMessage(request.getNegativeMessage());
        rule.setUndefinedMessage(request.getUndefinedMessage());
        rule.setNegativeValue(request.getNegativeValue());
        rule.setPositiveValue(request.getPositiveValue());
        rule.setUndefinedValue(request.getUndefinedValue());
        rule.setParameters(request.getParameters());

        rule.setCreatedAt(System.currentTimeMillis());
        rule.setUpdatedAt(System.currentTimeMillis());

        User user = this.loggedinUser.currentLoginUser();
        rule.setUser(user);

        ruleRepository.save(rule);
    }

    private FileType getFileType(int fileType) {
        if (fileType == 0) {
            return FileType.CLINICAL_DATA;
        }

        return FileType.SAMPLE_MANIFEST;
    }

    @Override
    public void update(Long ruleId, CreateRuleRequest request) {

        var rule = getRuleById(ruleId);

        rule.setName(request.getName());
        rule.setRuleType(RuleType.fromString(request.getRuleType()));
        rule.setFileType(getFileType(request.getFileType()));
        rule.setFirstColumn(request.getFirstColumn());
        rule.setSecondColumn(request.getSecondColumn());

        if (request.getCompareBetween() != 0) {
            rule.setCompareWith(CompareBetween.fromString(request.getCompareBetween()));
        }

        if (request.getExpression() != null && request.getExpression() != 0) {
            rule.setExpression(Expression.fromString(request.getExpression()));
        }

        rule.setParameter(request.getParameterValue());
        rule.setMethodName(request.getMethodName());
        rule.setErrorMessage(request.getErrorMessage());

        if (request.getCompareBetween() == CompareBetween.VALIDATE_COLUMN.getValue())
            rule.setMethodName(request.getColumnMethodName());

        rule.setVariableName(request.getVariableName());
        rule.setNegativeMessage(request.getNegativeMessage());
        rule.setUndefinedMessage(request.getUndefinedMessage());
        rule.setNegativeValue(request.getNegativeValue());
        rule.setPositiveValue(request.getPositiveValue());
        rule.setUndefinedValue(request.getUndefinedValue());
        rule.setParameters(request.getParameters());

        rule.setUpdatedAt(System.currentTimeMillis());

        ruleRepository.save(rule);
    }

    @Override
    public Rule getRuleById(Long id) {
        return ruleRepository.findById(id)
                .stream().findFirst()
                .orElseThrow(RuleNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        ruleRepository.delete(getRuleById(id));
    }

    @Override
    public List<String> getColumnsFromFileType(FileType fileType) {
        logger.trace("Getting the columns of file based on file type: {}", fileType);
        String fileLocation = getTemplateFile(fileType);

        try (InputStream is = new FileInputStream(fileLocation);
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {

            return getColumName(workbook.getSheetAt(0));

        } catch (Exception ex) {
            logger.error("Error while parsing the excel file columns.", ex);
            throw new ExcelFileHandlingException();
        }
    }

    private String getFileName(FileType fileType) {
        String fileName;
        switch (fileType) {
            case SAMPLE_MANIFEST:
                fileName = FileTemplate.SAMPLE_MANIFEST_FILE_NAME;
                break;
            case CLINICAL_DATA:
                fileName = FileTemplate.CLINICAL_DATA_FILE_NAME;
                break;
            case FACTOR_PRIOR_TO_PD:
                fileName = FileTemplate.FACTOR_PRIOR_TO_PD_FILE_NAME;
                break;
            default:
                throw new InternalServerException();
        }

        return fileName + FileTemplate.EXCEL_FILE_EXTENSION;
    }

    private String getTemplateFile(FileType fileType) {
        String fileName = getFileName(fileType);

        URL aStatic = getClass().getClassLoader().getResource(FileTemplate.FILE_TEMPLATE_DIR);
        if (aStatic == null) {
            logger.debug("File not found on location.");
            throw new InternalServerException();
        }
        return aStatic.getPath() + File.separator + fileName;
    }

    private List<String>  getColumName(XSSFSheet sheet) {
        List<String> excelColumns = new ArrayList<>();
        if (sheet != null) {
            XSSFRow row = sheet.getRow(0);
            if (row != null) {
                for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
                    XSSFCell cell = row.getCell(k, Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
                    if (cell != null) {
                        String columnName = cell.getStringCellValue() + "-" + CellReference.convertNumToColString(cell.getColumnIndex());
                        excelColumns.add(columnName);
                    }
                }
            }
        }

        return excelColumns;
    }

    @Override
    public DataTableResponse<RuleDataTable> getRules(DataTableRequest dataTable) {
        eCRFSpecification specification = new eCRFSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("patID", value, SearchOperation.LIKE);
            specification.add(criteria);
        }

        DataTableOrder order = dataTable.getOrder().stream().findFirst().orElse(null);

        Pageable sortedByName = PageRequest.of(dataTable.getStart(), dataTable.getLength());
        if (order != null) {
            DataTableColumnSpecs dataTableColumnSpecs = dataTable.getColumns().get(order.getColumn());
            if (dataTableColumnSpecs != null) {
                String data = dataTableColumnSpecs.getData();
                if (data.equals("checkbox") || data.equals("action")) {
                    data = "id";
                }
                Sort.Direction dir = (order.getDir().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort by = Sort.by(new Sort.Order(dir, data));
                int pageNumber = DataTableUtils.getPageNumber(dataTable);
                sortedByName = PageRequest.of(pageNumber, dataTable.getLength(), by);
            }
        }

        Page<Rule> all = ruleRepository.findAll(specification, sortedByName);
        List<RuleDataTable> dataTablesData = mapRuleData(all.getContent(), dataTable);
        long totalRecord = ruleRepository.count();
        long filteredRecord = ruleRepository.count(specification);
        DataTableResponse<RuleDataTable> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    @Override
    public List<Rule> findByFileTypeAndCompareBetween(FileType fileType, CompareBetween compareBetween) {

        return this.ruleRepository.findByFileTypeAndCompareWith(fileType, compareBetween);
    }

    private List<RuleDataTable> mapRuleData(List<Rule> rules, DataTableRequest dataTableRequest) {
        List<RuleDataTable> dataTablesData = new ArrayList<>();
        int i = 1;
        for (Rule rule : rules) {
            getSN(dataTableRequest, i);
            RuleDataTable ruleDataTable = new RuleDataTable();
            ruleDataTable.setId(rule.getId());
            ruleDataTable.setSn(getSN(dataTableRequest, i));
            ruleDataTable.setFileType(rule.getFileType().getValue());
            ruleDataTable.setRuleType(rule.getRuleType().getName());
            ruleDataTable.setName(rule.getName());

            String fullName = rule.getUser().getFirstName() + " " + rule.getUser().getSurname();
            ruleDataTable.setCreatedBy(fullName);

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rule.getCreatedAt());
            ruleDataTable.setCreatedAt(format);
            ruleDataTable.setAction(String.valueOf(rule.getId()));
            dataTablesData.add(ruleDataTable);

            i++;
        }

        return dataTablesData;
    }

    private Integer getSN(DataTableRequest dataTable, int i) {
        return dataTable.getStart() + i;
    }
}
