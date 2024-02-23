package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OptionSpamRequest {

    @NotNull(message = "Please specify the contents.")
    private String content;
}
