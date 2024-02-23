package org.medical.hub.workflow;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
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
public class WorkflowController {

    private static final String CREATE_VIEW = "workflow/create";
    private static final String EDIT_VIEW = "workflow/edit";
    private static final String LIST_VIEW = "workflow/index";
    private static final String VIEW_VIEW = "workflow/view";

    private final WorkflowService workflowService;

    @Autowired
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping(value = Routes.Workflow.GET, name = "get-workflow")
    public String index(Model model) {
        var emailTemplates = this.workflowService.findAll();
        model.addAttribute("workflow", emailTemplates);

        return LIST_VIEW;
    }

    @GetMapping(value = Routes.Workflow.CREATE, name = "create-workflow")
    public String create(Model model) {

        return CREATE_VIEW;
    }

    @PostMapping(value = Routes.Workflow.GET, name = "save-workflow")
    public String save(@Valid @ModelAttribute("workflow") CreateWorkflowRequest workflow,
                       BindingResult bindingResult,
                       RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }

        this.workflowService.save(workflow);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Workflow added successfully.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @GetMapping(value = Routes.Workflow.EDIT)
    public String edit(@PathVariable("workflowId") Long id, Model model) {
        Workflow template = this.workflowService.findById(id);

        var workflowRequest = new CreateWorkflowRequest();
        workflowRequest.setName(template.getName());
        workflowRequest.setDescription(template.getDescription());

        model.addAttribute("workflow", workflowRequest);
        model.addAttribute("id", id);

        return EDIT_VIEW;
    }

    @PutMapping(value = Routes.Workflow.UPDATE, name = "update-workflow")
    public String update(@PathVariable("workflowId") Long ruleId,
                         @Valid @ModelAttribute("workflow") CreateWorkflowRequest workflowRequest,
                         BindingResult bindingResult,
                         RedirectAttributes ra, Model model) {

        if (bindingResult.hasErrors()) {
            return EDIT_VIEW;
        }

        this.workflowService.update(ruleId, workflowRequest);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Workflow updated successfully.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @PostMapping(value = Routes.Workflow.DELETE)
    public String delete(@PathVariable("workflowId") Long ruleId, RedirectAttributes ra) {

        this.workflowService.delete(ruleId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Workflow deleted successfully.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @PostMapping(value = Routes.Workflow.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRules(@RequestBody DataTableRequest dataTable) {

        return ResponseEntity.ok(workflowService.getEmailTemplates(dataTable));
    }

    @GetMapping(value = Routes.Workflow.VIEW)
    public String view(@PathVariable("workflowId") Long workflowId, Model model){

        var workflow = this.workflowService.findById(workflowId);
        model.addAttribute("workflow", workflow);
        model.addAttribute("workflowId", workflowId);

        return VIEW_VIEW;
    }

    @ModelAttribute("workflow")
    private CreateWorkflowRequest getWorkflow() {
        return new CreateWorkflowRequest();
    }

}
