package org.medical.hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Rule not found ")
public class RuleNotFoundException  extends RuntimeException{
}
