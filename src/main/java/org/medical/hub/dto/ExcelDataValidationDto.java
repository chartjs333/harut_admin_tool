package org.medical.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExcelDataValidationDto {

    private String type;

    private String[] values;

    private String formula;
}
