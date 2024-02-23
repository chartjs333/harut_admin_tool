package org.medical.hub.repository;

import org.medical.hub.models.Medical2SampleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Medical2SampleInfoRepository extends JpaRepository<Medical2SampleInfo, Integer> {

  Medical2SampleInfo findByPatientId(String patientId);

}
