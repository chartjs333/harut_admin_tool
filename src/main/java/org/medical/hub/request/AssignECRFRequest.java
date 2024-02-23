package org.medical.hub.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AssignECRFRequest {

    @NotNull
    private Long assignTo;

    @NotNull
    private String surveyTwoId;
}
