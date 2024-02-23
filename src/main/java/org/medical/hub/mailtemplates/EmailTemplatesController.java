package org.medical.hub.mailtemplates;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.request.CreateEmailTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EmailTemplatesController {

    private final EmailTemplateService emailTemplateService;

    @Autowired
    public EmailTemplatesController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @GetMapping(value = Routes.EmailTemplates.GET, name = "get-email-templates")
    public String index(Model model) {
        var emailTemplates = this.emailTemplateService.findAll();
        model.addAttribute("emailTemplates", emailTemplates);

        return "email-templates/index";
    }

    @PostMapping(value = Routes.EmailTemplates.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRules(@RequestBody DataTableRequest dataTable) {

        return ResponseEntity.ok(emailTemplateService.getEmailTemplates(dataTable));
    }

    @GetMapping(value = Routes.EmailTemplates.CREATE, name = "create-email-templates")
    public String create(Model model) {

        return "email-templates/create";
    }

    @PostMapping(value = Routes.EmailTemplates.GET, name = "save-email-templates")
    public String save(@Valid @ModelAttribute("emailTemplate") CreateEmailTemplateRequest rules,
                       BindingResult bindingResult, RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return "email-templates/create";
        }

        this.emailTemplateService.save(rules);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email templates added successfully.");
        return "redirect:"+Routes.EmailTemplates.GET;
    }

    @GetMapping(value = Routes.EmailTemplates.EDIT)
    public String edit(@PathVariable("emailTemplatesId") Long id, Model model) {
        EmailTemplate template = this.emailTemplateService.findById(id);

        var emailTemplate = new CreateEmailTemplateRequest();
        emailTemplate.setName(template.getName());
        emailTemplate.setDescription(template.getDescription());
        emailTemplate.setEmailContent(template.getEmailContent());

        model.addAttribute("emailTemplate", emailTemplate);
        model.addAttribute("id", id);

        return "email-templates/edit";
    }

    @PutMapping(value = Routes.EmailTemplates.UPDATE, name = "update-email-templates")
    public String update(@PathVariable("emailTemplatesId") Long ruleId,
                         @Valid @ModelAttribute("emailTemplate") CreateEmailTemplateRequest emailTemplate,
                         BindingResult bindingResult,
                         RedirectAttributes ra, Model model) {

        if (bindingResult.hasErrors()) {
            return "email-templates/edit";
        }

        this.emailTemplateService.update(ruleId, emailTemplate);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email Templates updated successfully.");
        return "redirect:" + Routes.EmailTemplates.GET;
    }

    @PostMapping(value = Routes.EmailTemplates.DELETE)
    public String delete(@PathVariable("emailTemplatesId") Long ruleId, RedirectAttributes ra) {

        this.emailTemplateService.delete(ruleId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email template deleted successfully.");
        return "redirect:" + Routes.EmailTemplates.GET;
    }

    @ModelAttribute("emailTemplate")
    private CreateEmailTemplateRequest getEmailTemplates() {
        return new CreateEmailTemplateRequest();
    }

}
