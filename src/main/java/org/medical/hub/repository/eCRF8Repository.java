package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf8;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF8Repository extends JpaRepository<Medical2Ecrf8, Integer> {
	Medical2Ecrf8 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
