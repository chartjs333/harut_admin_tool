package org.medical.hub.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExcelColumnsDto {

    String type;
    String title;
    List<String> source;
    String name ;
    List<String> options;
    String editor;
    boolean allowEmpty;
    Integer width ;
    String align;
    String formula1;
    String columnIndex;
}
