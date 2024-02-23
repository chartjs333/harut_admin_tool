package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf5;
import org.springframework.data.jpa.repository.JpaRepository;


public interface eCRF5Repository extends JpaRepository<Medical2Ecrf5, Integer> {
	Medical2Ecrf5 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
