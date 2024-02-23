package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateRuleRequest {

    @NotNull(message = "Please select the file type")
    @Range(message = "Please select the valid file type", min = 0, max = 1)
    private Integer fileType;

    @NotNull(message = "Please select the rule type")
    @Range(message = "Please select the rule type", min = 1, max = 2)
    private Integer ruleType;

    @NotNull(message = "Name is required")
    private String name;

    private Integer compareBetween;
    private String firstColumn;
    private String secondColumn;
    private Integer expression;
    private String errorMessage;
    private String columnMethodName;
    private String parameterValue;

    private String variableName;
    private Integer negativeValue;
    private Integer positiveValue;
    private Integer undefinedValue;

    private String negativeMessage;
    private String undefinedMessage;

    // for row type
    private String methodName;
    private String parameters;
}
