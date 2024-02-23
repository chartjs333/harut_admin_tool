package org.medical.hub.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDataTable {

    private Integer sn;
    private Long id;
    private String name;
    private String createdAt;
    private String action;
    private String email;
    private String phoneNumber;
}
