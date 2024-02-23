package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF2Repository extends JpaRepository<Medical2Ecrf2, Integer> {

    Medical2Ecrf2 findBySurveyTwoId(String surveyTwoId);

    Long deleteBySurveyTwoId(String surveyTwoId);
}
