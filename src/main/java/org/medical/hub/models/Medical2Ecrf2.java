package org.medical.hub.models;

import lombok.Getter;
import lombok.Setter;
import org.medical.hub.annotation.ExcelColumn;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "medical_2_ecrf2", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf2 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf2_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf2_id_seq", sequenceName = "medical_2_ecrf2_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Column(name = "fam_PD", columnDefinition = "varchar", length = 128)
    private String famPD;

    @Column(name = "fam_PD_gd1", columnDefinition = "varchar", length = 128)
    private String famPDGd1;

    @Column(name = "fam_PD_gd10", columnDefinition = "varchar", length = 128)
    private String famPDGd10;

    @Column(name = "fam_PD_gd11", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd11;

    @Column(name = "fam_PD_gd12", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd12;

    @Column(name = "fam_PD_gd13", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd13;

    @Column(name = "fam_PD_gd14", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd14;

    @Column(name = "fam_PD_gd15", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd15;

    @Column(name = "fam_PD_gd16", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd16;

    @Column(name = "fam_PD_gd17", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd17;

    @Column(name = "fam_PD_gd18", columnDefinition = "varchar", length = 128)
    private String fam_PD_gd18;

    @Column(name = "fam_PD_ind1", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind1;

    @Column(name = "fam_PD_ind10", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind10;

    @Column(name = "fam_PD_ind11", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind11;

    @Column(name = "fam_PD_ind12", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind12;

    @Column(name = "fam_PD_ind13", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind13;

    @Column(name = "fam_PD_ind14", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind14;

    @Column(name = "fam_PD_ind15", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind15;

    @Column(name = "fam_PD_ind16", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind16;

    @Column(name = "fam_PD_ind17", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind17;

    @Column(name = "fam_PD_ind18", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind18;

    @Column(name = "fam_PD_num", columnDefinition = "varchar", length = 128)
    private String fam_PD_num;

    @Column(name = "fam_PD_samp1", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp1;

    @Column(name = "fam_PD_samp10", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp10;

    @Column(name = "fam_PD_samp11", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp11;

    @Column(name = "fam_PD_samp12", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp12;

    @Column(name = "fam_PD_samp13", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp13;

    @Column(name = "fam_PD_samp14", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp14;

    @Column(name = "fam_PD_samp15", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp15;

    @Column(name = "fam_PD_samp16", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp16;

    @Column(name = "fam_PD_samp17", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp17;

    @Column(name = "fam_PD_samp18", columnDefinition = "varchar", length = 128)
    private String fam_PD_samp18;

    @Column(name = "fam_PD_yob1", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob1;

    @Column(name = "fam_PD_yob10", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob10;

    @Column(name = "fam_PD_yob11", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob11;

    @Column(name = "fam_PD_yob12", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob12;

    @Column(name = "fam_PD_yob13", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob13;

    @Column(name = "fam_PD_yob14", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob14;

    @Column(name = "fam_PD_yob15", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob15;

    @Column(name = "fam_PD_yob16", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob16;

    @Column(name = "fam_PD_yob17", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob17;

    @Column(name = "fam_PD_yob18", columnDefinition = "varchar", length = 128)
    private String fam_PD_yob18;

    @Column(name = "fam_PD_yod1", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod1;

    @Column(name = "fam_PD_yod10", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod10;

    @Column(name = "fam_PD_yod11", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod11;

    @Column(name = "fam_PD_yod12", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod12;

    @Column(name = "fam_PD_yod13", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod13;

    @Column(name = "fam_PD_yod14", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod14;

    @Column(name = "fam_PD_yod15", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod15;

    @Column(name = "fam_PD_yod16", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod16;

    @Column(name = "fam_PD_yod17", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod17;

    @Column(name = "fam_PD_yod18", columnDefinition = "varchar", length = 128)
    private String fam_PD_yod18;

    @Column(name = "fam_ethn_gfm", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm;

    @Column(name = "fam_ethn_gfm_as", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm_as;

    @Column(name = "fam_ethn_gfm_asoth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm_asoth;

    @Column(name = "fam_ethn_gfm_cou", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm_cou;

    @Column(name = "fam_ethn_gfm_oth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm_oth;

    @Column(name = "fam_ethn_gfm_woth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfm_woth;

    @Column(name = "fam_ethn_gfp", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp;

    @Column(name = "fam_ethn_gfp_as", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp_as;

    @Column(name = "fam_ethn_gfp_asoth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp_asoth;

    @Column(name = "fam_ethn_gfp_cou", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp_cou;

    @Column(name = "fam_ethn_gfp_oth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp_oth;

    @Column(name = "fam_ethn_gfp_woth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gfp_woth;

    @Column(name = "fam_ethn_gmm", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm;

    @Column(name = "fam_ethn_gmm_as", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm_as;

    @Column(name = "fam_ethn_gmm_asoth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm_asoth;

    @Column(name = "fam_ethn_gmm_cou", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm_cou;

    @Column(name = "fam_ethn_gmm_oth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm_oth;

    @Column(name = "fam_ethn_gmm_woth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmm_woth;

    @Column(name = "fam_ethn_gmp", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp;

    @Column(name = "fam_ethn_gmp_as", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp_as;

    @Column(name = "fam_ethn_gmp_asoth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp_asoth;

    @Column(name = "fam_ethn_gmp_cou", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp_cou;

    @Column(name = "fam_ethn_gmp_oth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp_oth;

    @Column(name = "fam_ethn_gmp_woth", columnDefinition = "varchar", length = 128)
    private String fam_ethn_gmp_woth;

    @Column(name = "fam_healthy", columnDefinition = "varchar", length = 128)
    private String fam_healthy = "1";

    @Column(name = "fam_healthy_ind1", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind1;

    @Column(name = "fam_healthy_ind10", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind10;

    @Column(name = "fam_healthy_ind11", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind11;

    @Column(name = "fam_healthy_ind12", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind12;

    @Column(name = "fam_healthy_ind13", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind13;

    @Column(name = "fam_healthy_ind14", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind14;

    @Column(name = "fam_healthy_ind15", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind15;

    @Column(name = "fam_healthy_ind16", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind16;

    @Column(name = "fam_healthy_ind17", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind17;

    @Column(name = "fam_healthy_ind18", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind18;

    @Column(name = "fam_healthy_ind19", columnDefinition = "varchar", length = 128)
    private String fam_healthy_ind19;

    @Column(name = "fam_healthy_num", columnDefinition = "varchar", length = 128)
    private String fam_healthy_num;

    @Column(name = "fam_healthy_samp1", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp1;

    @Column(name = "fam_healthy_samp10", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp10;

    @Column(name = "fam_healthy_samp11", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp11;

    @Column(name = "fam_healthy_samp12", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp12;

    @Column(name = "fam_healthy_samp13", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp13;

    @Column(name = "fam_healthy_samp14", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp14;

    @Column(name = "fam_healthy_samp15", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp15;

    @Column(name = "fam_healthy_samp16", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp16;

    @Column(name = "fam_healthy_samp17", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp17;

    @Column(name = "fam_healthy_samp18", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp18;

    @Column(name = "fam_healthy_samp19", columnDefinition = "varchar", length = 128)
    private String fam_healthy_samp19;

    @Column(name = "fam_healthy_yob1", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob1;

    @Column(name = "fam_healthy_yob10", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob10;

    @Column(name = "fam_healthy_yob11", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob11;

    @Column(name = "fam_healthy_yob12", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob12;

    @Column(name = "fam_healthy_yob13", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob13;

    @Column(name = "fam_healthy_yob14", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob14;

    @Column(name = "fam_healthy_yob15", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob15;

    @Column(name = "fam_healthy_yob16", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob16;

    @Column(name = "fam_healthy_yob17", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob17;

    @Column(name = "fam_healthy_yob18", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob18;

    @Column(name = "fam_healthy_yob19", columnDefinition = "varchar", length = 128)
    private String fam_healthy_yob19;

    @Column(name = "fam_oth", columnDefinition = "varchar", length = 128)
    private String fam_oth;

    @Column(name = "fam_oth_gd1", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd1;

    @Column(name = "fam_oth_gd10", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd10;

    @Column(name = "fam_oth_gd11", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd11;

    @Column(name = "fam_oth_gd12", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd12;

    @Column(name = "fam_oth_gd13", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd13;

    @Column(name = "fam_oth_gd14", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd14;

    @Column(name = "fam_oth_gd15", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd15;

    @Column(name = "fam_oth_gd16", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd16;

    @Column(name = "fam_oth_gd17", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd17;

    @Column(name = "fam_oth_gd18", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd18;

    @Column(name = "fam_oth_gd19", columnDefinition = "varchar", length = 128)
    private String fam_oth_gd19;

    @Column(name = "fam_oth_ind1", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind1;

    @Column(name = "fam_oth_ind10", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind10;

    @Column(name = "fam_oth_ind11", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind11;

    @Column(name = "fam_oth_ind12", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind12;

    @Column(name = "fam_oth_ind13", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind13;

    @Column(name = "fam_oth_ind14", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind14;

    @Column(name = "fam_oth_ind15", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind15;

    @Column(name = "fam_oth_ind16", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind16;

    @Column(name = "fam_oth_ind17", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind17;

    @Column(name = "fam_oth_ind18", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind18;

    @Column(name = "fam_oth_ind19", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind19;

    @Column(name = "fam_oth_num", columnDefinition = "varchar", length = 128)
    private String fam_oth_num;

    @Column(name = "fam_oth_samp1", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp1;

    @Column(name = "fam_oth_samp10", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp10;

    @Column(name = "fam_oth_samp11", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp11;

    @Column(name = "fam_oth_samp12", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp12;

    @Column(name = "fam_oth_samp13", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp13;

    @Column(name = "fam_oth_samp14", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp14;

    @Column(name = "fam_oth_samp15", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp15;

    @Column(name = "fam_oth_samp16", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp16;

    @Column(name = "fam_oth_samp17", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp17;

    @Column(name = "fam_oth_samp18", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp18;

    @Column(name = "fam_oth_samp19", columnDefinition = "varchar", length = 128)
    private String fam_oth_samp19;

    @Column(name = "fam_oth_yob1", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob1;

    @Column(name = "fam_oth_yob10", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob10;

    @Column(name = "fam_oth_yob11", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob11;

    @Column(name = "fam_oth_yob12", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob12;

    @Column(name = "fam_oth_yob13", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob13;

    @Column(name = "fam_oth_yob14", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob14;

    @Column(name = "fam_oth_yob15", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob15;

    @Column(name = "fam_oth_yob16", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob16;

    @Column(name = "fam_oth_yob17", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob17;

    @Column(name = "fam_oth_yob18", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob18;

    @Column(name = "fam_oth_yob19", columnDefinition = "varchar", length = 128)
    private String fam_oth_yob19;

    @Column(name = "fam_oth_yoso1", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso1;

    @Column(name = "fam_oth_yoso10", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso10;

    @Column(name = "fam_oth_yoso11", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso11;

    @Column(name = "fam_oth_yoso12", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso12;

    @Column(name = "fam_oth_yoso13", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso13;

    @Column(name = "fam_oth_yoso14", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso14;

    @Column(name = "fam_oth_yoso15", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso15;

    @Column(name = "fam_oth_yoso16", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso16;

    @Column(name = "fam_oth_yoso17", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso17;

    @Column(name = "fam_oth_yoso18", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso18;

    @Column(name = "fam_oth_yoso19", columnDefinition = "varchar", length = 128)
    private String fam_oth_yoso19;

    @Column(name = "fam_othn", columnDefinition = "varchar", length = 128)
    private String fam_othn;

    @Column(name = "fam_othn_dis1", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis1;

    @Column(name = "fam_othn_dis10", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis10;

    @Column(name = "fam_othn_dis11", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis11;

    @Column(name = "fam_othn_dis12", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis12;

    @Column(name = "fam_othn_dis13", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis13;

    @Column(name = "fam_othn_dis14", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis14;

    @Column(name = "fam_othn_dis15", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis15;

    @Column(name = "fam_othn_dis16", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis16;

    @Column(name = "fam_othn_dis17", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis17;

    @Column(name = "fam_othn_dis18", columnDefinition = "varchar", length = 128)
    private String fam_othn_dis18;

    @Column(name = "fam_othn_gd1", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1;

    @Column(name = "fam_othn_gd10", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd10;

    @Column(name = "fam_othn_gd11", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd11;

    @Column(name = "fam_othn_gd12", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd12;

    @Column(name = "fam_othn_gd13", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd13;

    @Column(name = "fam_othn_gd14", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd14;

    @Column(name = "fam_othn_gd15", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd15;

    @Column(name = "fam_othn_gd16", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd16;

    @Column(name = "fam_othn_gd17", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd17;

    @Column(name = "fam_othn_gd18", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd18;

    @Column(name = "fam_othn_ind1", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind1;

    @Column(name = "fam_othn_ind10", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind10;

    @Column(name = "fam_othn_ind11", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind11;

    @Column(name = "fam_othn_ind12", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind12;

    @Column(name = "fam_othn_ind13", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind13;

    @Column(name = "fam_othn_ind14", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind14;

    @Column(name = "fam_othn_ind15", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind15;

    @Column(name = "fam_othn_ind16", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind16;

    @Column(name = "fam_othn_ind17", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind17;

    @Column(name = "fam_othn_ind18", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind18;

    @Column(name = "fam_othn_num", columnDefinition = "varchar", length = 128)
    private String fam_othn_num;

    @Column(name = "fam_othn_samp1", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp1;

    @Column(name = "fam_othn_samp10", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp10;

    @Column(name = "fam_othn_samp11", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp11;

    @Column(name = "fam_othn_samp12", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp12;

    @Column(name = "fam_othn_samp13", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp13;

    @Column(name = "fam_othn_samp14", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp14;

    @Column(name = "fam_othn_samp15", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp15;

    @Column(name = "fam_othn_samp16", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp16;

    @Column(name = "fam_othn_samp17", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp17;

    @Column(name = "fam_othn_samp18", columnDefinition = "varchar", length = 128)
    private String fam_othn_samp18;

    @Column(name = "fam_othn_yob1", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob1;

    @Column(name = "fam_othn_yob10", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob10;

    @Column(name = "fam_othn_yob11", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob11;

    @Column(name = "fam_othn_yob12", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob12;

    @Column(name = "fam_othn_yob13", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob13;

    @Column(name = "fam_othn_yob14", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob14;

    @Column(name = "fam_othn_yob15", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob15;

    @Column(name = "fam_othn_yob16", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob16;

    @Column(name = "fam_othn_yob17", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob17;

    @Column(name = "fam_othn_yob18", columnDefinition = "varchar", length = 128)
    private String fam_othn_yob18;

    @Column(name = "fam_othn_yod1", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod1;

    @Column(name = "fam_othn_yod10", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod10;

    @Column(name = "fam_othn_yod11", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod11;

    @Column(name = "fam_othn_yod12", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod12;

    @Column(name = "fam_othn_yod13", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod13;

    @Column(name = "fam_othn_yod14", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod14;

    @Column(name = "fam_othn_yod15", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod15;

    @Column(name = "fam_othn_yod16", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod16;

    @Column(name = "fam_othn_yod17", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod17;

    @Column(name = "fam_othn_yod18", columnDefinition = "varchar", length = 128)
    private String fam_othn_yod18;

    @ExcelColumn(name = "Genotyping Done?-S", map_from = {"No", "Yes and FOUND to have likely pathogenic PD gene mutation(s)",
            "Yes and NOT found to have likely pathogenic PD gene mutation(s)"}, map_to = {"0", "2", "3"})
    @Column(name = "genotyp", columnDefinition = "varchar", length = 128)
    private String genotyp;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"PRKN"}, map_to = {"1"})
    @Column(name = "genotyp_gene_0", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_0;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"PINK1"}, map_to = {"2"})
    @Column(name = "genotyp_gene_1", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_1;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"DJ-1"}, map_to = {"3"})
    @Column(name = "genotyp_gene_2", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_2;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"LRRK2"}, map_to = {"4"})
    @Column(name = "genotyp_gene_3", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_3;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"SNCA"}, map_to = {"5"})
    @Column(name = "genotyp_gene_4", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_4;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"GBA"}, map_to = {"6"})
    @Column(name = "genotyp_gene_5", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_5;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"VPS35"}, map_to = {"7"})
    @Column(name = "genotyp_gene_6", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_6;

    @ExcelColumn(name = "If found to have likely pathogenic mutations, please specify which PD gene:-T", map_from = {"Others"}, map_to = {"8"})
    @Column(name = "genotyp_gene_7", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_7;

    @Column(name = "genotyp_gene_oth", columnDefinition = "varchar", length = 128)
    private String genotyp_gene_oth;

    @Column(name = "genotyp_platf_0", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_0;

    @Column(name = "genotyp_platf_1", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_1;

    @Column(name = "genotyp_platf_2", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_2;

    @Column(name = "genotyp_platf_3", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_3;

    @Column(name = "genotyp_platf_4", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_4;

    @Column(name = "genotyp_platf_5", columnDefinition = "varchar", length = 128)
    private String genotyp_platf_5;

    @Column(name = "yosc_year", columnDefinition = "varchar", length = 128)
    private String yoscYear;

    @Column(name = "fam_PD_sub_id1", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id1;

    @Column(name = "fam_PD_sub_id10", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id10;

    @Column(name = "fam_PD_sub_id11", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id11;

    @Column(name = "fam_PD_sub_id12", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id12;

    @Column(name = "fam_PD_sub_id13", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id13;

    @Column(name = "fam_PD_sub_id14", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id14;

    @Column(name = "fam_PD_sub_id15", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id15;

    @Column(name = "fam_PD_sub_id16", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id16;

    @Column(name = "fam_PD_sub_id17", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id17;

    @Column(name = "fam_PD_sub_id18", columnDefinition = "varchar", length = 128)
    private String fam_PD_sub_id18;

    @Column(name = "fam_oth_sub_id1", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id1;

    @Column(name = "fam_oth_sub_id10", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id10;

    @Column(name = "fam_oth_sub_id11", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id11;

    @Column(name = "fam_oth_sub_id12", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id12;

    @Column(name = "fam_oth_sub_id13", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id13;

    @Column(name = "fam_oth_sub_id14", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id14;

    @Column(name = "fam_oth_sub_id15", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id15;

    @Column(name = "fam_oth_sub_id16", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id16;

    @Column(name = "fam_oth_sub_id17", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id17;

    @Column(name = "fam_oth_sub_id18", columnDefinition = "varchar", length = 128)
    private String fam_oth_sub_id18;

    @Column(name = "fam_othn_sub_id1", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id1;

    @Column(name = "fam_othn_sub_id10", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id10;

    @Column(name = "fam_othn_sub_id11", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id11;

    @Column(name = "fam_othn_sub_id12", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id12;

    @Column(name = "fam_othn_sub_id13", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id13;

    @Column(name = "fam_othn_sub_id14", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id14;

    @Column(name = "fam_othn_sub_id15", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id15;

    @Column(name = "fam_othn_sub_id16", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id16;

    @Column(name = "fam_othn_sub_id17", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id17;

    @Column(name = "fam_othn_sub_id18", columnDefinition = "varchar", length = 128)
    private String fam_othn_sub_id18;

    @Column(name = "fam_othn_gd1a", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a;

    @Column(name = "fam_othn_gd1a0", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a0;

    @Column(name = "fam_othn_gd1a1", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a1;

    @Column(name = "fam_othn_gd1a2", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a2;

    @Column(name = "fam_othn_gd1a3", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a3;

    @Column(name = "fam_othn_gd1a4", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a4;

    @Column(name = "fam_othn_gd1a5", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a5;

    @Column(name = "fam_othn_gd1a6", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a6;

    @Column(name = "fam_othn_gd1a7", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a7;

    @Column(name = "fam_othn_gd1a8", columnDefinition = "varchar", length = 128)
    private String fam_othn_gd1a8;

    @Column(name = "fam_PD_ind_other1", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other1;


    @Column(name = "fam_PD_ind_other10", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other10;

    @Column(name = "fam_PD_ind_other11", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other11;

    @Column(name = "fam_PD_ind_other12", columnDefinition = "varchar", length = 128)
    private String famPDIndOther12;

    @Column(name = "fam_PD_ind_other13", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other13;

    @Column(name = "fam_PD_ind_other14", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other14;

    @Column(name = "fam_PD_ind_other15", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other15;

    @Column(name = "fam_PD_ind_other16", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other16;

    @Column(name = "fam_PD_ind_other17", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other17;

    @Column(name = "fam_PD_ind_other18", columnDefinition = "varchar", length = 128)
    private String fam_PD_ind_other18;

    @Column(name = "fam_oth_ind_other1", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other1;

    @Column(name = "fam_oth_ind_other10", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other10;

    @Column(name = "fam_oth_ind_other11", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other11;

    @Column(name = "fam_oth_ind_other12", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other12;

    @Column(name = "fam_oth_ind_other13", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other13;

    @Column(name = "fam_oth_ind_other14", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other14;

    @Column(name = "fam_oth_ind_other15", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other15;

    @Column(name = "fam_oth_ind_other16", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other16;

    @Column(name = "fam_oth_ind_other17", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other17;

    @Column(name = "fam_oth_ind_other18", columnDefinition = "varchar", length = 128)
    private String fam_oth_ind_other18;

    @Column(name = "fam_othn_ind_other1", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other1;

    @Column(name = "fam_othn_ind_other10", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other10;

    @Column(name = "fam_othn_ind_other11", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other11;

    @Column(name = "fam_othn_ind_other12", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other12;

    @Column(name = "fam_othn_ind_other13", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other13;

    @Column(name = "fam_othn_ind_other14", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other14;

    @Column(name = "fam_othn_ind_other15", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other15;

    @Column(name = "fam_othn_ind_other16", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other16;

    @Column(name = "fam_othn_ind_other17", columnDefinition = "varchar", length = 128)
    private String fam_othn_ind_other17;

    @Column(name = "fam_othn_ind_other18", columnDefinition = "varchar", length = 128)
    private String famOthnIndOther18;

    @ExcelColumn(name = "If positive, please specify for the index case, which family member(s) -R" +
            "has/have neurodegenerative/movement disorders with the-R")
    @Column(name = "famh_details_of_ndd", columnDefinition = "varchar", length = 128)
    private String famhDetailsOfNdd;

    @ExcelColumn(name = "If positive, please specify  for the index case, which family member(s) has/have PD and their -P" +
            "relationships (maternal or paternal side)-P")
    @Column(name = "famh_details_of_pd", columnDefinition = "varchar", length = 128)
    private String famhDetailsOfPd;

    @ExcelColumn(name = "Family history of other neurodegenerative or movement disorders-Q",
    map_from = {"Positive (additional samples available)","Positive (no additional samples available)","Negative","Unknown"},
    map_to = {"0", "1", "2", "9999"})
    @Column(name = "famh_of_ndd", columnDefinition = "varchar", length = 128)
    private String famhOfNdd;

    @ExcelColumn(name = "Family history of PD-O", map_from = {"Positive (additional samples available)",
            "Positive (no additional samples available)", "Negative", "Unknown"}, map_to = {"0", "1", "2", "9999"})
    @Column(name = "famh_of_pd", columnDefinition = "varchar", length = 128)
    private String famhOfPd;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

