package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailAction {

    private Long id;
    private String name;
    private String icon ;
    private boolean isTrash ;
    private String url;
    private String action ;
}
