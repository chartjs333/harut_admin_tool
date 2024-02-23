package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf10;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO RENAME THIS into IMedical2EcrfDAO10
public interface eCRF10Repository extends JpaRepository<Medical2Ecrf10, Integer> {
	Medical2Ecrf10 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
