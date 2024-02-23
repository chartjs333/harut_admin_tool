package org.medical.hub.dto;

import lombok.Data;

@Data
public class EmailTemplatesDataTable {

    private Integer sn;
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String createdBy;
    private String action ;

}
