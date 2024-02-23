package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medical.hub.annotation.ExcelColumn;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf4", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf4 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf4_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf4_id_seq", sequenceName = "medical_2_ecrf4_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @ExcelColumn(name = "Appendicectomy-J", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_app", columnDefinition = "varchar", length = 128)
    private String envApp;

    @ExcelColumn(name = "Chronic Hep B infection-M", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_ch_Bi", columnDefinition = "varchar", length = 128)
    private String envChBi;

    @ExcelColumn(name = "Chronic Hep C infection-N", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_ch_Ci", columnDefinition = "varchar", length = 128)
    private String envChCi;

    @ExcelColumn(name = "Chronic renal failure-O", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_crf", columnDefinition = "varchar", length = 128)
    private String envCrf;

    @ExcelColumn(name = "Diabetes Mellitus-E", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_dm", columnDefinition = "varchar", length = 128)
    private String envDm;

    @ExcelColumn(name = "Gout-F", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_gout", columnDefinition = "varchar", length = 128)
    private String envGout;

    @ExcelColumn(name = "Helicobacter pylori infection-G", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_hpi", columnDefinition = "varchar", length = 128)
    private String envHpi;

    @ExcelColumn(name = "Irritable bowel syndrome-H", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_ibs", columnDefinition = "varchar", length = 128)
    private String envIbs;

    @ExcelColumn(name = "Inflammatory bowel disease-I", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_inbd", columnDefinition = "varchar", length = 128)
    private String envInbd;

    @ExcelColumn(name = "Melanoma-L", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_mel", columnDefinition = "varchar", length = 128)
    private String envMel;

    @ExcelColumn(name = "Vagotomy-K", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "0", "9999"})
    @Column(name = "env_vag", columnDefinition = "varchar", length = 128)
    private String envVag;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
