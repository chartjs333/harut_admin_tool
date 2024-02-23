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

@Controller
@AllArgsConstructor
public class MailWorkflowController {

    private static final String CREATE_VIEW = "workflowemail/create";
    private static final String VIEW_VIEW = "workflowemail/view";
    private static final String CREATE_WORKFLOW_FOR_EMAIL = "mail/workflow";

    private final WorkflowService workflowService;
    private final WorkflowEmailService workflowEmailService;

    @GetMapping(value = "mail/{mailId}/workflow", name = "create-mail-workflow")
    public String createWorkflowMail(@PathVariable("mailId") Long mailId, Model model) {

        model.addAttribute("mailId", mailId);
        return CREATE_WORKFLOW_FOR_EMAIL;
    }

    @PostMapping(value = "mail/{mailId}/workflow")
    public String saveWorkflowMail(@PathVariable("mailId") Long mailId,
                                   @Valid @ModelAttribute("workflow") CreateWorkflowRequest workflow,
                                   BindingResult bindingResult,
                                   RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return CREATE_WORKFLOW_FOR_EMAIL;
        }

        this.workflowService.saveWithMail(workflow, mailId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Workflow added successfully.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @GetMapping(value = "mail/{mailId}/workflow/{workflowId}")
    public String getMailsOfWorkflow(@PathVariable("workflowId") Long workflowId,
                                     @PathVariable("mailId") Long mailId,
                                     Model model) {

        var workflow = this.workflowService.findById(workflowId);
        model.addAttribute("workflow", workflow);
        model.addAttribute("workflowId", workflowId);
        model.addAttribute("mailId", mailId);

        return VIEW_VIEW;
    }

    @GetMapping(value = "mail/{mailId}/workflow/{workflowId}/create")
    public String createMailForWorkflow(@PathVariable("workflowId") Long workflowId,
                                        @PathVariable("mailId") Long mailId,
                                        Model model) {

        model.addAttribute("workflowId", workflowId);
        model.addAttribute("mailId", mailId);
        model.addAttribute("action", "/mail/" + mailId + "/workflow/" + workflowId);
        return CREATE_VIEW;
    }

    @PostMapping(value = "mail/{mailId}/workflow/{workflowId}")
    public String saveMailForWorkflow(@PathVariable("workflowId") Long workflowId,
                                      @PathVariable("mailId") Long mailId,
                                      @Valid @ModelAttribute("workflowEmail") CreateMailRequest workflow,
                                      BindingResult bindingResult,
                                      RedirectAttributes ra) throws ParseException {

        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }

        this.workflowEmailService.save(workflowId, workflow);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Email added to workflow successfully.");
        return Common.REDIRECT + "/mail/" + mailId + "/workflow/" + workflowId;
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
