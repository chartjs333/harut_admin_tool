package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateMailRequest {

    @NotEmpty(message = "Name is required.")
    private String subject;

    private String to;

    @NotEmpty(message = "Email content is required")
    private String emailContent;

    @NotEmpty(message = "Sent at is required.")
    private String sentAt ;
}
