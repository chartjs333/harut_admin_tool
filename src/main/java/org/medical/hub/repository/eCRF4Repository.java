package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf4;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF4Repository extends JpaRepository<Medical2Ecrf4, Integer> {
	Medical2Ecrf4 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
