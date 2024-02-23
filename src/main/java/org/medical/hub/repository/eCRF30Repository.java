package org.medical.hub.repository;

import org.medical.hub.models.Calculator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eCRF30Repository extends JpaRepository<Calculator, Integer> {
    Calculator findBySurveyTwoId(String surveyTwoId);
    Long deleteBySurveyTwoId(String surveyTwoId);
}