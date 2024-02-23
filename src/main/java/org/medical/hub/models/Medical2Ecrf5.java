package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf5", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf5 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf5_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf5_id_seq", sequenceName = "medical_2_ecrf5_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;


    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Column(name = "covid_19", columnDefinition = "varchar", length = 128)
    private String covid19;

    @Column(name = "covid_19_PD", columnDefinition = "varchar", length = 128)
    private String covid19PD;

    @Column(name = "covid_19_PD_spec", columnDefinition = "varchar", length = 128)
    private String covid19PDSpec;

    @Column(name = "covid_19_hyposm", columnDefinition = "varchar", length = 128)
    private String covid19Hyposm;

    @Column(name = "covid_19_icu", columnDefinition = "varchar", length = 128)
    private String covid19Icu;

    @Column(name = "covid_19_month", columnDefinition = "varchar", length = 128)
    private String covid19Month;

    @Column(name = "covid_19_othneuro", columnDefinition = "varchar", length = 128)
    private String covid19Othneuro;

    @Column(name = "covid_19_othneuro_spec", columnDefinition = "varchar", length = 128)
    private String covid19OthneuroSpec;

    @Column(name = "covid_19_vent", columnDefinition = "varchar", length = 128)
    private String covid19Vent;

    @Column(name = "covid_19_year", columnDefinition = "varchar", length = 128)
    private String covid19Year;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}