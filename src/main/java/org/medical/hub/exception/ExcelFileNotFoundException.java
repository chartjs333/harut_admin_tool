package org.medical.hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Excel file not found ")
public class ExcelFileNotFoundException extends RuntimeException{
}
