package org.medical.hub.mail;

import lombok.AllArgsConstructor;
import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.workflow.CreateWorkflowRequest;
import org.medical.hub.workflow.WorkflowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@AllArgsConstructor
public class WorkflowEmailController {

    private static final String CREATE_VIEW = "workflowemail/create";
    private static final String EDIT_VIEW = "workflowemail/edit";

    private final WorkflowEmailService workflowEmailService;

    @GetMapping(value = Routes.WorkflowEmail.CREATE, name = "create-workflow-email")
    public String create(@PathVariable("workflowId") Long workflowId, Model model) {

        model.addAttribute("workflowId", workflowId);
        model.addAttribute("action", "/workflow/" + workflowId + "/create-email");
        return CREATE_VIEW;
    }

    @PostMapping(value = Routes.WorkflowEmail.STORE, name = "save-workflow-email")
    public String save(@PathVariable("workflowId") Long workflowId,
                       @Valid @ModelAttribute("workflowEmail") CreateMailRequest workflow,
                       BindingResult bindingResult,
                       RedirectAttributes ra) throws ParseException {

        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }

        this.workflowEmailService.save(workflowId, workflow);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email added to workflow successfully.");
        return Common.REDIRECT + Routes.Workflow.GET + "/" + workflowId;
    }

    @GetMapping(value = Routes.WorkflowEmail.EDIT, name = "edit-workflow-email")
    public String editWorkflowEmail(@PathVariable("workflowId") Long workflowId,
                                    @PathVariable("mailId") Long mailId,
                                    Model model) {

        // get the mail details
        UserEmails mailById = this.workflowEmailService.findMailById(workflowId, mailId);
        CreateMailRequest mailRequest = new CreateMailRequest();
        mailRequest.setEmailContent(mailById.getContent());
        mailRequest.setSubject(mailById.getSubject());

        long millis = mailById.getSentAt(); // your milliseconds here
        Date date = new Date(millis);
        String pattern = "MM/dd/yyyy hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateTime = simpleDateFormat.format(date);

        mailRequest.setSentAt(dateTime);

        model.addAttribute("workflowId", workflowId);
        model.addAttribute("mailId", mailId);
        model.addAttribute("action", "/workflow/" + workflowId + "/mail/" + mailId + "/update");
        model.addAttribute("workflowEmail", mailRequest);
        return EDIT_VIEW;
    }

    @PostMapping(value = Routes.WorkflowEmail.UPDATE, name = "update-workflow-email")
    public String updateWorkflowEmail(@PathVariable("workflowId") Long workflowId,
                                      @PathVariable("mailId") Long mailId,
                                      @Valid @ModelAttribute("workflowEmail") CreateMailRequest workflow,
                                      BindingResult bindingResult,
                                      RedirectAttributes ra) throws ParseException {

        if (bindingResult.hasErrors()) {
            return EDIT_VIEW;
        }

        this.workflowEmailService.update(workflowId, mailId, workflow);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email added to workflow successfully.");
        return Common.REDIRECT + Routes.Workflow.GET + "/" + workflowId;
    }

    @PostMapping(value = Routes.WorkflowEmail.DELETE)
    public String delete(@PathVariable("workflowId") Long workflowId,
                         @PathVariable("mailId") Long mailId,
                         RedirectAttributes ra) {

        this.workflowEmailService.delete(workflowId, mailId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Workflow email deleted successfully.");
        return Common.REDIRECT +  Routes.Workflow.GET + "/" + workflowId;
    }

    @ModelAttribute("workflowEmail")
    private CreateMailRequest getWorkflowEmail() {
        return new CreateMailRequest();
    }

    @ModelAttribute("workflow")
    private CreateWorkflowRequest getWorkflowRequest() {
        return new CreateWorkflowRequest();
    }
}
