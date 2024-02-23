package org.medical.hub.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer not found.")
public class CustomerNotFoundException extends RuntimeException {

    private String message;
    private String redirectTo;

    protected CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message, String redirectTo) {
        this.message = message;
        this.redirectTo = redirectTo;
    }
}
