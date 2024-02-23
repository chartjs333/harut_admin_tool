package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf6;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF6Repository extends JpaRepository<Medical2Ecrf6, Integer> {
	Medical2Ecrf6 findBySurveyTwoId(String surveyTwoId);
	Long deleteBySurveyTwoId(String surveyTwoId);
}
