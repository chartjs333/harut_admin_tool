package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf2;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMedical2EcrfDAO2 extends JpaRepository<Medical2Ecrf2, Integer> {
	Medical2Ecrf2 findBySurveyTwoId(String surveyTwoId);
	Medical2Ecrf2 findByid(int id);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
