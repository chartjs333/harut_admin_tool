package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "ecrf20")
@Table(name = "medical_2_ecrf20",  uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
@NoArgsConstructor
public class Medical2Ecrf20 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf20_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf20_id_seq", sequenceName = "medical_2_ecrf20_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
    private int id;


    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "file", columnDefinition = "bytea")
    @Basic(fetch = FetchType.LAZY)
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
