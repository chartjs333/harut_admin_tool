package org.medical.hub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleDataTable {

    private Integer sn;
    private Long id;
    private String fileType;
    private String createdAt;
    private String action;
    private String ruleType;
    private String name;
    private String createdBy;
}
