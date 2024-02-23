package org.medical.hub.mail;

import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;

import java.util.Map;

public interface MailService {

    DataTableResponse<MailDataTableResponse> getMails(DataTableRequest dataTable);

    /**
     * Get all the mail that have been moved to trash
     *
     * @param dataTable datatable request body
     * @return DataTable Response
     */
    DataTableResponse<MailDataTableResponse> getTrashMails(DataTableRequest dataTable);

    /**
     * Add email to the favorite list
     *
     * @param id
     */
    Map<Object, Object> favorite(Long id);

    /**
     * Move the email to trash
     *
     * @param id email id
     */
    void moveToTrash(Long id);

    /**
     * Restore the email from trash
     *
     * @param id email id
     */
    void restore(Long id);

    /**
     * Delete mail permanently
     *
     * @param id email id
     */
    void deletePermanently(Long id);

    /**
     * Get the mail details
     *
     * @param id email id
     * @return
     */
    UserEmails getMailDetails(Long id);

    void sendEmail(CreateMailRequest request, Long mailId);
}
