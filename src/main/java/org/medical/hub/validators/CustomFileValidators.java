package org.medical.hub.validators;

import org.medical.hub.request.FileUploadRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class CustomFileValidators implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FileUploadRequest uploadedFile = (FileUploadRequest) target;
        MultipartFile file = uploadedFile.getFile();
        if (file.isEmpty()) {
            errors.rejectValue("file", "upload.file.required", "Please upload the file");
        }

        String contentType = file.getContentType();
        if (!acceptedMimeTypes().contains(contentType)) {
            errors.rejectValue("file", "upload.invalid.file.type", "Please upload valid excel file(.xlsx) file only");
        }
    }

    private List<String> acceptedMimeTypes() {
        return List.of(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-excel",
                "application/msexcel",
                "application/x-msexcel",
                "application/x-ms-excel",
                "application/x-excel",
                "application/x-dos_ms_excel",
                "application/xls",
                "application/x-xls"
        );
    }
}
