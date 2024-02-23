package org.medical.hub.services;

import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;
import org.medical.hub.request.AssignECRFRequest;

import java.text.ParseException;
import java.util.List;

public interface ECRFService {

    /**
     * Delete selected eCRF by survey two id
     *
     * @param surveyTwoID selected survey two id
     */
    void deleteECRFs(List<String> surveyTwoID);

    /**
     * Delete the ecrfy by survey two id
     *
     * @param surveyTwoID
     */
    void deleteECRF(String surveyTwoID);

    /**
     * Assign eCRF to the selected user
     *
     * @param surveyTwoId selected eCRF
     * @param userId selected user
     */
    void assignECRF(String surveyTwoId, Long userId);

    /**
     * Assign multiple eCRF to the selected user
     *
     * @param request
     */
    void assignECRFByPatIDs(AssignECRFRequest request);

    /**
     * Get the pat id
     *
     * @return
     */
    List<String> getPatId();

    /**
     * Get the eCRF for the datatable
     *
     * @param dataTable jQuery datatable request
     *
     * @return response for Datatable
     * @throws ParseException
     */
    DataTableResponse getECRFReport(DataTableRequest dataTable) throws ParseException;

}
