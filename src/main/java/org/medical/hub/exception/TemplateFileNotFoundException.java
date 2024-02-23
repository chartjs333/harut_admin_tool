package org.medical.hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Template not found.")
public class TemplateFileNotFoundException extends RuntimeException {

    private String message;

    public TemplateFileNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
