package org.medical.hub.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Email already exists")
public class CustomerEmailAlreadyExists extends RuntimeException{

    private String message;
    private String redirectTo;

}
