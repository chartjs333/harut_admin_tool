package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailDto {
    private String smtp;
    private int port;

    private String fromEmail;
    private String fromName;
    private String password;

    private String subject;
    private String sendTo;
    private String cc;
    private String content;

}
