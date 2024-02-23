package org.medical.hub.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.medical.hub.controller.UploadMinimalExcelController;
import org.medical.hub.dto.ExcelColumnsDto;
import org.medical.hub.dto.ExcelData;
import org.medical.hub.dto.ExcelDataDto;
import org.medical.hub.dto.ExcelSheetDto;
import org.medical.hub.exception.ExcelFileNotFoundException;
import org.medical.hub.exception.InternalServerException;
import org.medical.hub.models.*;
import org.medical.hub.repository.ExcelFileRepository;
import org.medical.hub.repository.RuleRepository;
import org.medical.hub.request.ExcelDataValidationRequest;
import org.medical.hub.request.ExcelFileActionRequest;
import org.medical.hub.request.FileUploadRequest;
import org.medical.hub.utils.RuleHelper;
import org.medical.hub.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ExcelFileService {

    private final ExcelFileRepository fileRepository;
    private final RuleRepository ruleRepository;
    private final ExcelFileParserService excelParser;
    private final LoggedinUser loggedinUser;

    @Autowired
    public ExcelFileService(ExcelFileRepository repository,
                            RuleRepository ruleRepository,
                            ExcelFileParserService excelFileParserService,
                            LoggedinUser loggedinUser) {
        this.fileRepository = repository;
        this.ruleRepository = ruleRepository;
        this.excelParser = excelFileParserService;
        this.loggedinUser = loggedinUser;
    }

    /**
     * Save the details of Excel file uploaded
     *
     * @param uploadModel
     */
    public void save(FileUploadRequest uploadModel) {
        try {

            ExcelFile excelFile = new ExcelFile();
            excelFile.setStatus(Status.IN_PROGRESS);
            excelFile.setTypeOf(uploadModel.getFileType());
            excelFile.setFileName(FilenameUtils.getBaseName(uploadModel.getFile().getOriginalFilename()));
            excelFile.setFileId(String.valueOf(System.currentTimeMillis()));
            excelFile.setContent(uploadModel.getFile().getBytes());
            excelFile.setCreatedAt(System.currentTimeMillis());
            excelFile.setUpdatedAt(System.currentTimeMillis());

            User user = this.loggedinUser.currentLoginUser();
            excelFile.setUser(user);

            this.fileRepository.save(excelFile);
        } catch (Exception ex) {
            throw new InternalServerException();
        }
    }

    /**
     * Get the list of Excel files
     *
     * @param page page number
     * @param size per page
     * @return Excel File
     */
    public Page<ExcelFile> getAll(Integer page, Integer size) {

        User user = this.loggedinUser.currentLoginUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return this.fileRepository.findByUserId(user.getId(), pageable);
    }

    /**
     * Get the content of Excel file(Only first sheet content)
     *
     * @param id Excel file id
     * @return First sheet content
     */
    public ExcelDataDto getExcelDataDetails(Long id) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ExcelFile byId = getById(id);
        // get rules
        List<Rule> rules = ruleRepository.findByFileType(byId.getTypeOf());

        List<ExcelSheetDto> excelSheets = this.excelParser.parseExcel(byId.getContent());
        ExcelDataDto data = excelSheets.get(0).getData();
        var finalData = applyColumnRule(rules, data);

        return new ExcelDataDto(data.getData(), finalData, data.getColumns());
    }

    /**
     * Either overwrite or create new file based on button action value
     *
     * @param id     Excel file id
     * @param action Action request(overwrite or create new)
     */
    public void overwrite(Long id, ExcelFileActionRequest action) {

        ExcelFile byId = getById(id);

//        try {
        byte[] file = createExcelFile(id, action);
        if (action.getButtonAction().equals("2")) {
            overwriteFile(byId, file);
        } else if (action.getButtonAction().equals("1")) {
            saveNewFile(action.getFileName(), byId, file);
        }
//        } catch (Exception ex) {
//            throw new ExcelFileHandlingException();
//        }
    }

    /**
     * Create new Excel file
     *
     * @param id     excel file id
     * @param action Save changes or Overwrite
     * @return Excel file
     */
    public byte[] createExcelFile(Long id, ExcelFileActionRequest action) {
        ExcelFile byId = getById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return excelParser.createExcelFile(authentication, byId.getContent(), action);
    }

    /**
     * Get the Excel file details.
     *
     * @param id excel file id
     * @return ExcelFile details
     */
    public ExcelFile getById(Long id) {
        User user = this.loggedinUser.currentLoginUser();

        Optional<ExcelFile> byId = fileRepository.findByIdAndUserId(id, user.getId());
        if (byId.isEmpty()) {
            throw new ExcelFileNotFoundException();
        }

        return byId.get();
    }

    /**
     * Delete Excel file permanently.
     *
     * @param id excel file id
     * @return success status
     */
    public void deleteExcelFile(Long id) {
        ExcelFile byId = getById(id);
        fileRepository.delete(byId);
    }

    private void overwriteFile(ExcelFile byId, byte[] content) {
        byId.setStatus(Status.IN_PROGRESS);
        byId.setTypeOf(byId.getTypeOf());
        byId.setFileName(byId.getFileName());
        byId.setFileId(String.valueOf(System.currentTimeMillis()));
        byId.setContent(content);
        byId.setUpdatedAt(System.currentTimeMillis());
        this.fileRepository.save(byId);
    }

    private void saveNewFile(String fileName, ExcelFile byId, byte[] content) {
        ExcelFile excelFile = new ExcelFile();
        excelFile.setStatus(Status.IN_PROGRESS);
        excelFile.setTypeOf(byId.getTypeOf());
        excelFile.setFileName(fileName);
        excelFile.setFileId(String.valueOf(System.currentTimeMillis()));
        excelFile.setContent(content);
        excelFile.setCreatedAt(System.currentTimeMillis());

        User user = this.loggedinUser.currentLoginUser();
        excelFile.setUser(user);
        this.fileRepository.save(excelFile);
    }

    public Map<String, String> validateExcelData(Long fileId, ExcelDataValidationRequest request) {
        ExcelFile byId = this.getById(fileId);

        List<Rule> rules = ruleRepository.findByFileType(byId.getTypeOf());
        List<ExcelSheetDto> excelSheets = this.excelParser.parseExcel(byId.getContent());
        ExcelDataDto excelDataDto = excelSheets.get(0).getData();
        List<ExcelColumnsDto> columns = excelDataDto.getColumns();

        return applyRuleForData(rules, columns, request);
    }

    private Map<String, String> applyRuleForData(List<Rule> rules,
                                                 List<ExcelColumnsDto> columns,
                                                 ExcelDataValidationRequest request) {
        Map<String, String> response = new HashMap<>();
        List<Rule> columnRules = rules.stream()
                .filter(rule -> rule.getRuleType() == RuleType.COLUMN)
                .map(this::mapRule)
                .collect(Collectors.toList());
        response.put("isValid", "true");
        response.put("message", "");
        if (columnRules.isEmpty()) {
            return response;
        }

        Pattern compile = Pattern.compile("^([A-Z]+)([0-9]+)");
        Matcher match = compile.matcher(request.getCellReference());
        if (!match.find()) {
            return response;
        }

        String columnIndex = match.group(1);

        // get the column from cell reference
        String columnName = "";
        for (ExcelColumnsDto column : columns) {
            if (column.getColumnIndex().equals(columnIndex)) {
                columnName = column.getTitle();
                break;
            }
        }

        if (StringUtils.isBlank(columnName)) {
            return response;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> rowData = gson.fromJson(request.getRowData(), listType);

        for (Rule rule : rules) {
            if (rule.getFirstColumn().equals(columnName)) {
                if (rule.getCompareWith() == CompareBetween.COL_WITH_PARAMETER) { // compare value with parameter
                    var isValid = expression(rule, request.getData(), rule.getParameter());
                    if (isValid) {
                        response.put("isValid", "false");
                        response.put("message", rule.getErrorMessage());
                        break;
                    }
                } else if (rule.getCompareWith() == CompareBetween.BETWEEN_COLUMNS) {
                    int secondColumnIndex = secondColumnIndex(columns, rule.getSecondColumn());
                    if (rowData.size() < secondColumnIndex) {
                        continue;
                    }

                    var compareWith = rowData.get(secondColumnIndex);
                    var isValid = expression(rule, request.getData(), compareWith);
                    if (isValid) {
                        response.put("isValid", "false");
                        response.put("message", rule.getErrorMessage());
                    }
                }
            }
        }
        return response;

    }

    private Rule mapRule(Rule rule) {
        String firstColumn = StringHelper.splitColName(rule.getFirstColumn(), "-");
        String secondColumn = StringHelper.splitColName(rule.getSecondColumn(), "-");

        rule.setFirstColumn(firstColumn);
        rule.setSecondColumn(secondColumn);

        return rule;
    }

    private List<List<ExcelData>> applyColumnRule(List<Rule> rules, ExcelDataDto excelData) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        List<ExcelColumnsDto> columns = excelData.getColumns();
        var values = excelData.getValues();

        List<Rule> columnRules = rules.stream()
                .filter(rule -> rule.getRuleType() == RuleType.COLUMN)
                .map(this::mapRule)
                .collect(Collectors.toList());

        List<Rule> rowRules = rules.stream()
                .filter(rule -> rule.getRuleType() == RuleType.ROW)
                .map(this::mapRule)
                .collect(Collectors.toList());

        if (columnRules.isEmpty() && rowRules.isEmpty()) {
            return values;
        }

        List<List<ExcelData>> finalData = new ArrayList<>();

        for (int row = 0; row < values.size(); row++) {
            var excelRow = values.get(row);
            if (excelRow.isEmpty()) {
                finalData.add(excelRow);
                continue;
            }
            List<ExcelData> aExcelData = new ArrayList<>();
            for (int cell = 0; cell < excelRow.size(); cell++) {
                var cellValue = excelRow.get(cell);

                var columnName = columns.get(cell);
                if (columnName == null) {
                    aExcelData.add(cellValue);
                    continue;
                }

                boolean isRuleApplied = false;
                for (Rule rule : columnRules) {
                    if (columnName.getTitle().equals(rule.getFirstColumn())) {

                        String ruleErrorMessage = rule.getErrorMessage();
                        if (rule.getCompareWith() == CompareBetween.COL_WITH_PARAMETER) { // compare value with parameter
                            var isValid = expression(rule, cellValue.getData(), rule.getParameter());
                            if (isValid) {
                                String style = cellValue.getStyle();
                                style = (style != null) ? style + "background-color:yellow" : "background-color:yellow";
                                cellValue.setStyle(style);
                                if (StringUtils.isNotBlank(ruleErrorMessage))
                                    cellValue.setMessage(ruleErrorMessage);
                            }
                            cellValue.setValid(isValid);
                            aExcelData.add(cellValue);

                        } else {
                            int secondColumnIndex = secondColumnIndex(columns, rule.getSecondColumn());
                            var secondColumnRow = excelRow.get(secondColumnIndex);
                            var compareWith = secondColumnRow.getData();
                            var isValid = expression(rule, cellValue.getData(), compareWith);
                            if (isValid) {
                                String style = cellValue.getStyle();
                                style = (style != null) ? style + "background-color:yellow" : "background-color:yellow";
                                cellValue.setStyle(style);
                                if (StringUtils.isNotBlank(ruleErrorMessage))
                                    cellValue.setMessage(ruleErrorMessage);
                            }
                            cellValue.setValid(isValid);
                            aExcelData.add(cellValue);
                        }
                        isRuleApplied = true;
                    }
                }

                for (Rule rule : rowRules) {
                    String method = rule.getMethodName();
                    if (StringUtils.isNotBlank(method)) {
                        String message = "";
                        if (!method.contains(".")) {
                            boolean methodExists = RuleHelper.isMethodExists(ExcelFileService.class, method);
                            if (methodExists) {
                                Method declaredMethod = this.getClass().getDeclaredMethod(method, Map.class);
                                Object invoke = declaredMethod.invoke(this, excelData.getData());

                                if (invoke instanceof Boolean) {
                                    message = rule.getErrorMessage();
                                } else if (invoke instanceof String) {
                                    message = (String) invoke;
                                } else if (invoke instanceof Integer) {
                                    Integer intVal = (Integer) invoke;
                                    if (intVal < 0) {
                                        message = rule.getNegativeMessage();
                                    }
                                }
                            }
                        } else {
                            String[] methodSplit = method.split("\\.");
                            String methodName = methodSplit[methodSplit.length - 1];

                            String className = "";
                            for (int i = 0; i < methodSplit.length - 1; i++) {
                                className += methodSplit[i] + ".";
                            }
                            String finalClassName = StringUtils.stripEnd(className, ".");
                            Class validationClass = Class.forName(finalClassName);

                            boolean methodExists = RuleHelper.isMethodExists(validationClass, methodName);
                            if (methodExists) {
                                Method declaredMethod = validationClass.getDeclaredMethod(methodName, String.class);
                                Object o = validationClass.newInstance();
                                Object invoke = declaredMethod.invoke(o, excelData.getData());

                                if (invoke instanceof Boolean) {
                                    message = rule.getErrorMessage();
                                } else if (invoke instanceof String) {
                                    message = (String) invoke;
                                } else if (invoke instanceof Integer) {
                                    Integer intVal = (Integer) invoke;
                                    if (intVal < 0) {
                                        message = rule.getNegativeMessage();
                                    }
                                }
                            }
                        }
                    }
                }

                if (!isRuleApplied) {
                    aExcelData.add(cellValue);
                }
            }
            finalData.add(aExcelData);
        }

        return finalData;
    }

    /**
     * Get the second column index
     *
     * @param columns      excel file columns
     * @param secondColumn rule second value
     * @return column idex
     */
    private int secondColumnIndex(List<ExcelColumnsDto> columns, String secondColumn) {
        int i = -1;
        for (int j = 0; j < columns.size(); j++) {
            ExcelColumnsDto col = columns.get(j);
            if (col.getTitle().equals(secondColumn)) {
                i = j;
            }
        }

        return i;
    }

    private boolean expression(Rule rule, String firstValue, String secondValue) {
        if (rule.getExpression() == null) {
            return true;
        }
        switch (rule.getExpression()) {
            case EQUAL:
                return firstValue.equals(secondValue);
            case NOT_EQUAL_TO:
                return !firstValue.equals(secondValue);
            default:
                return expressionCompare(rule, firstValue, secondValue);
        }
    }

    private boolean expressionCompare(Rule rule, String firstValue, String secondValue) {
        int first = Integer.parseInt(firstValue);
        int second = Integer.parseInt(secondValue);
        switch (rule.getExpression()) {
            case LESS_THAN:
                return first < second;
            case LESS_THAN_EQUAL:
                return first <= second;
            case GREATER_THAN:
                return first > second;
            case GREATER_THAN_EQUAL:
                return first >= second;
            default:
                return false;
        }
    }

    private boolean applyRowRule(String parameterValue,
                                 List<String> columns,
                                 List<String> rowData) {
        String separator = "$";
        if (!parameterValue.contains(separator))
            return true;

        String[] nameSplit = parameterValue.split(separator);
        if (nameSplit.length != 2) {
            return true;
        }

        String className = nameSplit[0];
        if (!className.contains(".")) {
            className = "org.medical.hub.rowrule." + className;
        }

        String methodName = nameSplit[1];
        Map<String, String> param = mapColToData(columns, rowData);
        try {
            Class<?> rowClass = Class.forName(className);
            Object obj = rowClass.newInstance();
            Method method = obj.getClass().getMethod(methodName, Map.class);
            method.invoke(obj, param);

            return true;
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            return true;
        }

    }

    private Map<String, String> mapColToData(List<String> columns,
                                             List<String> rowData) {

        Map<String, String> colMap = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            var key = columns.get(i);
            var value = rowData.get(i);

            colMap.put(key, value);
        }

        return colMap;
    }

}
