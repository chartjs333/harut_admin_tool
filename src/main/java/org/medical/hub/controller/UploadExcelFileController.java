package org.medical.hub.controller;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.message.ExcelMessages;
import org.medical.hub.models.FileType;
import org.medical.hub.request.FileUploadRequest;
import org.medical.hub.services.ExcelFileService;
import org.medical.hub.validators.CustomFileValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UploadExcelFileController {

    private final ExcelFileService fileService;
    private final CustomFileValidators fileValidators;

    private static final String UPLOAD_VIEW = "excel/upload";
    private static final String MODEL_NAME = "uploadModel" ;

    @Autowired
    public UploadExcelFileController(CustomFileValidators fileValidators,
                                     ExcelFileService fileService) {
        this.fileValidators = fileValidators;
        this.fileService = fileService;
    }

    @GetMapping(value = Routes.ExcelFile.UPLOAD_CLINICAL_DATA_FILE, name = "upload-clinical-excel-file")
    public String uploadClinicalFile(FileUploadRequest uploadModel, Model model) {

        uploadModel.setFileType(FileType.CLINICAL_DATA);
        model.addAttribute("fileType", FileType.SAMPLE_MANIFEST.getValue());
        model.addAttribute(MODEL_NAME, uploadModel);
        return UPLOAD_VIEW;
    }

    @GetMapping(value = Routes.ExcelFile.UPLOAD_SAMPLE_MANIFEST_FILE, name = "upload-manifest-excel-file")
    public String uploadManifestFile(FileUploadRequest uploadModel, Model model) {

        uploadModel.setFileType(FileType.SAMPLE_MANIFEST);
        model.addAttribute("fileType", FileType.SAMPLE_MANIFEST.getValue());
        model.addAttribute(MODEL_NAME, uploadModel);

        return UPLOAD_VIEW;
    }

    @GetMapping(value = Routes.ExcelFile.UPLOAD_FACTORS_PRIOR_FILE, name = "upload-factors-prior-to-pd")
    public String uploadFactorsPriorToPD(FileUploadRequest uploadModel, Model model) {

        uploadModel.setFileType(FileType.FACTOR_PRIOR_TO_PD);
        model.addAttribute("fileType", FileType.FACTOR_PRIOR_TO_PD.getValue());
        model.addAttribute(MODEL_NAME, uploadModel);

        return UPLOAD_VIEW;
    }

    @PostMapping(value = "/upload/", name = "upload-file-post")
    public String upload(@Valid @ModelAttribute(MODEL_NAME) FileUploadRequest uploadModel,
                         BindingResult result,
                         RedirectAttributes ra) {
        this.fileValidators.validate(uploadModel, result);
        if (result.hasErrors()) {
            return UPLOAD_VIEW;
        }

        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, ExcelMessages.SUCCESS_FILE_UPLOADED);
        this.fileService.save(uploadModel);
        return "redirect:" + Routes.ExcelFile.GET;
    }

    @ModelAttribute(MODEL_NAME)
    private FileUploadRequest uploadModelRequest() {
        return new FileUploadRequest();
    }
}
