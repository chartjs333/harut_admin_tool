package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medical.hub.annotation.ExcelColumn;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity(name = "ecrf1")
@Table(name = "medical_2_ecrf1",  uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
@NoArgsConstructor
public class Medical2Ecrf1 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf1_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf1_id_seq", sequenceName = "medical_2_ecrf1_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "ecrf_status", columnDefinition = "varchar", length = 128)
    private String ecrfStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp", length = 29)
    private Date createdAt;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @ExcelColumn(name = "Year of PD motor symptom onset-M")
    @Column(name = "aao", columnDefinition = "varchar", length = 128)
    private String aao;

    @Column(name = "co_death", columnDefinition = "varchar", length = 128)
    private String coDeath;

    @ExcelColumn(name = "Parental consanguinity-K", required = true, map_from = {"Yes", "No", "Unknown"}, map_to = {"1","0","2"})
    @Column(name = "consang", columnDefinition = "varchar", length = 128)
    private String consang;

    @ExcelColumn(name = "If yes, please describe:-L")
    @Column(name = "consang_oth", columnDefinition = "varchar", length = 128)
    private String consangOth;

    @Column(name = "contact_month", columnDefinition = "varchar", length = 128)
    private String contactMonth;

    @Column(name = "contact_year", columnDefinition = "varchar", length = 128)
    private String contactYear;

    @Column(name = "country", columnDefinition = "varchar", length = 128)
    private String country;

    @Column(name = "curr_status", columnDefinition = "varchar", length = 128)
    private String currStatus;

    @Column(name = "diagn_crit_0", columnDefinition = "varchar", length = 128)
    private String diagnCrit0;

    @Column(name = "diagn_crit_1", columnDefinition = "varchar", length = 128)
    private String diagnCrit1;

    @Column(name = "diagn_crit_2", columnDefinition = "varchar", length = 128)
    private String diagnCrit2;

    @Column(name = "diagn_crit_3", columnDefinition = "varchar", length = 128)
    private String diagnCrit3;

    @Column(name = "dob_day", columnDefinition = "varchar", length = 128)
    private String dobDay;

    @Column(name = "dob_month", columnDefinition = "varchar", length = 128)
    private String dobMonth;

    @ExcelColumn(name = "Year of birth-G", required = true)
    @Column(name = "dob_year", columnDefinition = "varchar", length = 128)
    private String dobYear;

    @Column(name = "educ_0", columnDefinition = "varchar", length = 128)
    private String educ0;

    @Column(name = "educ_1", columnDefinition = "varchar", length = 128)
    private String educ1;

    @Column(name = "educ_2", columnDefinition = "varchar", length = 128)
    private String educ2;

    @ExcelColumn(name="Ethnicity-I", required = true)
    @Column(name = "ethn", columnDefinition = "varchar", length = 128)
    private String ethn;

    @ExcelColumn(name="If Asian, pls specify Ethnicity-J")
    @Column(name = "ethn_as", columnDefinition = "varchar", length = 128)
    private String ethnAs;

    @Column(name = "ethn_as_oth", columnDefinition = "varchar", length = 128)
    private String ethnAsOth;

    @Column(name = "ethn_oth", columnDefinition = "varchar", length = 128)
    private String ethnOth;

    @Column(name = "occup", columnDefinition = "varchar", length = 128)
    private String occup;

    @ExcelColumn(name = "Local Patient ID-C", required = true)
    @Column(name = "pat_ID", columnDefinition = "varchar", length = 128)
    private String patID;

    @ExcelColumn(name = "Sex-H", required = true, map_from = {"Male", "Female"}, map_to = {"1", "2"})
    @Column(name = "sex", columnDefinition = "varchar", length = 128)
    private String sex;

    @ExcelColumn(name = "Site-A", required = true)
    @Column(name = "site", columnDefinition = "varchar", length = 128)
    private String site;

    @ExcelColumn(name = "Principal Investigator-B", required = true)
    @Column(name = "site_PI", columnDefinition = "varchar", length = 128)
    private String sitePI;

    @Column(name = "yo_death", columnDefinition = "varchar", length = 128)
    private String yoDeath;

    @ExcelColumn(name = "Year of PD diagnosis-N")
    @Column(name = "yo_diagn", columnDefinition = "varchar", length = 128)
    private String yoDiagn;

    @ExcelColumn(name = "Relationship to Index-F", required = true)
    @Column(name = "index_or_type_of_relation_to_index", columnDefinition = "varchar", length = 128)
    private String indexOrTypeOfRelationToIndex;

    @Column(name = "other_relation", columnDefinition = "varchar", length = 128)
    private String otherRelation;

    @ExcelColumn(name = "Local Family ID-D", required = true)
    @Column(name = "local_family_code", columnDefinition = "varchar", length = 128)
    private String localFamilyCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}