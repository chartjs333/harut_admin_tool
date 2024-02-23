package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface eCRF1Repository extends JpaRepository<Medical2Ecrf1, Integer>, JpaSpecificationExecutor {

    Medical2Ecrf1 findBySurveyTwoId(String surveyTwoId);

    List<Medical2Ecrf1> findByPatID(String patientId);

    @Query(nativeQuery = true, value = "SELECT distinct e1.pat_ID  FROM medical_2_ecrf1 e1")
    List<String> getDistinctPatID();
}
