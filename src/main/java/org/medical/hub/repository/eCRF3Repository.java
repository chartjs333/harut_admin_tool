package org.medical.hub.repository;

import org.medical.hub.models.Medical2Ecrf3;
import org.springframework.data.jpa.repository.JpaRepository;


public interface eCRF3Repository extends JpaRepository<Medical2Ecrf3, Integer> {

	Medical2Ecrf3 findBySurveyTwoId(String surveyTwoId);

	Long deleteBySurveyTwoId(String surveyTwoId);
}
