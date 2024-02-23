package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelDataValidationRequest {

    String data;

    String cellReference;

    String rowData;
}
