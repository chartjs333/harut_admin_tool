package org.medical.hub.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EcrfDataTable {

    private String checkbox;
    private int id;
    private String patID;
    private String fillingStatus;
    private String createdAt;
    private String action;
    private String userWithAddress;
}
