package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf20;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF20Repository extends JpaRepository<Medical2Ecrf20, Integer> {

    Medical2Ecrf20 findBySurveyTwoId(String surveyTwoId);
    Long deleteBySurveyTwoId(String surveyTwoId);

}
