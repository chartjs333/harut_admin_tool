package org.medical.hub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelData {

    private String data;

    private boolean isValid ;

    private String cellReference;

    private String style;

    private String message;

    private String rowReference;

    private String rowMessage;
}
