package org.medical.hub.password;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.validators.password.PasswordConfirmation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@PasswordConfirmation(first = "password", second ="passwordConfirmation", message = "The password fields must match")
public class ChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String password ;

    @NotBlank
    private String passwordConfirmation ;
}
