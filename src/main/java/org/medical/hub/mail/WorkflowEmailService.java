package org.medical.hub.mail;

import java.text.ParseException;

public interface WorkflowEmailService {

    /**
     * Save the workflow emails
     *
     * @param workflowId
     * @param request
     * @throws ParseException
     */
    void save(Long workflowId, CreateMailRequest request) throws ParseException;

    /**
     * Get the mail of workflow
     *
     * @param workflowId
     * @param mailId
     * @return
     */
    UserEmails findMailById(Long workflowId, Long mailId);

    /**
     * Update the workflow emails
     *
     * @param workflowId
     * @param request
     * @throws ParseException
     */
    void update(Long workflowId, Long mailId, CreateMailRequest request) throws ParseException;

    /**
     * Delete the workflow mail details
     *
     * @param workflowId
     * @param mailId
     * @return
     */
    boolean delete(Long workflowId, Long mailId);
}
