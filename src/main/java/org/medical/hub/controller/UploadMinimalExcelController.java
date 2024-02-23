package org.medical.hub.controller;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.annotation.ExcelColumn;
import org.medical.hub.common.FillingStatus;
import org.medical.hub.dto.ExcelColumnsDto;
import org.medical.hub.dto.ExcelData;
import org.medical.hub.dto.ExcelDataDto;
import org.medical.hub.dto.ExcelSheetDto;
import org.medical.hub.models.*;
import org.medical.hub.repository.*;
import org.medical.hub.request.GenerateECRFRequest;
import org.medical.hub.services.ExcelFileParserService;
import org.medical.hub.services.ExcelFileService;
import org.medical.hub.services.RuleService;
import org.medical.hub.utils.RuleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UploadMinimalExcelController {

    private static final String N_G = "-99";

    private final ModelAndView modelAndView = new ModelAndView("excel/list");

    @Autowired
    private ExcelFileRepository fileRepository;

    @Autowired
    private eCRF1Repository medical2Ecrf1DAO;
    @Autowired
    private eCRF2Repository medical2Ecrf2DAO;
    @Autowired
    private eCRF3Repository medical2Ecrf3DAO;
    @Autowired
    private eCRF4Repository medical2Ecrf4DAO;
    @Autowired
    private eCRF5Repository medical2Ecrf5DAO;
    @Autowired
    private eCRF6Repository medical2Ecrf6DAO;
    @Autowired
    private eCRF7Repository medical2Ecrf7DAO;
    @Autowired
    private eCRF8Repository medical2Ecrf8DAO;
    @Autowired
    private eCRF9Repository medical2Ecrf9DAO;
    @Autowired
    private eCRF10Repository medical2Ecrf10DAO;
    @Autowired
    private eCRF20Repository medical2Ecrf20DAO;
    @Autowired
    private eCRF30Repository medical2Ecrf30DAO;

    @Autowired
    UserRepository userRepository;

    private final ExcelFileService fileService;
    private final ExcelFileParserService parserService;
    private final RuleService ruleService;

    @Autowired
    public UploadMinimalExcelController(ExcelFileService fileService,
                                        ExcelFileParserService parserService,
                                        RuleService ruleService) {
        this.fileService = fileService;
        this.parserService = parserService;
        this.ruleService = ruleService;
    }


    @Transactional
    @PostMapping("/uploadMinimalExcel/{file_id}/generate-eCRF")
    public ModelAndView uploadMinimalExcel(@PathVariable("file_id") Long fileId, GenerateECRFRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        //check if file is empty

        // validate the data based on rule
        List<String> errorsToShow = new ArrayList<>();
        ExcelDataDto excelDataDetails = fileService.getExcelDataDetails(fileId);
        List<List<ExcelData>> values1 = excelDataDetails.getValues();
//        for (List<ExcelData> data: values1){
//            for(ExcelData excelData: data){
//                if(!excelData.isValid()){
//                    errorsToShow.add(excelData.getMessage());
//                }
//            }
//        }

        if(!errorsToShow.isEmpty()){
            modelAndView.addObject("errorsToShow", errorsToShow);
            return modelAndView;
        }

        List<String> infoToShow = new ArrayList<>();
        ExcelFile file = fileService.getById(fileId);
        if (file.getStatus() == Status.SUBMITTED) {
            ModelAndView modelAndView = new ModelAndView("redirect:/excel-file");
            RedirectAttributes ra = new RedirectAttributesModelMap();
            ra.addFlashAttribute("error_message", "ECRF is already generated.");
            return modelAndView;
        }

        var assignTo = userRepository.findById(request.getAssignTo());
        if (assignTo.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/excel-file");
            RedirectAttributes ra = new RedirectAttributesModelMap();
            ra.addFlashAttribute("error_message", "Assign to user not found.");
            return modelAndView;
        }
        var user = assignTo.get();

        var columnRules = ruleService.findByFileTypeAndCompareBetween(file.getTypeOf(), CompareBetween.VALIDATE_COLUMN);

        //read excel file
        List<Map<String, ExcelData>> list1 = new ArrayList<>();
        //array to store line numbers
        List<Integer> lineNumbers = new ArrayList<>();
        if (file.getTypeOf() == FileType.SAMPLE_MANIFEST || file.getTypeOf() == FileType.CLINICAL_DATA || file.getTypeOf() == FileType.FACTOR_PRIOR_TO_PD) {
            List<ExcelSheetDto> sheets = parserService.parseExcel(file.getContent());
            if (!sheets.isEmpty()) {
                ExcelSheetDto excelSheetDto = sheets.get(0);
                List<ExcelColumnsDto> columns = excelSheetDto.getData().getColumns();
                List<List<ExcelData>> values = excelSheetDto.getData().getValues();

                for (int lineNumber = 0; lineNumber < values.size(); lineNumber++) {
                    List<ExcelData> value = values.get(lineNumber);
                    Map<String, ExcelData> map = new HashMap<>();
                    boolean lineIsEmpty = true;
                    for (int i = 0; i < columns.size(); i++) {
                        if (value.size() > i) {
                            //check if string value is not blank
                            if (value.get(i).getData() != null && !value.get(i).getData().trim().isEmpty()) {
                                lineIsEmpty = false;
                            }
                            map.put(columns.get(i).getTitle().trim() + "-" + columns.get(i).getColumnIndex(), value.get(i));
                        }
                    }
                    if (!lineIsEmpty) {
                        lineNumbers.add(lineNumber + 1);
                        list1.add(map);
                    }
                }
            }

            List<List<?>> list2 = new ArrayList<>();
            int[] line_nr = new int[1];
            list1.forEach(map -> {
                //pop the first element from the list
                line_nr[0] = lineNumbers.remove(0);

                //Retrieve Fields from a Java Class Using Reflection
                Medical2Ecrf1 ecrf1 = new Medical2Ecrf1();
                Medical2Ecrf2 ecrf2 = new Medical2Ecrf2();
                Medical2Ecrf3 ecrf3 = new Medical2Ecrf3();
                Medical2Ecrf4 ecrf4 = new Medical2Ecrf4();
                Medical2Ecrf5 ecrf5 = new Medical2Ecrf5();
                Medical2Ecrf6 ecrf6 = new Medical2Ecrf6();
                Medical2Ecrf7 ecrf7 = new Medical2Ecrf7();
                Medical2Ecrf8 ecrf8 = new Medical2Ecrf8();
                Medical2Ecrf9 ecrf9 = new Medical2Ecrf9();
                Medical2Ecrf10 ecrf10 = new Medical2Ecrf10();
                Medical2Ecrf20 ecrf20 = new Medical2Ecrf20();
                Calculator ecrf30 = new Calculator();

                if (file.getTypeOf() == FileType.CLINICAL_DATA || file.getTypeOf() == FileType.FACTOR_PRIOR_TO_PD) {
                    boolean mandatoryFieldsAreEmpty = false;
                    if (map.get("Local Patient ID-C") == null || map.get("Local Patient ID-C").getData() == null || map.get("Local Patient ID-C").getData().trim().isEmpty()) {
                        errorsToShow.add("Invalid file or Patient ID is empty in line " + line_nr[0]);
                        mandatoryFieldsAreEmpty = true;
                    }
                    if (map.get("Local Family ID-D") == null || map.get("Local Family ID-D").getData() == null || map.get("Local Family ID-D").getData().trim().isEmpty()) {
                        errorsToShow.add("Invalid file or Local Family ID is empty in line " + line_nr[0]);
                        mandatoryFieldsAreEmpty = true;
                    }
                    if (mandatoryFieldsAreEmpty) {
                        list2.add(Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null));
                        return;
                    }
                    String localFamilyCode = map.get("Local Family ID-D").getData();
                    String patID = map.get("Local Patient ID-C").getData();
                    if (!getEcrfObjectsFromDatabase(localFamilyCode, patID, list2, errorsToShow)) {
                        return;
                    }
                } else {
                    //add all ecrf objects to list2
                    list2.add(Arrays.asList(ecrf1, ecrf2, ecrf3, ecrf4, ecrf5, ecrf6, ecrf7, ecrf8, ecrf9, ecrf10, ecrf20, ecrf30));
                }

                Arrays.asList(new Object[]{ecrf1, Medical2Ecrf1.class}, new Object[]{ecrf2, Medical2Ecrf2.class}, new Object[]{ecrf3, Medical2Ecrf3.class},
                        new Object[]{ecrf4, Medical2Ecrf4.class}, new Object[]{ecrf8, Medical2Ecrf8.class}).forEach(ecrf -> {
                    Arrays.stream(((Class<?>) ecrf[1]).getDeclaredFields()).forEach(field -> {
                        Annotation[] annotations = field.getDeclaredAnnotations();
                        if (field.isAnnotationPresent(ExcelColumn.class)) {
                            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                            if (excelColumn.name().equals("Local Patient ID-C")) {
                                System.out.println("Local Patient ID-C");
                            }
                            //update object fields using reflection
                            try {
                                ExcelData excelData = map.get(excelColumn.name());
                                if (excelData != null) {

                                    List<Rule> columnRule = columnRules.stream()
                                            .filter(col -> col.getFirstColumn().equalsIgnoreCase(excelColumn.name()))
                                            .collect(Collectors.toList());

                                    if (!columnRule.isEmpty()) {
                                        for (Rule colRule : columnRule) {
                                            if (!colRule.getMethodName().contains(".")) {
                                                boolean methodExists = RuleHelper.isMethodExists(UploadMinimalExcelController.class, colRule.getMethodName());
                                                if (methodExists) {
                                                    Method declaredMethod = this.getClass().getDeclaredMethod(colRule.getMethodName(), String.class);
                                                    Object invoke = declaredMethod.invoke(this, excelData.getData());
                                                    String message = "";
                                                    if (invoke instanceof Boolean) {
                                                        message = colRule.getErrorMessage();
                                                    } else if (invoke instanceof String) {
                                                        message = (String) invoke;
                                                    } else if (invoke instanceof Integer) {
                                                        Integer intVal = (Integer) invoke;
                                                        if (intVal < 0) {
                                                            message = colRule.getNegativeMessage();
                                                        }
                                                    }

                                                    if (StringUtils.isNotBlank(message)) {
                                                        errorsToShow.add(message+" "+ excelColumn.name() + " in line " + line_nr[0]);
                                                    }
                                                }
                                            } else {
                                                String[] methodSplit = colRule.getMethodName().split("\\.");
                                                String methodName = methodSplit[methodSplit.length - 1];

                                                String className = "";
                                                for (int i = 0; i < methodSplit.length - 1; i++) {
                                                    className += methodSplit[i]+".";
                                                }
                                                String finalClassName = StringUtils.stripEnd(className, ".");
                                                Class validationClass = Class.forName(finalClassName);

                                                boolean methodExists = RuleHelper.isMethodExists(validationClass, methodName);
                                                if (methodExists) {
                                                    Method declaredMethod = validationClass.getDeclaredMethod(methodName, String.class);
                                                    Object o = validationClass.newInstance();
                                                    Object invoke = declaredMethod.invoke(o, excelData.getData());
                                                    String message = "";
                                                    if (invoke instanceof Boolean) {
                                                        message = colRule.getErrorMessage();
                                                    } else if (invoke instanceof String) {
                                                        message = (String) invoke;
                                                    } else if (invoke instanceof Integer) {
                                                        Integer intVal = (Integer) invoke;
                                                        if (intVal < 0) {
                                                            message = colRule.getNegativeMessage();
                                                        }
                                                    }

                                                    if (StringUtils.isNotBlank(message)) {
                                                        errorsToShow.add(message+" "+ excelColumn.name() + " in line " + line_nr[0]);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (excelColumn.map_from().length > 0) {

                                        //find the index in the map_from array which equals the name value
                                        int index = Arrays.asList(Arrays.stream(excelColumn.map_from()).map(String::toUpperCase).toArray()).
                                                indexOf(map.get(excelColumn.name()).getData() != null ? map.get(excelColumn.name()).getData().toUpperCase() : null);
                                        if (index > -1) {
                                            field.setAccessible(true);
                                            field.set(ecrf[0], excelColumn.map_to()[index]);
                                        } else {
                                            if (excelColumn.required())
                                                errorsToShow.add("Error in mapping for column " + excelColumn.name() + " in line " + line_nr[0]);
                                        }
                                    } else {
                                        if (excelColumn.required() && (map.get(excelColumn.name()).getData() == null || map.get(excelColumn.name()).getData().trim().isEmpty())) {
                                            errorsToShow.add("Error in column " + excelColumn.name() + " in line " + line_nr[0]);
                                        } else if (map.get(excelColumn.name()).getData() != null) {
                                            field.setAccessible(true);
                                            field.set(ecrf[0], map.get(excelColumn.name()).getData().trim());
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                errorsToShow.add("Error in program for field " + field.getName());
                                e.printStackTrace();
                            }
                        }
                    });
                });
            });

            while (!list2.isEmpty() && errorsToShow.isEmpty()) {
                //remove first ecrf objects from list2 and assign them to the local variable ecrf
                List ecrf = list2.remove(0);

                // and assign them to local variables
                Medical2Ecrf1 ecrf1 = (Medical2Ecrf1) ecrf.get(0);
                Medical2Ecrf2 ecrf2 = (Medical2Ecrf2) ecrf.get(1);
                Medical2Ecrf3 ecrf3 = (Medical2Ecrf3) ecrf.get(2);
                Medical2Ecrf4 ecrf4 = (Medical2Ecrf4) ecrf.get(3);
                Medical2Ecrf5 ecrf5 = (Medical2Ecrf5) ecrf.get(4);
                Medical2Ecrf6 ecrf6 = (Medical2Ecrf6) ecrf.get(5);
                Medical2Ecrf7 ecrf7 = (Medical2Ecrf7) ecrf.get(6);
                Medical2Ecrf8 ecrf8 = (Medical2Ecrf8) ecrf.get(7);
                Medical2Ecrf9 ecrf9 = (Medical2Ecrf9) ecrf.get(8);
                Medical2Ecrf10 ecrf10 = (Medical2Ecrf10) ecrf.get(9);
                Medical2Ecrf20 ecrf20 = (Medical2Ecrf20) ecrf.get(10);
                Calculator ecrf30 = (Calculator) ecrf.get(11);

                String surveyTwoId = user.getUserId() + "-" + Calendar.getInstance().getTime().getTime();

                infoToShow.add("Successfully uploaded " + ecrf1.getPatID() + "/" + ecrf1.getLocalFamilyCode() + " to the ecrf " + surveyTwoId);

                Date date = new Date(System.currentTimeMillis());
                ecrf1.setCreatedAt(date);
                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf1.setSurveyTwoId(surveyTwoId);
                ecrf1.setUser(user);
                ecrf1.setFillingStatus(FillingStatus.EXCEL_UPLOADED.getValue());
                medical2Ecrf1DAO.save(ecrf1);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf2.setSurveyTwoId(surveyTwoId);
                ecrf2.setUser(user);
                ecrf2.setFillingStatus(FillingStatus.EXCEL_UPLOADED.getValue());
                medical2Ecrf2DAO.save(ecrf2);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf3.setSurveyTwoId(surveyTwoId);
                ecrf3.setUser(user);
                ecrf3.setFillingStatus(FillingStatus.EXCEL_UPLOADED.getValue());
                medical2Ecrf3DAO.save(ecrf3);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf4.setSurveyTwoId(surveyTwoId);
                ecrf4.setUser(user);
                ecrf4.setFillingStatus(FillingStatus.EXCEL_UPLOADED.getValue());
                medical2Ecrf4DAO.save(ecrf4);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf5.setSurveyTwoId(surveyTwoId);
                ecrf5.setUser(user);
                ecrf5.setFillingStatus(FillingStatus.NOT_STARTED.getValue());
                medical2Ecrf5DAO.save(ecrf5);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf6.setSurveyTwoId(surveyTwoId);
                ecrf6.setUser(user);
                ecrf6.setFillingStatus(FillingStatus.NOT_STARTED.getValue());
                medical2Ecrf6DAO.save(ecrf6);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf7.setSurveyTwoId(surveyTwoId);
                ecrf7.setUser(user);
                ecrf7.setFillingStatus(FillingStatus.NOT_STARTED.getValue());
                medical2Ecrf7DAO.save(ecrf7);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf8.setSurveyTwoId(surveyTwoId);
                ecrf8.setUser(user);
                ecrf8.setFillingStatus(FillingStatus.EXCEL_UPLOADED.getValue());
                medical2Ecrf8DAO.save(ecrf8);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf9.setSurveyTwoId(surveyTwoId);
                ecrf9.setUser(user);
                ecrf9.setFillingStatus(FillingStatus.NOT_STARTED.getValue());
                medical2Ecrf9DAO.save(ecrf9);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf10.setSurveyTwoId(surveyTwoId);
                ecrf10.setUser(user);
                ecrf10.setFillingStatus(FillingStatus.NOT_STARTED.getValue());
                medical2Ecrf10DAO.save(ecrf10);

                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf20.setSurveyTwoId(surveyTwoId);
                ecrf20.setUser(user);
                medical2Ecrf20DAO.save(ecrf20);

                ecrf30.setCreatedAt(date);
                if (FileType.SAMPLE_MANIFEST == file.getTypeOf()) ecrf30.setSurveyTwoId(surveyTwoId);
                ecrf30.setUser(user);
                medical2Ecrf30DAO.save(ecrf30);
            }
        }

        if (errorsToShow.isEmpty()) {
            file.setStatus(Status.SUBMITTED);
            fileRepository.save(file);
            errorsToShow.add("Successfully uploaded");
            errorsToShow.addAll(infoToShow);
        }
        modelAndView.addObject("errorsToShow", errorsToShow);
        return modelAndView;
    }

    //create method to retrieve ecrf objects from the database by local family code and patient id
    public boolean getEcrfObjectsFromDatabase(String localFamilyCode, String patID, List<List<?>> list2, List<String> errorsToShow) {
        List<Object> invalid = Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null);
        Medical2Ecrf1[] ecrf1s = medical2Ecrf1DAO.findByPatID(patID).stream().filter(ecrf1 ->
                ecrf1.getLocalFamilyCode().equals(localFamilyCode) &&
                        !"deleted".equals(ecrf1.getCurrStatus())).collect(Collectors.toList()).toArray(new Medical2Ecrf1[0]);
        if (ecrf1s.length == 0) {
            errorsToShow.add("No record found for " + patID + "/" + localFamilyCode);
            list2.add(invalid);
            return false;
        } else if (ecrf1s.length > 1) {
            list2.add(invalid);
            errorsToShow.add("Multiple records found for " + patID + "/" + localFamilyCode);
            return false;
        } else {
            Medical2Ecrf1 ecrf1 = ecrf1s[0];
            Medical2Ecrf2 ecrf2 = medical2Ecrf2DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf3 ecrf3 = medical2Ecrf3DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf4 ecrf4 = medical2Ecrf4DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf5 ecrf5 = medical2Ecrf5DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf6 ecrf6 = medical2Ecrf6DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf7 ecrf7 = medical2Ecrf7DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf8 ecrf8 = medical2Ecrf8DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf9 ecrf9 = medical2Ecrf9DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf10 ecrf10 = medical2Ecrf10DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Medical2Ecrf20 ecrf20 = medical2Ecrf20DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());
            Calculator ecrf30 = medical2Ecrf30DAO.findBySurveyTwoId(ecrf1.getSurveyTwoId());

            list2.add(Arrays.asList(ecrf1, ecrf2, ecrf3, ecrf4, ecrf5, ecrf6, ecrf7, ecrf8, ecrf9, ecrf10, ecrf20, ecrf30));
            return true;
        }
    }

    public boolean validateYear(String value) {
        return true;
    }

}