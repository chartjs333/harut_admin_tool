package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medical.hub.annotation.ExcelColumn;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf3", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf3 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf3_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf3_id_seq", sequenceName = "medical_2_ecrf3_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Column(name = "PD_med_ac", columnDefinition = "varchar", length = 128)
    private String PD_med_ac;

    @Column(name = "PD_med_am", columnDefinition = "varchar", length = 128)
    private String PD_med_am;

    @Column(name = "PD_med_comt", columnDefinition = "varchar", length = 128)
    private String PD_med_comt;

    @Column(name = "PD_med_dopag", columnDefinition = "varchar", length = 128)
    private String PD_med_dopag;

    @Column(name = "PD_med_ldopa", columnDefinition = "varchar", length = 128)
    private String PD_med_ldopa;

    @Column(name = "PD_med_mao_b", columnDefinition = "varchar", length = 128)
    private String PD_med_mao_b;

    @Column(name = "apom_inf1", columnDefinition = "varchar", length = 128)
    private String apom_inf1;

    @Column(name = "apom_inf2", columnDefinition = "varchar", length = 128)
    private String apom_inf2;

    @Column(name = "brain_ct2", columnDefinition = "varchar", length = 128)
    private String brain_ct2;

    @Column(name = "brain_ct3", columnDefinition = "varchar", length = 128)
    private String brain_ct3;

    @Column(name = "brain_ct4", columnDefinition = "varchar", length = 128)
    private String brain_ct4;

    @Column(name = "brain_mri2", columnDefinition = "varchar", length = 128)
    private String brain_mri2;

    @Column(name = "brain_mri3", columnDefinition = "varchar", length = 128)
    private String brain_mri3;

    @Column(name = "brain_mri4", columnDefinition = "varchar", length = 128)
    private String brain_mri4;

    @ExcelColumn(name = "CISI-PD (Motor) score-BG", value = "cisi2")
    @Column(name = "cisi2", columnDefinition = "varchar", length = 128)
    private String cisi2;

    @ExcelColumn(name = "CISI-PD (Disability) score-BH", value = "cisi3")
    @Column(name = "cisi3", columnDefinition = "varchar", length = 128)
    private String cisi3;

    @ExcelColumn(name = "CISI-PD (Motor complications) score-BI", value = "cisi4")
    @Column(name = "cisi4", columnDefinition = "varchar", length = 128)
    private String cisi4;

    @ExcelColumn(name = "CISI-PD (Cognitive) score-BJ", value = "cisi5")
    @Column(name = "cisi5", columnDefinition = "varchar", length = 128)
    private String cisi5;

    @ExcelColumn(name = "CISI-PD (Total) score-BK", value = "cisi6")
    @Column(name = "cisi6", columnDefinition = "varchar", length = 128)
    private String cisi6;

    @ExcelColumn(name = "Year of assessment 5-BL", value = "cisi7")
    @Column(name = "cisi7", columnDefinition = "varchar", length = 128)
    private String cisi7;

    @Column(name = "dat_pet2", columnDefinition = "varchar", length = 128)
    private String datPet2;

    @Column(name = "dat_pet3", columnDefinition = "varchar", length = 128)
    private String datPet3;

    @Column(name = "dat_pet4", columnDefinition = "varchar", length = 128)
    private String datPet4;

    @Column(name = "first_ms1_0", columnDefinition = "varchar", length = 128)
    private String firstMs10;

    @Column(name = "first_ms1_1", columnDefinition = "varchar", length = 128)
    private String firstMs11;

    @Column(name = "first_ms1_2", columnDefinition = "varchar", length = 128)
    private String first_ms1_2;

    @Column(name = "first_ms1_3", columnDefinition = "varchar", length = 128)
    private String first_ms1_3;

    @Column(name = "first_ms1_4", columnDefinition = "varchar", length = 128)
    private String first_ms1_4;

    @Column(name = "first_ms1_5", columnDefinition = "varchar", length = 128)
    private String first_ms1_5;

    @Column(name = "first_ms1_6", columnDefinition = "varchar", length = 128)
    private String first_ms1_6;

    @Column(name = "first_ms1_7", columnDefinition = "varchar", length = 128)
    private String first_ms1_7;

    @Column(name = "first_ms2", columnDefinition = "varchar", length = 128)
    private String first_ms2;

    @Column(name = "funct_neuros1", columnDefinition = "varchar", length = 128)
    private String funct_neuros1;

    @Column(name = "funct_neuros2", columnDefinition = "varchar", length = 128)
    private String funct_neuros2;

    @ExcelColumn(name = "Hoehn & Yahr stage-AW", map_from = {"1", "1.5", "2", "2.5", "3", "4", "5"},
            map_to = {"1", "2", "3", "4", "5", "6", "7"} )
    @Column(name = "hy2", columnDefinition = "varchar", length = 128)
    private String hy2;

    @ExcelColumn(name = "Year of assessment 1-AX", value = "hy3")
    @Column(name = "hy3", columnDefinition = "varchar", length = 128)
    private String hy3;

    @Column(name = "ldopa_cig1", columnDefinition = "varchar", length = 128)
    private String ldopaCig1;

    @Column(name = "ldopa_cig2", columnDefinition = "varchar", length = 128)
    private String ldopaCig2;

    @Column(name = "ledd", columnDefinition = "varchar", length = 128)
    private String ledd;

    @ExcelColumn(name = "MDS-UPDRS part III score-BB")
    @Column(name = "mds_updrs2", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs2;

    @ExcelColumn(name = "State of assessment 1-AZ", value = "mds_updrs3",
            map_from = {"ON-medication", "OFF-medication", "Not specified"},
            map_to = {"1", "2", "3"})
    @Column(name = "mds_updrs3", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs3;

    @Column(name = "mds_updrs4", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs4;

    @Column(name = "mds_updrs5", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs5;

    @Column(name = "mds_updrs6", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs6;

    @ExcelColumn(name = "Year of assessment 3-BD", value = "mds_updrs7")
    @Column(name = "mds_updrs7", columnDefinition = "varchar", length = 128)
    private String mdsUpdrs7;

    @Column(name = "mibg2", columnDefinition = "varchar", length = 128)
    private String mibg2;

    @Column(name = "mibg3", columnDefinition = "varchar", length = 128)
    private String mibg3;

    @Column(name = "mibg4", columnDefinition = "varchar", length = 128)
    private String mibg4;

    @Column(name = "mmse2", columnDefinition = "varchar", length = 128)
    private String mmse2;

    @Column(name = "mmse3", columnDefinition = "varchar", length = 128)
    private String mmse3;

    @ExcelColumn(name = "MoCA score-BE", value = "moca2")
    @Column(name = "moca2", columnDefinition = "varchar", length = 128)
    private String moca2;

    @ExcelColumn(name = "Year of assessment 4-BF", value = "moca3")
    @Column(name = "moca3", columnDefinition = "varchar", length = 128)
    private String moca3;

    @ExcelColumn(name = "Action tremor-G", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_at1", columnDefinition = "varchar", length = 128)
    private String msAt1;

//    @ExcelColumn(name = "Occurrence", value = "ms_at2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_at2", columnDefinition = "varchar", length = 128)
    private String msAt2;

    @ExcelColumn(name = "Dystonia-O", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_d1", columnDefinition = "varchar", length = 128)
    private String msD1;

    @ExcelColumn(name = "Occurrence 3-P", value = "ms_d2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_d2", columnDefinition = "varchar", length = 128)
    private String msD2;

    @ExcelColumn(name = "Gait difficulty-J", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_gd1", columnDefinition = "varchar", length = 128)
    private String msGd1;

//    @ExcelColumn(name = "Occurrence", value = "ms_gd2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_gd2", columnDefinition = "varchar", length = 128)
    private String msGd2;

    @ExcelColumn(name = "Gait freezing-K", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_gf1", columnDefinition = "varchar", length = 128)
    private String msGf1;

    @ExcelColumn(name = "Occurrence 1-L", value = "ms_gf2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_gf2", columnDefinition = "varchar", length = 128)
    private String msGf2;

    @ExcelColumn(name = "Levodopa-induced dyskinesias-T", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_ldopa_dys1", columnDefinition = "varchar", length = 128)
    private String msLdopaDys1;

    @ExcelColumn(name = "Year of first occurrence 2-U", value = "ms_ldopa_dys2")
    @Column(name = "ms_ldopa_dys2", columnDefinition = "varchar", length = 128)
    private String msLdopaDys2;

    @ExcelColumn(name = "Clear favourable response-Q", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_med", columnDefinition = "varchar", length = 128)
    private String msMed;

    @ExcelColumn(name = "Motor fluctuations-R", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_mf1", columnDefinition = "varchar", length = 128)
    private String msMf1;

    @ExcelColumn(name = "Year of first occurrence 1-S", value = "ms_mf2")
    @Column(name = "ms_mf2", columnDefinition = "varchar", length = 128)
    private String msMf2;

    @ExcelColumn(name = "Postural instability / Falls-M", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_pi1", columnDefinition = "varchar", length = 128)
    private String msPi1;

    @ExcelColumn(name = "Occurrence 2-N", value = "ms_pi2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_pi2", columnDefinition = "varchar", length = 128)
    private String msPi2;

    @ExcelColumn(name = "Postural tremor-F", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_pt1", columnDefinition = "varchar", length = 128)
    private String msPt1;

//    @ExcelColumn(name = "Occurrence", value = "ms_pt2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_pt2", columnDefinition = "varchar", length = 128)
    private String msPt2;

    @ExcelColumn(name = "Rigidity/stiffness-I", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_rs1", columnDefinition = "varchar", length = 128)
    private String msRs1;

//    @ExcelColumn(name = "Occurrence", value = "ms_rs2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_rs2", columnDefinition = "varchar", length = 128)
    private String msRs2;

    @ExcelColumn(name = "Resting Tremor-E", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_rt1", columnDefinition = "varchar", length = 128)
    private String msRt1;

//    @ExcelColumn(name = "Occurrence", value = "ms_rt2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_rt2", columnDefinition = "varchar", length = 128)
    private String msRt2;


    @ExcelColumn(name = "Sleep benefit-V", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_sb1", columnDefinition = "varchar", length = 128)
    private String msSb1;

    @ExcelColumn(name = "Slow movements-H", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_sm1", columnDefinition = "varchar", length = 128)
    private String msSm1;

//    @ExcelColumn(name = "Occurrence", value = "ms_sm2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
//            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "ms_sm2", columnDefinition = "varchar", length = 128)
    private String msSm2;

    @ExcelColumn(name = "Upper motor neuron signs-W", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "ms_umns", columnDefinition = "varchar", length = 128)
    private String msUmns;

    @Column(name = "ms_yoa", columnDefinition = "varchar", length = 128)
    private String msYoa;

    @ExcelColumn(name = "RBD-X", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_RBD1", columnDefinition = "varchar", length = 128)
    private String nmsRBD1;

    @ExcelColumn(name = "Occurrence 4-Y", value = "nms_RBD2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_RBD2", columnDefinition = "varchar", length = 128)
    private String nmsRBD2;


    @ExcelColumn(name = "Anxiety-AC", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_anx1", columnDefinition = "varchar", length = 128)
    private String nmsAnx1;

    @Column(name = "nms_anx2", columnDefinition = "varchar", length = 128)
    private String nmsAnx2;

    @ExcelColumn(name = "Constipation-AL", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_con1", columnDefinition = "varchar", length = 128)
    private String nmsCon1;

    @ExcelColumn(name = "Occurrence 8-AM", value = "nms_con2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_con2", columnDefinition = "varchar", length = 128)
    private String nmsCon2;

    @ExcelColumn(name = "Dementia-AF", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_dem1", columnDefinition = "varchar", length = 128)
    private String nmsDem1;

    @ExcelColumn(name = "Occurrence 5-AE", value = "nms_dem2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_dem2", columnDefinition = "varchar", length = 128)
    private String nmsDem2;

    @ExcelColumn(name = "Year of first occurrence 3-AH", value = "nms_dem3")
    @Column(name = "nms_dem3", columnDefinition = "varchar", length = 128)
    private String nmsDem3;

    @ExcelColumn(name = "Depression symptoms-AB", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_dp1", columnDefinition = "varchar", length = 128)
    private String nmsDp1;

    @Column(name = "nms_dp2", columnDefinition = "varchar", length = 128)
    private String nmsDp2;

    @ExcelColumn(name = "Excessive daytime sleepiness-AA", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_eds1", columnDefinition = "varchar", length = 128)
    private String nmsEds1;

    @Column(name = "nms_eds2", columnDefinition = "varchar", length = 128)
    private String nmsEds2;

    @ExcelColumn(name = "Hyposmia-AS", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_hyp1", columnDefinition = "varchar", length = 128)
    private String nmsHyp1;

    @Column(name = "nms_hyp2", columnDefinition = "varchar", length = 128)
    private String nmsHyp2;

    @ExcelColumn(name = "Impulse control disorders-AK", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_icd1", columnDefinition = "varchar", length = 128)
    private String nmsIcd1;

    @Column(name = "nms_icd2", columnDefinition = "varchar", length = 128)
    private String nmsIcd2;

    @ExcelColumn(name = "Insomnia-Z", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_ins1", columnDefinition = "varchar", length = 128)
    private String nmsIns1;

    @Column(name = "nms_ins2", columnDefinition = "varchar", length = 128)
    private String nmsIns2;

    @ExcelColumn(name = "Mild cognitive impairment-AD", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_mci1", columnDefinition = "varchar", length = 128)
    private String nmsMci1;

    @ExcelColumn(name = "Occurrence 5-AE", value = "nms_mci2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_mci2", columnDefinition = "varchar", length = 128)
    private String nmsMci2;

    @ExcelColumn(name = "Orthostatic hypotension-AP", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_ogh1", columnDefinition = "varchar", length = 128)
    private String nmsOgh1;

    @ExcelColumn(name = "Occurrence 10-AQ", value = "nms_ogh2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_ogh2", columnDefinition = "varchar", length = 128)
    private String nmsOgh2;

    @ExcelColumn(name = "Presence of Atypical Features-AU", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_oth_yes_no", columnDefinition = "varchar", length = 128)
    private String nmsOthYesNo;

    @ExcelColumn(name = "If yes, specify atypical features-AV", value = "nms_oth")
    @Column(name = "nms_oth", columnDefinition = "varchar", length = 128)
    private String nmsOth;

    @ExcelColumn(name = "Pain-AR", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_pain1", columnDefinition = "varchar", length = 128)
    private String nmsPain1;

    @Column(name = "nms_pain2", columnDefinition = "varchar", length = 128)
    private String nmsPain2;

    @ExcelColumn(name = "Urinary dysfunction-AN", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_ud1", columnDefinition = "varchar", length = 128)
    private String nmsUd1;

    @ExcelColumn(name = "Occurrence 9-AM", value = "nms_ud2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_ud2", columnDefinition = "varchar", length = 128)
    private String nmsUd2;

    @ExcelColumn(name = "Underweight-AT", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_uw1", columnDefinition = "varchar", length = 128)
    private String nmsUw1;

    @Column(name = "nms_uw2", columnDefinition = "varchar", length = 128)
    private String nmsUw2;

    @ExcelColumn(name = "Visual hallucinations-AI", map_from = {"Yes", "No", "Unknown"}, map_to = {"0", "1", "9999"})
    @Column(name = "nms_vh1", columnDefinition = "varchar", length = 128)
    private String nmsVh1;

    @ExcelColumn(name = "Occurrence 7-AJ", value = "nms_vh2", map_from = {"before or at the time of PD diagnosis", "during the disease course",
            "unknown time course of occurence"}, map_to = {"1", "2", "3"})
    @Column(name = "nms_vh2", columnDefinition = "varchar", length = 128)
    private String nmsVh2;

    @Column(name = "nms_yoa", columnDefinition = "varchar", length = 128)
    private String nmsYoa;

    @Column(name = "nmsq2", columnDefinition = "varchar", length = 128)
    private String nmsq2;

    @Column(name = "nmsq3", columnDefinition = "varchar", length = 128)
    private String nmsq3;

    @Column(name = "nmss2", columnDefinition = "varchar", length = 128)
    private String nmss2;

    @Column(name = "nmss3", columnDefinition = "varchar", length = 128)
    private String nmss3;

    @Column(name = "oth2", columnDefinition = "varchar", length = 128)
    private String oth2;

    @Column(name = "oth3", columnDefinition = "varchar", length = 128)
    private String oth3;

    @Column(name = "oth4", columnDefinition = "varchar", length = 128)
    private String oth4;

    @Column(name = "pdq2", columnDefinition = "varchar", length = 128)
    private String pdq2;

    @Column(name = "pdq3", columnDefinition = "varchar", length = 128)
    private String pdq3;

    @Column(name = "polysom_rbd2", columnDefinition = "varchar", length = 128)
    private String polysom_rbd2;

    @Column(name = "polysom_rbd3", columnDefinition = "varchar", length = 128)
    private String polysom_rbd3;

    @Column(name = "polysom_rbd4", columnDefinition = "varchar", length = 128)
    private String polysom_rbd4;

    @Column(name = "scopa_aut2", columnDefinition = "varchar", length = 128)
    private String scopa_aut2;

    @Column(name = "scopa_aut3", columnDefinition = "varchar", length = 128)
    private String scopa_aut3;

    @Column(name = "smell2", columnDefinition = "varchar", length = 128)
    private String smell2;

    @Column(name = "smell3", columnDefinition = "varchar", length = 128)
    private String smell3;

    @Column(name = "smell4", columnDefinition = "varchar", length = 128)
    private String smell4;

    @Column(name = "transcr_son2", columnDefinition = "varchar", length = 128)
    private String transcr_son2;

    @Column(name = "transcr_son3", columnDefinition = "varchar", length = 128)
    private String transcr_son3;

    @Column(name = "transcr_son4", columnDefinition = "varchar", length = 128)
    private String transcr_son4;

    @Column(name = "treatm_yoa", columnDefinition = "varchar", length = 128)
    private String treatm_yoa;

    @ExcelColumn(name = "UPDRS part III score-AY", value = "updrs2")
    @Column(name = "updrs2", columnDefinition = "varchar", length = 128)
    private String updrs2;

    @ExcelColumn(name = "State of assessment 1-AZ", map_from = {"ON-medication", "OFF-medication", "Not specified"},
            map_to = {"1", "2", "3"})
    @Column(name = "updrs3", columnDefinition = "varchar", length = 128)
    private String updrs3;

    @ExcelColumn(name = "Year of assessment 2-BA", value = "updrs4")
    @Column(name = "updrs4", columnDefinition = "varchar", length = 128)
    private String updrs4;

    @Column(name = "updrs5", columnDefinition = "varchar", length = 128)
    private String updrs5;

    @Column(name = "updrs6", columnDefinition = "varchar", length = 128)
    private String updrs6;

    @Column(name = "updrs7", columnDefinition = "varchar", length = 128)
    private String updrs7;

    @Column(name = "typical_or_atypical", columnDefinition = "varchar", length = 128)
    private String typicalOrAtypical;

    @Column(name = "specify_atypical", columnDefinition = "varchar", length = 128)
    private String specifyAtypical;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}