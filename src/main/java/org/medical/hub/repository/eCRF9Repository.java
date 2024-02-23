package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf9;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO RENAME THIS into IMedical2EcrfDAO9
public interface eCRF9Repository extends JpaRepository<Medical2Ecrf9, Integer> {
	Medical2Ecrf9 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
