package org.medical.hub.mailtemplates;

import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;
import org.medical.hub.dto.EmailTemplatesDataTable;
import org.medical.hub.request.CreateEmailTemplateRequest;

import java.util.List;

public interface EmailTemplateService {

    void save(CreateEmailTemplateRequest request);

    void update(Long ruleId, CreateEmailTemplateRequest request);

    EmailTemplate findById(Long id);

    void delete(Long id);

    List<EmailTemplate> findAll();

    DataTableResponse<EmailTemplatesDataTable> getEmailTemplates(DataTableRequest dataTable);
}
