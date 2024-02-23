package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.customer.Customer;

import java.util.List;

@Getter
@Setter
public class MailDataTableResponse {

    private Long checkbox;
    private String subject;
    private String correspondents;
    private String createdAt;
    private String status;

    private MailAction favorite;
    private Long id ;
    private String action;
    private List<MailAction> actions;
    private Customer customer;
}
