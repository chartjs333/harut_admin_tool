package org.medical.hub.mail;

import org.apache.commons.lang3.StringUtils;
import org.medical.hub.datatable.*;
import org.medical.hub.models.User;
import org.medical.hub.repository.SearchCriteria;
import org.medical.hub.repository.SearchOperation;
import org.medical.hub.services.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final LoggedinUser loggedinUser;
    private final SendMailService sendMailService;

    @Autowired
    public MailServiceImpl(MailRepository mailRepository, LoggedinUser loggedinUser, SendMailService sendMailService) {
        this.mailRepository = mailRepository;
        this.loggedinUser = loggedinUser;
        this.sendMailService = sendMailService;
    }

    @Override
    public DataTableResponse<MailDataTableResponse> getMails(DataTableRequest dataTable) {

        MailSpecification specification = new MailSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("subject", value, SearchOperation.LIKE);
            specification.add(criteria);
        }
        specification.add(new SearchCriteria("status", MailStatus.IN_ANTICIPATION, SearchOperation.NOT_EQUAL));

        for (var col : dataTable.getColumns()) {
            String value1 = col.getSearch().getValue();
            if (StringUtils.isNotBlank(value1)) {
                if (col.getName().equals("status")) {
                    MailStatus status;
                    switch (value1) {
                        case "1":
                            status = MailStatus.NEW;
                            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
                            break;
                        case "2":
                            status = MailStatus.IN_PROGRESS;
                            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
                            break;
                        case "3":
                            status = MailStatus.SUBMITTED;
                            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
                            break;
                        case "4":
                            status = MailStatus.SENT;
                            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
                            break;
                        case "5":
                            status = MailStatus.IN_ANTICIPATION;
                            specification.add(new SearchCriteria("status", status, SearchOperation.EQUAL));
                            break;
                        default:
                            status = MailStatus.ALL;
                    }
                }
            }
        }

        var softDelete = new SearchCriteria("deletedAt", 0L, SearchOperation.EQUAL);
        specification.add(softDelete);

        var parentId = new SearchCriteria("parent", 0L, SearchOperation.EQUAL);
        specification.add(parentId);

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

        User user = loggedinUser.currentLoginUser();

        Page<UserEmails> all = mailRepository.findAll(specification, sortedByName);
        List<MailDataTableResponse> dataTablesData = mapMailData(all.getContent(), dataTable, user);
        long totalRecord = mailRepository.count();
        long filteredRecord = mailRepository.count(specification);
        DataTableResponse<MailDataTableResponse> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    @Override
    public DataTableResponse<MailDataTableResponse> getTrashMails(DataTableRequest dataTable) {

        MailSpecification specification = new MailSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            var criteria = new SearchCriteria("subject", value, SearchOperation.LIKE);
            specification.add(criteria);
        }

        var softDelete = new SearchCriteria("deletedAt", 0L, SearchOperation.GREATER_THAN);
        specification.add(softDelete);

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

        User user = loggedinUser.currentLoginUser();

        Page<UserEmails> all = mailRepository.findAll(specification, sortedByName);
        List<MailDataTableResponse> dataTablesData = mapMailData(all.getContent(), dataTable, user);
        long totalRecord = mailRepository.count();
        long filteredRecord = mailRepository.count(specification);
        DataTableResponse<MailDataTableResponse> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    private UserEmails findMail(Long id) {
        return mailRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Map<Object, Object> favorite(Long id) {
        UserEmails mail = findMail(id);
        User user = loggedinUser.currentLoginUser();

        Map<Object, Object> response = new HashMap<>();
        response.put("success", true);

        var favorite = mail.getUser().stream()
                .filter(favoriteEmail1 -> Objects.equals(favoriteEmail1.getUser().getId(), user.getId()))
                .findFirst().orElse(null);
        if (favorite != null) {

            mail.getUser().remove(favorite);
            response.put("message", "Mail has been removed from favorite list");
            response.put("data", "<i class='far fa-star' aria-hidden='true' ></i>");
        } else {
            // add to favorite list
            FavoriteEmail favoriteEmail = new FavoriteEmail();
            favoriteEmail.setUser(user);
            favoriteEmail.setEmail(mail);
            List<FavoriteEmail> user1 = mail.getUser();
            user1.add(favoriteEmail);

            mail.setUser(user1);
            response.put("message", "Mail is added to your favorite list");
            response.put("data", "<i class='fas fa-star yellow' aria-hidden='true' ></i>");
        }

        mailRepository.save(mail);
        return response;
    }

    @Override
    @Transactional
    public void moveToTrash(Long id) {

        mailRepository.updateDeletedAt(id, System.currentTimeMillis());
    }

    @Override
    @Transactional
    public void restore(Long id) {
        mailRepository.updateDeletedAt(id, null);
    }

    @Override
    public void deletePermanently(Long id) {
        mailRepository.delete(findMail(id));
    }

    @Override
    public UserEmails getMailDetails(Long id) {
        return findMail(id);
    }

    @Override
    @Transactional
    public void sendEmail(CreateMailRequest request, Long mailId) {

        UserEmails mail = findMail(mailId);

        UserEmails email = new UserEmails();
        email.setFrom(request.getTo());
        email.setContent(request.getEmailContent());
        email.setSubject(request.getSubject());
        email.setCreatedAt(System.currentTimeMillis());
        email.setUpdatedAt(System.currentTimeMillis());
        email.setParent(mail);
        mailRepository.save(email);

        mail.setStatus(MailStatus.IN_PROGRESS);
        mailRepository.save(mail);

        MailDto mailDto = new MailDto();
        mailDto.setSendTo(request.getTo());
        mailDto.setContent(request.getEmailContent());
        mailDto.setSubject(request.getSubject());
        mailDto.setFromEmail("info@nikolaymadoyan.com");
        mailDto.setFromName("Medical Hub");
        mailDto.setSmtp("smtpout.secureserver.net");
        mailDto.setPort(587);
        this.sendMailService.sendMail(mailDto);
    }

    private List<MailDataTableResponse> mapMailData(List<UserEmails> emails,
                                                    DataTableRequest dataTableRequest,
                                                    User user) {
        List<MailDataTableResponse> dataTablesData = new ArrayList<>();

        for (UserEmails mail : emails) {

            MailDataTableResponse mailData = new MailDataTableResponse();
            mailData.setId(mail.getId());
            mailData.setCheckbox(mail.getId());
            mailData.setSubject(mail.getSubject());
            mailData.setStatus(mail.getStatus().getName());
            mailData.setCustomer(mail.getCustomer());

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(mail.getCreatedAt());
            mailData.setCreatedAt(format);
            mailData.setAction(String.valueOf(mail.getId()));

            boolean isFavorite = false;
            if (!mail.getUser().isEmpty()) {
                var favorite = mail.getUser().stream()
//                        .filter(u -> Objects.equals(u.getId(), user.getId()))
                        .filter(favoriteEmail1 -> Objects.equals(favoriteEmail1.getUser().getId(), user.getId()))
                        .findFirst().orElse(null);
                if (favorite != null) {
                    isFavorite = true;
                    MailAction favoriteAction = new MailAction();
                    favoriteAction.setId(mailData.getId());
                    favoriteAction.setIcon("fas fa-star yellow");
                    favoriteAction.setUrl("/mail/" + mailData.getId() + "/favorite");
                    mailData.setFavorite(favoriteAction);
                }
            }

            if (!isFavorite) {
                MailAction favoriteAction = new MailAction();
                favoriteAction.setId(mailData.getId());
                favoriteAction.setIcon("far fa-star");
                favoriteAction.setUrl("/mail/" + mailData.getId() + "/favorite");
                mailData.setFavorite(favoriteAction);
            }

            List<MailAction> mailActions = new ArrayList<>();
            if (mail.getDeletedAt() != null && mail.getDeletedAt() > 0) {
                MailAction mailAction = new MailAction();
                mailAction.setName("Delete Permanently");
                mailAction.setUrl("/mail/" + mail.getId() + "/delete-permanently");
                mailAction.setAction("delete-permanently");
                mailActions.add(mailAction);

                MailAction restoreAction = new MailAction();
                restoreAction.setName("Restore");
                restoreAction.setUrl("/mail/" + mail.getId() + "/restore");
                restoreAction.setAction("restore");
                mailActions.add(restoreAction);
            } else {

                MailAction viewAction = new MailAction();
                viewAction.setName("View");
                viewAction.setUrl("/mail/" + mail.getId());
                viewAction.setAction("view");
                mailActions.add(viewAction);

                if(mail.getWorkflow() == null){
                    MailAction answerAction = new MailAction();
                    answerAction.setName("Answer");
                    answerAction.setUrl("/mail/create");
                    answerAction.setAction("create-mail");
                    answerAction.setId(mail.getId());
                    mailActions.add(answerAction);
                }else{
                    MailAction viewWorkflow = new MailAction();
                    viewWorkflow.setName("Activity");
                    viewWorkflow.setUrl("mail/"+mail.getId()+"/workflow/"+mail.getWorkflow().getId());
                    viewWorkflow.setAction("view-workflow");
                    mailActions.add(viewWorkflow);
                }

                MailAction deleteAction = new MailAction();
                deleteAction.setName("Delete");
                deleteAction.setUrl("/mail/" + mail.getId() + "/delete");
                deleteAction.setAction("delete");
                mailActions.add(deleteAction);
            }

            mailData.setActions(mailActions);
            dataTablesData.add(mailData);

        }

        return dataTablesData;
    }
}
