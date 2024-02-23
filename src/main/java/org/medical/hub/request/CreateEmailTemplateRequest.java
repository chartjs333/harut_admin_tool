package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateEmailTemplateRequest {

    @NotNull(message = "Name is required.")
    private String name;

    @NotNull(message = "Description is required.")
    private String description;

    @NotNull(message = "Email content is required")
    private String emailContent;
}
