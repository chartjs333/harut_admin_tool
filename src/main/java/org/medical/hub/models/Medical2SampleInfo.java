package org.medical.hub.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "medical_2_sample_info")
public class Medical2SampleInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "patient_id", unique = true, nullable = false)
  private String patientId;

  @Column(name = "medical2_id",  nullable = false)
  private String medical2Id;

  @Column(name = "medical2_fam_id",  nullable = false)
  private String medical2FamId;

  @Column(name = "survey_two_id")
  private String surveyTwoId;

}
