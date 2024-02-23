package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf7", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf7 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf7_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf7_id_seq", sequenceName = "medical_2_ecrf7_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Column(name = "affectedunaffected_proband", columnDefinition = "varchar", length = 128)
    private String affectedunaffectedProband;

    @Column(name = "code", columnDefinition = "varchar", length = 128)
    private String code;

    @Column(name = "dna_sample_shipment", columnDefinition = "varchar", length = 128)
    private String dnaSampleShipment;

    @Column(name = "esrf_completed", columnDefinition = "varchar", length = 128)
    private String esrfCompleted;

    @Column(name = "gda", columnDefinition = "varchar", length = 128)
    private String gda;

    @Column(name = "medical2_id", columnDefinition = "varchar", length = 128)
    private String medical2Id;

    @Column(name = "local_casefamily_id", columnDefinition = "varchar", length = 128)
    private String localCasefamilyId;

    @Column(name = "name_of_provider", columnDefinition = "varchar", length = 128)
    private String nameOfProvider;

    @Column(name = "performing_center_gda", columnDefinition = "varchar", length = 128)
    private String performingCenterGda;

    @Column(name = "performing_center_wgs", columnDefinition = "varchar", length = 128)
    private String performingCenterWgs;

    @Column(name = "prioritized_for_wgs", columnDefinition = "varchar", length = 128)
    private String prioritizedForWgs;

    @Column(name = "result_of_gda", columnDefinition = "varchar", length = 128)
    private String resultOfGda;

    @Column(name = "wgs", columnDefinition = "varchar", length = 128)
    private String wgs;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
