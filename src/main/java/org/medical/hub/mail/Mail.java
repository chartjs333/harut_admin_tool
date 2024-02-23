package org.medical.hub.mail;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.models.Expression;
import org.medical.hub.models.ExpressionConverter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_emails")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String from;
    private String subject;
    private String messageId;
    private String content;
    private Long createdAt;
    private Long updatedAt;
    private Boolean isFavorite;
    private Long deletedAt;

    @Convert(converter = MailStatusConverter.class)
    private MailStatus status;


}
