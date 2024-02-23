package org.medical.hub.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelFileActionRequest {

    String data;
    String buttonAction;
    String columns;
    String fileName;
}
