package org.medical.hub.exception;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.customer.CustomerNotFoundException;
import org.medical.hub.workflow.WorkflowNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ExcelFileNotFoundException.class)
    public String storageException(final ExcelFileNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Excel file not found");
        return "redirect:/excel-file";
    }

    @ExceptionHandler(ExcelFileHandlingException.class)
    public String excelFileHandlingException(final ExcelFileHandlingException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Error while parsing the excel file.");
        return "redirect:/excel-file";
    }

    @ExceptionHandler(TemplateFileNotFoundException.class)
    public String templateFileNotFoundException(final TemplateFileNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, throwable.getMessage());
        return "redirect:/excel-file";
    }

    @ExceptionHandler(InternalServerException.class)
    public String internalServerException(final InternalServerException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Internal server error");
        return "redirect:/excel-file";
    }

    @ExceptionHandler(eCRFNotFoundException.class)
    public String eCRFNotFoundException(final eCRFNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "eCRF not found.");
        return "redirect:/" + Routes.ECRFReport.GET;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(final UserNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Assignee not found.");
        return "redirect:/" + Routes.ECRFReport.GET;
    }

    @ExceptionHandler(RuleNotFoundException.class)
    public String ruleNotFoundException(final RuleNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Rule not found.");
        return "redirect:/" + Routes.Rules.GET;
    }

    @ExceptionHandler(eCRFReportNotFoundException.class)
    public String eCRFReportNotFoundException(final eCRFReportNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "eCRF report not found.");
        return "redirect:/" + Routes.ECRFReport.GET;
    }

    @ExceptionHandler(WorkflowNotFoundException.class)
    public String workflowNotFoundException(final WorkflowNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Workflow not found.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @ExceptionHandler(MailNotFoundException.class)
    public String mailNotFoundException(final MailNotFoundException throwable, final RedirectAttributes ra) {

        ra.addFlashAttribute(Common.ERROR_MESSAGE, "Workflow not found.");
        return Common.REDIRECT + Routes.Workflow.GET;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public String customerNotFoundException(final CustomerNotFoundException throwable, final RedirectAttributes ra) {
        String redirectTo = throwable.getRedirectTo();
        if(redirectTo.equals("")){
            redirectTo = Routes.Correspondent.GET;
        }
        ra.addFlashAttribute(Common.ERROR_MESSAGE, throwable.getMessage());
        return Common.REDIRECT + redirectTo;
    }
}
