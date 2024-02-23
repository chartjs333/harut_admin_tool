package org.medical.hub.mailtemplates;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.datatable.*;
import org.medical.hub.dto.EmailTemplatesDataTable;
import org.medical.hub.exception.RuleNotFoundException;
import org.medical.hub.models.User;
import org.medical.hub.repository.*;
import org.medical.hub.request.CreateEmailTemplateRequest;
import org.medical.hub.services.LoggedinUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private Logger logger = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);

    private final EmailTemplateRepository emailTemplateRepository;
    private final LoggedinUser loggedinUser;

    @Autowired
    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository, LoggedinUser loggedinUser) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.loggedinUser = loggedinUser;
    }

    @Override
    public void save(CreateEmailTemplateRequest request) {

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setName(request.getName());
        emailTemplate.setDescription(request.getDescription());
        emailTemplate.setEmailContent(request.getEmailContent());

        emailTemplate.setCreatedAt(System.currentTimeMillis());
        emailTemplate.setUpdatedAt(System.currentTimeMillis());

        User user = this.loggedinUser.currentLoginUser();
        emailTemplate.setUser(user);

        emailTemplateRepository.save(emailTemplate);
    }

    @Override
    public void update(Long ruleId, CreateEmailTemplateRequest request) {
        EmailTemplate emailTemplate =findById(ruleId);
        emailTemplate.setName(request.getName());
        emailTemplate.setDescription(request.getDescription());
        emailTemplate.setEmailContent(request.getEmailContent());

        emailTemplate.setCreatedAt(System.currentTimeMillis());
        emailTemplate.setUpdatedAt(System.currentTimeMillis());

        User user = this.loggedinUser.currentLoginUser();
        emailTemplate.setUser(user);

        emailTemplateRepository.save(emailTemplate);
    }

    @Override
    public EmailTemplate findById(Long id) {
        return emailTemplateRepository.findById(id)
                .stream().findFirst()
                .orElseThrow(RuleNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        emailTemplateRepository.delete(findById(id));
    }

    @Override
    public List<EmailTemplate> findAll() {
         return emailTemplateRepository.findAll();
    }

    @Override
    public DataTableResponse<EmailTemplatesDataTable> getEmailTemplates(DataTableRequest dataTable) {
        EmailTemplateSpecification specification = new EmailTemplateSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("patID", value, SearchOperation.LIKE);
            specification.add(criteria);
        }

        DataTableOrder order = dataTable.getOrder().stream().findFirst().orElse(null);

        Pageable sortedByName = PageRequest.of(dataTable.getStart(), dataTable.getLength());
        if (order != null) {
            DataTableColumnSpecs dataTableColumnSpecs = dataTable.getColumns().get(order.getColumn());
            if (dataTableColumnSpecs != null) {
                String data = dataTableColumnSpecs.getData();
                if (data.equals("checkbox") || data.equals("action")) {
                    data = "id";
                }
                Sort.Direction dir = (order.getDir().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort by = Sort.by(new Sort.Order(dir, data));
                int pageNumber = DataTableUtils.getPageNumber(dataTable);
                sortedByName = PageRequest.of(pageNumber, dataTable.getLength(), by);
            }
        }

        Page<EmailTemplate> all = emailTemplateRepository.findAll(specification, sortedByName);
        List<EmailTemplatesDataTable> dataTablesData = mapRuleData(all.getContent(), dataTable);
        long totalRecord = emailTemplateRepository.count();
        long filteredRecord = emailTemplateRepository.count(specification);
        DataTableResponse<EmailTemplatesDataTable> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    private List<EmailTemplatesDataTable> mapRuleData(List<EmailTemplate> rules, DataTableRequest dataTableRequest) {
        List<EmailTemplatesDataTable> dataTablesData = new ArrayList<>();
        int i = 1;
        for (EmailTemplate rule : rules) {
            getSN(dataTableRequest, i);
            EmailTemplatesDataTable ruleDataTable = new EmailTemplatesDataTable();
            ruleDataTable.setId(rule.getId());
            ruleDataTable.setSn(getSN(dataTableRequest, i));
            ruleDataTable.setDescription(rule.getDescription());
            ruleDataTable.setName(rule.getName());

            String fullName = rule.getUser().getFirstName() + " " + rule.getUser().getSurname();
            ruleDataTable.setCreatedBy(fullName);

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rule.getCreatedAt());
            ruleDataTable.setCreatedAt(format);
            ruleDataTable.setAction(String.valueOf(rule.getId()));
            dataTablesData.add(ruleDataTable);

            i++;
        }

        return dataTablesData;
    }

    private Integer getSN(DataTableRequest dataTable, int i) {
        return dataTable.getStart() + i;
    }
}
