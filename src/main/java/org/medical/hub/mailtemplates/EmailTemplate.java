package org.medical.hub.mailtemplates;

import lombok.Data;
import org.medical.hub.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Table(name = "email_templates")
@Entity
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String emailContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User user;
}
