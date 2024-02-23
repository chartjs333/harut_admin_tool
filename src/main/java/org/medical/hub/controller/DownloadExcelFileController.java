package org.medical.hub.controller;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.common.Routes;
import org.medical.hub.exception.ExcelFileHandlingException;
import org.medical.hub.exception.TemplateFileNotFoundException;
import org.medical.hub.message.ExcelMessages;
import org.medical.hub.models.ExcelFile;
import org.medical.hub.models.FileType;
import org.medical.hub.request.ExcelFileActionRequest;
import org.medical.hub.services.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.*;
import java.net.URL;

@Controller
public class DownloadExcelFileController {

    @Value("${template.dir:file-templates}")
    private String FILE_TEMPLATE_DIR;

    @Value("${template.file.name.manifest:Sample Manifest}")
    private String SAMPLE_MANIFEST_FILE_NAME;

    @Value("${template.file.name.clinical:Clinical Data}")
    private String CLINICAL_DATA_FILE_NAME;

    @Value("${template.file.name.factor:Factors Prior to PD}")
    private String FACTORS_PRIOR_FILE_NAME;

    private static final String EXCEL_FILE_EXTENSION = ".xlsx";
    private static final String NO_CACHE_NO_STORE_MUST_REVALIDATE = "no-cache, no-store, must-revalidate";
    private static final String NO_CACHE = "no-cache";
    private static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";
    private static final String HEADER_VALUE = "0";
    private final ExcelFileService fileService;

    @Autowired
    public DownloadExcelFileController(ExcelFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/download-clinical-template", name = "download-clinical-template")
    public ResponseEntity<InputStreamResource> downloadClinicalTemplate() {

        return downloadTemplateFile(FileType.CLINICAL_DATA);
    }

    @GetMapping(value = "/download-manifest-template", name = "download-manifest-template")
    public ResponseEntity<InputStreamResource> downloadManifestTemplate() {

        return downloadTemplateFile(FileType.SAMPLE_MANIFEST);
    }

    @GetMapping(value = "/download-factors-prior", name = "download-factors-prior-template")
    public ResponseEntity<InputStreamResource> downloadFactorsPrior() {

        return downloadTemplateFile(FileType.FACTOR_PRIOR_TO_PD);
    }

    @GetMapping(value = Routes.ExcelFile.DOWNLOAD, name = "download-excel-file")
    public ResponseEntity<InputStreamResource> download(@PathVariable("id") Long id) {
        ExcelFile byId = this.fileService.getById(id);

        return downloadFile(byId.getFileName(), byId.getContent());
    }

    @PostMapping(value = Routes.ExcelFile.EXPORT, name = "export-excel-file")
    public ResponseEntity<InputStreamResource> exportExcelFile(@PathVariable("id") Long id,
                                                               @Valid @ModelAttribute("fileAction") ExcelFileActionRequest fileAction) {

        byte[] excelFile = this.fileService.createExcelFile(id, fileAction);
        ExcelFile byId = this.fileService.getById(id);
        return downloadFile(byId.getFileName(), excelFile);
    }

    private ResponseEntity<InputStreamResource> downloadTemplateFile(FileType fileType) {

        try {
            String fileName = getFileName(fileType);

            URL aStatic = getClass().getClassLoader().getResource(FILE_TEMPLATE_DIR);
            if (aStatic == null) {
                throw new FileNotFoundException();
            }
            String fileLocation = aStatic.getPath() + File.separator + fileName;
            return downloadFile(fileName, fileLocation);
        } catch (FileNotFoundException ex) {
            throw new TemplateFileNotFoundException(getErrorMessage(fileType));
        } catch (Exception ex) {
            throw new ExcelFileHandlingException();
        }
    }

    private String getErrorMessage(FileType fileType) {
        switch (fileType) {
            case SAMPLE_MANIFEST:
                return ExcelMessages.SAMPLE_MANIFEST_NOT_FOUND;
            case CLINICAL_DATA:
                return ExcelMessages.CLINICAL_DATA_NOT_FOUND;
            case FACTOR_PRIOR_TO_PD:
                return ExcelMessages.FACTOR_PRIOR_NOT_FOUND;
            default:
                return null;
        }
    }

    private String getFileName(FileType fileType) {
        switch (fileType) {
            case SAMPLE_MANIFEST:
                return SAMPLE_MANIFEST_FILE_NAME + EXCEL_FILE_EXTENSION;
            case CLINICAL_DATA:
                return CLINICAL_DATA_FILE_NAME + EXCEL_FILE_EXTENSION;
            case FACTOR_PRIOR_TO_PD:
                return FACTORS_PRIOR_FILE_NAME + EXCEL_FILE_EXTENSION;
            default:
                return null;
        }
    }

    private ResponseEntity<InputStreamResource> downloadFile(String fileName, String fileLocation) throws FileNotFoundException {
        HttpHeaders httpHeaders = getHeaders(fileName);

        InputStream is = new FileInputStream(fileLocation);
        InputStreamResource inputStreamResource = new InputStreamResource(is);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }

    private ResponseEntity<InputStreamResource> downloadFile(String fileName, byte[] blob) {
        if (StringUtils.isBlank(fileName)) {
            fileName = "excel";
        }
        HttpHeaders httpHeaders = getHeaders(fileName + EXCEL_FILE_EXTENSION);

        InputStream is = new ByteArrayInputStream(blob);
        InputStreamResource inputStreamResource = new InputStreamResource(is);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);
    }

    private HttpHeaders getHeaders(String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        httpHeaders.add(HttpHeaders.CACHE_CONTROL, NO_CACHE_NO_STORE_MUST_REVALIDATE);
        httpHeaders.add(HttpHeaders.PRAGMA, NO_CACHE);
        httpHeaders.add(HttpHeaders.EXPIRES, HEADER_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_VND_MS_EXCEL);

        return httpHeaders;
    }

}
