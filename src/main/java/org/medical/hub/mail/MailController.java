package org.medical.hub.mail;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;
import org.medical.hub.mailtemplates.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class MailController {

    private static final String GET_MAIL_VIEW = "mail/index";
    private static final String GET_TRASH_MAIL_VIEW = "mail/trash";
    private static final String CREATE_MAIL_VIEW = "mail/create";
    private static final String MAIL_TEMPLATES = "mail/templates";
    private static final String SHOW_MAIL_VIEW = "mail/view";

    private final MailService mailService;
    private final EmailTemplateService emailTemplateService;

    @Autowired
    public MailController(MailService mailService, EmailTemplateService emailTemplateService) {
        this.mailService = mailService;
        this.emailTemplateService = emailTemplateService;
    }

    @GetMapping(value = Routes.Mail.GET, name = "get-emails")
    public String getEmails() {

        return GET_MAIL_VIEW;
    }

    @PostMapping(value = Routes.Mail.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMails(@RequestBody DataTableRequest dataTable) {

        return ResponseEntity.ok(mailService.getMails(dataTable));
    }

    @PostMapping(value = Routes.Mail.FAVORITE)
    public ResponseEntity favorite(@PathVariable("id") Long id) {
        Map<Object, Object> response = mailService.favorite(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = Routes.Mail.DELETE)
    public String moveToTrash(@PathVariable("id") Long id, RedirectAttributes ra) {
        mailService.moveToTrash(id);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email is moved to trashed.");
        return Common.REDIRECT + Routes.Mail.GET;
    }

    @PostMapping(value = Routes.Mail.RESTORE)
    public String restore(@PathVariable("id") Long id, RedirectAttributes ra) {
        mailService.restore(id);

        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email is restored successfully.");
        return Common.REDIRECT + Routes.Mail.GET;
    }

    @PostMapping(value = Routes.Mail.DELETE_PERMANENTLY)
    public String deletePermanently(@PathVariable("id") Long id, RedirectAttributes ra) {
        mailService.deletePermanently(id);

        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email is deleted permanently.");
        return Common.REDIRECT + Routes.Mail.GET;
    }

    @GetMapping(value = Routes.Mail.TRASH_MAIL, name = "trash-emails")
    public String getTrashMail() {

        return GET_TRASH_MAIL_VIEW;
    }

    @PostMapping(value = Routes.Mail.AJAX_TRASH_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTrashMails(@RequestBody DataTableRequest dataTable) {

        return ResponseEntity.ok(mailService.getTrashMails(dataTable));
    }

    @GetMapping(value = Routes.Mail.VIEW)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("mail", mailService.getMailDetails(id));

        return SHOW_MAIL_VIEW;
    }


    @GetMapping(value = Routes.Mail.CREATE_MAIL)
    public String createMail(@PathVariable("id") Long mailId, Model model) {

        var mailDetails = mailService.getMailDetails(mailId);

        var createEmailTemplate = new CreateMailRequest();
        createEmailTemplate.setTo(mailDetails.getFrom());

        model.addAttribute("emailTemplate", createEmailTemplate);
        model.addAttribute("mailDetails", mailDetails);

        return CREATE_MAIL_VIEW;
    }

    @GetMapping(value = Routes.Mail.EMAIL_TEMPLATES_MAIL)
    public String useMailTemplate(@PathVariable("id") Long mailId,
                                  @PathVariable("templateId") Long templateId,
                                  Model model) {
        var template = emailTemplateService.findById(templateId);
        var mailDetails = mailService.getMailDetails(mailId);

        var createEmailTemplate = new CreateMailRequest();
        createEmailTemplate.setEmailContent(template.getEmailContent());
        createEmailTemplate.setTo(mailDetails.getFrom());

        model.addAttribute("emailTemplate", createEmailTemplate);
        model.addAttribute("mailDetails", mailDetails);

        return CREATE_MAIL_VIEW;
    }

    @GetMapping(value = Routes.Mail.EMAIL_TEMPLATES)
    public String getEmailTemplates(@PathVariable("id") Long mailId, Model model) {
        var templates = emailTemplateService.findAll();

        model.addAttribute("templates", templates);
        return MAIL_TEMPLATES;
    }

    @PostMapping(value = "/{id}/send-email", name = "send-email")
    public String sendEmail(@PathVariable("id") Long mailId,
                            CreateMailRequest mailRequest,
                            BindingResult bindingResult,
                            RedirectAttributes ra) {

        this.mailService.sendEmail(mailRequest, mailId);

        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email has been sent to the user.");
        return Common.REDIRECT + Routes.Mail.GET;
    }

    @GetMapping(value = "mail/{id}/timeline")
    public String timeline(@PathVariable("id") Long mailId, Model model) {
        var mailDetails = mailService.getMailDetails(mailId);

        model.addAttribute("mailDetails", mailDetails);
        model.addAttribute("timeline", mailDetails.getChildren());
        return "mail/timeline";
    }

    @ModelAttribute("emailTemplate")
    private CreateMailRequest getEmailTemplates() {
        return new CreateMailRequest();
    }

}
