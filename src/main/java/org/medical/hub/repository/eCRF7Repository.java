package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf7;
import org.springframework.data.jpa.repository.JpaRepository;


public interface eCRF7Repository extends JpaRepository<Medical2Ecrf7, Integer> {
	Medical2Ecrf7 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
