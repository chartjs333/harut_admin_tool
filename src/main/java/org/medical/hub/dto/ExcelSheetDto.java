package org.medical.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExcelSheetDto {

    ExcelDataDto data ;

    String sheetName;
}
