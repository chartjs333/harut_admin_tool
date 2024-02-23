package org.medical.hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExcelDataDto {

    private List<List<String>> data;

    private List<List<ExcelData>> values;

    private List<ExcelColumnsDto> columns;

}
