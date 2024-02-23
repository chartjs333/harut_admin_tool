package org.medical.hub.controller;


import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.models.User;
import org.medical.hub.request.AssignECRFRequest;
import org.medical.hub.request.ECRFDeleteRequest;
import org.medical.hub.services.ECRFService;
import org.medical.hub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ECRFReportController {

    private ECRFService eCRFService;
    private UserService userService;

    @Autowired
    public ECRFReportController(ECRFService eCRFService, UserService userService) {
        this.eCRFService = eCRFService;
        this.userService = userService;
    }

    @GetMapping(value = Routes.ECRFReport.GET, name = "eCRF-report-get")
    public String index(Model model) {
        List<User> users = this.userService.getUsers();
        List<String> patIds = this.eCRFService.getPatId();
        model.addAttribute("users", users);
        model.addAttribute("patIds", patIds);
        return "eCRF-report/list";
    }

    @PostMapping(value = Routes.ECRFReport.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getECRF(@RequestBody DataTableRequest dataTable) throws ParseException {

        return ResponseEntity.ok(eCRFService.getECRFReport(dataTable));
    }

    @PostMapping(value = Routes.ECRFReport.MASS_ASSIGN)
    public String assignECRF(@Valid AssignECRFRequest assignedTo, BindingResult result, RedirectAttributes ra) {

        if(result.hasErrors()){
            return "redirect:" + Routes.ECRFReport.GET;
        }

        this.eCRFService.assignECRFByPatIDs(assignedTo);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF assigned to the user successfully.");
        return "redirect:" + Routes.ECRFReport.GET;
    }

    @PostMapping(value = Routes.ECRFReport.ASSIGN)
    public String assignECRF(@PathVariable String surveyTwoId, AssignECRFRequest assignedTo, RedirectAttributes ra) {

        this.eCRFService.assignECRF(surveyTwoId, assignedTo.getAssignTo());
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF assigned to the user successfully.");
        return "redirect:" + Routes.ECRFReport.GET;
    }

    @PostMapping(value = Routes.ECRFReport.DELETE_SELECTED)
    public String delete(ECRFDeleteRequest request, RedirectAttributes ra) {
        List<String> collect = Arrays.stream(request.getIds().split(",")).distinct().collect(Collectors.toList());
        this.eCRFService.deleteECRFs(collect);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF report deleted successfully.");
        return "redirect:" + Routes.ECRFReport.GET;
    }

    @PostMapping(value = Routes.ECRFReport.DELETE)
    public String deleteSingleECRF(@PathVariable String surveyTwoId, RedirectAttributes ra) {

        this.eCRFService.deleteECRF(surveyTwoId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "eCRF report deleted successfully.");
        return "redirect:" + Routes.ECRFReport.GET;
    }
}