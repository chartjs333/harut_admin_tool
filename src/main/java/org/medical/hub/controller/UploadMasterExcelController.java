package org.medical.hub.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.medical.hub.models.Medical2Ecrf1;
import org.medical.hub.models.Medical2SampleInfo;
import org.medical.hub.repository.Medical2SampleInfoRepository;
import org.medical.hub.repository.eCRF1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadMasterExcelController {
    private static final String N_G = "-99";
    private final ModelAndView modelAndView = new ModelAndView("user-main");
    @Autowired
    private Medical2SampleInfoRepository medical2SampleInfoRepository;
    @Autowired
    private eCRF1Repository iMedical2EcrfDAO1;
    @Value("${masterExcelFilePath}")
    private String masterExcelFilePath;


    @Transactional
    @PostMapping("/uploadMasterExcel")
    public ModelAndView uploadMasterExcel(HttpServletResponse response,
                                          @RequestParam("file") MultipartFile file,
                                          Authentication authentication, ModelMap model) throws IOException {

        boolean saveToDB = true;
        List<String> errors = new ArrayList<>();
        String errorNotFoundLocalId = "";
        String errorNotUniqueLocalId = "";
        String errorAlreadyHaveSuchUpdate = "";
        List<Medical2SampleInfo> listOfSamples = new ArrayList<>();

        String[] fileNameArr = file.getOriginalFilename().split("\\.");
        String extension = fileNameArr[fileNameArr.length - 1];
        if (!extension.equals("xlsx")) {
            errors.add("Only Files with xlsx format are supported");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);
        String sheetName = sheet.getSheetName();
        if (sheet == null || !sheet.getSheetName().trim().equalsIgnoreCase("Overview all samples")) {
            errors.add("The file's first sheet must have 'Overview all samples' title");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }

        Integer headerLineIndex = null;
        Integer medical2IdIndex = null;
        Integer medical2FamilyIdIndex = null;
        Integer localIDIndex = null;
        boolean foundheaderline = false;

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (row.getCell(j) != null && row.getCell(j).toString().trim().equalsIgnoreCase("Local ID")) {
                    headerLineIndex = i;
                    foundheaderline = true;
                    break;
                }
            }
            if (foundheaderline) {
                break;
            }
        }

        if (!foundheaderline) {
            errors.add("No header line found");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }

        //stugel myus 3 columnere

        for (int i = 0; i < sheet.getRow(headerLineIndex).getLastCellNum(); i++) {

            if (sheet.getRow(headerLineIndex).getCell(i) != null &&
                    sheet.getRow(headerLineIndex).getCell(i).toString().trim().equalsIgnoreCase("Medical2 ID (Medical2)")) {
                medical2IdIndex = i;
            } else if (sheet.getRow(headerLineIndex).getCell(i) != null &&
                    sheet.getRow(headerLineIndex).getCell(i).toString().trim().equalsIgnoreCase("Medical2 Family ID")) {
                medical2FamilyIdIndex = i;
            } else if (sheet.getRow(headerLineIndex).getCell(i) != null &&
                    sheet.getRow(headerLineIndex).getCell(i).toString().trim().equalsIgnoreCase("Local ID")) {
                localIDIndex = i;
            }
        }

        if (localIDIndex == null || medical2FamilyIdIndex == null || medical2IdIndex == null) {
            //todo error tal, == headerum chi gtel kam local Idn, kam Medical2 idnere
            errors.add("Missing important column(s) in header line");
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }

        for (int i = headerLineIndex + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            String localId;
            String medical2Id;
            String medical2FamilyId;

            if (row.getCell(localIDIndex) != null && !row.getCell(localIDIndex).toString().trim()
                    .equals("") &&
                    row.getCell(medical2FamilyIdIndex) != null && !row.getCell(medical2FamilyIdIndex).toString().trim()
                    .equals("") &&
                    row.getCell(medical2IdIndex) != null && !row.getCell(medical2IdIndex).toString().trim()
                    .equals("")) {

                localId = row.getCell(localIDIndex).toString();
                medical2Id = row.getCell(medical2IdIndex).toString();
                medical2FamilyId = row.getCell(medical2FamilyIdIndex).toString();


                List<Medical2Ecrf1> listOfEcrfs = iMedical2EcrfDAO1.findByPatID(localId);
                //04.04.2022
                listOfEcrfs = ignoreDeletedStatuses(listOfEcrfs);
                if (listOfEcrfs == null || listOfEcrfs.isEmpty()) {
                    saveToDB = false;
                    errorNotFoundLocalId = errorNotFoundLocalId + localId + ", ";
                    //todo ++++++error Stringum pahum enq sran, vor nman localId-ov chenq gtel patient DONE!
                    continue;
                } else if (listOfEcrfs.size() > 1) {

                    saveToDB = false;
                    errorNotUniqueLocalId = errorNotUniqueLocalId + localId + ", ";
                    continue;
                    //todo  ++++++stex haskanal ete 1-ic shat em gtnum, inch enq anum?) DONE!
                } else {
                    Medical2SampleInfo medical2SampleDB = medical2SampleInfoRepository.findByPatientId(localId);
                    if (medical2SampleDB != null) {
                        //todo error Stringum pahum enq sran, vor nman localId-ov gtel enq Medical2SampleInfo, isk piti chliner
                        saveToDB = false;
                        errorAlreadyHaveSuchUpdate = errorAlreadyHaveSuchUpdate + localId + ", ";
                        continue;
                    }
                    Medical2SampleInfo medical2Sample = new Medical2SampleInfo();
                    medical2Sample.setPatientId(localId);
                    medical2Sample.setMedical2Id(medical2Id);
                    medical2Sample.setMedical2FamId(medical2FamilyId);
                    medical2Sample.setSurveyTwoId(listOfEcrfs.get(0).getSurveyTwoId());
                    listOfSamples.add(medical2Sample);
                }
            }
        }

//    if (saveToDB) {
//      medical2SampleInfoRepository.saveAll(listOfSamples);
//      return modelAndView;
//    }
        medical2SampleInfoRepository.saveAll(listOfSamples);
        if (!errorNotFoundLocalId.trim().equals("")) {
            modelAndView.addObject("errorNotFoundLocalId", errorNotFoundLocalId);
        }
        if (!errorNotUniqueLocalId.trim().equals("")) {
            modelAndView.addObject("errorNotUniqueLocalId", errorNotUniqueLocalId);
        }
        if (!errorAlreadyHaveSuchUpdate.trim().equals("")) {
            modelAndView.addObject("errorAlreadyHaveSuchUpdate", errorAlreadyHaveSuchUpdate);
        }
        //создать файл и папку если они не существуют
        File masterExcelFile = new File(masterExcelFilePath);
        //получить название файла из пути
        String fileName = masterExcelFile.getName();
        //разделитель пути к файлу независимо от системы
        String separator = System.getProperty("file.separator");
        if (masterExcelFile.getAbsolutePath().lastIndexOf(separator) == -1) separator = "/";
        //получить навание пути к файлу без самого файла
        String filePath = masterExcelFile.getAbsolutePath().substring(0, masterExcelFile.getAbsolutePath().lastIndexOf(separator));
        if (!new File(filePath).exists()) {
            new File(filePath).mkdir();
        }
        FileOutputStream outputStream = new FileOutputStream(filePath + separator + fileName);
        wb.write(outputStream);
        outputStream.close();

        return modelAndView;
    }

    public List<Medical2Ecrf1> ignoreDeletedStatuses(List<Medical2Ecrf1> listFromDb) {
        List<Medical2Ecrf1> newList = new ArrayList<>();
        for (Medical2Ecrf1 ecrf1 : listFromDb) {
            if (ecrf1.getEcrfStatus() == null) {
                newList.add(ecrf1);
            } else if (!ecrf1.getEcrfStatus().equals("deleted")) {
                newList.add(ecrf1);
            }
        }
        return newList;
    }
}
