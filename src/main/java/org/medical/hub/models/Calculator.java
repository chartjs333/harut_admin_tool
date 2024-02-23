package org.medical.hub.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "calculator", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Calculator implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="calculator_id_seq")
    @SequenceGenerator(name="calculator_id_seq", sequenceName="calculator_id_seq", allocationSize=1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp", length = 29)
    private Date createdAt;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Column(name = "dd1", columnDefinition = "varchar", length = 128)
    private String dd1;

    @Column(name = "dd10", columnDefinition = "varchar", length = 128)
    private String dd10;

    @Column(name = "dd11", columnDefinition = "varchar", length = 128)
    private String dd11;

    @Column(name = "dd12", columnDefinition = "varchar", length = 128)
    private String dd12;

    @Column(name = "dd13", columnDefinition = "varchar", length = 128)
    private String dd13;

    @Column(name = "dd14", columnDefinition = "varchar", length = 128)
    private String dd14;

    @Column(name = "dd15", columnDefinition = "varchar", length = 128)
    private String dd15;

    @Column(name = "dd16", columnDefinition = "varchar", length = 128)
    private String dd16;

    @Column(name = "dd17", columnDefinition = "varchar", length = 128)
    private String dd17;

    @Column(name = "dd18", columnDefinition = "varchar", length = 128)
    private String dd18;

    @Column(name = "dd19", columnDefinition = "varchar", length = 128)
    private String dd19;

    @Column(name = "dd2", columnDefinition = "varchar", length = 128)
    private String dd2;

    @Column(name = "dd20", columnDefinition = "varchar", length = 128)
    private String dd20;

    @Column(name = "dd21", columnDefinition = "varchar", length = 128)
    private String dd21;

    @Column(name = "dd22", columnDefinition = "varchar", length = 128)
    private String dd22;

    @Column(name = "dd3", columnDefinition = "varchar", length = 128)
    private String dd3;

    @Column(name = "dd4", columnDefinition = "varchar", length = 128)
    private String dd4;

    @Column(name = "dd5", columnDefinition = "varchar", length = 128)
    private String dd5;

    @Column(name = "dd6", columnDefinition = "varchar", length = 128)
    private String dd6;

    @Column(name = "dd7", columnDefinition = "varchar", length = 128)
    private String dd7;

    @Column(name = "dd8", columnDefinition = "varchar", length = 128)
    private String dd8;

    @Column(name = "dd9", columnDefinition = "varchar", length = 128)
    private String dd9;

    @Column(name = "result", columnDefinition = "varchar", length = 128)
    private String result;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}