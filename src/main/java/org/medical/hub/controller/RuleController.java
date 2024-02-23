package org.medical.hub.controller;

import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.models.CompareBetween;
import org.medical.hub.models.Expression;
import org.medical.hub.models.FileType;
import org.medical.hub.models.Rule;
import org.medical.hub.request.CreateRuleRequest;
import org.medical.hub.services.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class RuleController {

    private Logger logger = LoggerFactory.getLogger(RuleController.class);
    private final RuleService ruleService;

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping(value = Routes.Rules.GET, name = "get-rules")
    public String index() {

        return "rules/index";
    }

    @GetMapping(value = Routes.Rules.CREATE, name = "create-rule")
    public String create(Model model) throws FileNotFoundException {
        logger.trace("Displaying the form to create a model.");
        return "rules/create";
    }

    @PostMapping(value = Routes.Rules.AJAX_GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRules(@RequestBody DataTableRequest dataTable) {

        return ResponseEntity.ok(ruleService.getRules(dataTable));
    }

    @PostMapping(value = Routes.Rules.GET, name = "save-rule")
    public String save(@Valid @ModelAttribute("rule") CreateRuleRequest rules, BindingResult bindingResult, RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return "rules/create";
        }

        this.ruleService.save(rules);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Rules added successfully.");
        return "redirect:http://localhost:8080/rules";
//        return "redirect:" + Routes.Rules.GET;
    }

    @PostMapping(value = Routes.Rules.DELETE)
    public String delete(@PathVariable Long ruleId, RedirectAttributes ra) {

        this.ruleService.delete(ruleId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Rules deleted successfully.");
        return "redirect:" + Routes.Rules.GET;
    }

    @GetMapping(value = Routes.Rules.EDIT)
    public String edit(@PathVariable Long ruleId, Model model) throws FileNotFoundException {
        Rule ruleById = this.ruleService.getRuleById(ruleId);

        var rule = new CreateRuleRequest();
        rule.setName(ruleById.getName());
        rule.setRuleType(ruleById.getRuleType().getValue());
        rule.setSecondColumn(ruleById.getSecondColumn());
        rule.setFirstColumn(ruleById.getFirstColumn());
        if (ruleById.getCompareWith() != null)
            rule.setCompareBetween(ruleById.getCompareWith().getValue());
        rule.setFileType(ruleById.getFileType().ordinal());
        if (ruleById.getExpression() != null)
            rule.setExpression(ruleById.getExpression().getValue());
        rule.setVariableName(ruleById.getVariableName());
        rule.setNegativeValue(ruleById.getNegativeValue());
        rule.setPositiveValue(ruleById.getPositiveValue());
        rule.setUndefinedValue(ruleById.getUndefinedValue());
        rule.setNegativeMessage(ruleById.getNegativeMessage());
        rule.setMethodName(ruleById.getMethodName());
        rule.setParameters(ruleById.getParameters());
        rule.setParameterValue(ruleById.getParameter());
        rule.setCompareBetween(ruleById.getCompareWith().getValue());
        rule.setErrorMessage(ruleById.getErrorMessage());
        if (ruleById.getCompareWith() == CompareBetween.VALIDATE_COLUMN)
            rule.setColumnMethodName(ruleById.getMethodName());

        model.addAttribute("rule", rule);
        model.addAttribute("id", ruleId);

        return "rules/edit";
    }

    @PutMapping(value = Routes.Rules.UPDATE, name = "update-rule")
    public String update(@PathVariable Long ruleId, @Valid @ModelAttribute("rule") CreateRuleRequest rule,
                         BindingResult bindingResult, RedirectAttributes ra, Model model) throws FileNotFoundException {

        if (bindingResult.hasErrors()) {
            return "rules/edit";
        }

        this.ruleService.update(ruleId, rule);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Rules updated successfully.");
        return "redirect:" + Routes.Rules.GET;
    }

    @GetMapping(value = Routes.Rules.VIEW)
    public String view(@PathVariable Long ruleId, Model model) {
        Rule ruleById = this.ruleService.getRuleById(ruleId);
        model.addAttribute("rule", ruleById);

        return "rules/view";
    }

    @ModelAttribute("rule")
    private CreateRuleRequest getRule() {
        return new CreateRuleRequest();
    }

    @ModelAttribute("expression")
    private Expression[] getExpression() {
        return Expression.values();
    }

    @ModelAttribute("clinicalData")
    private List<String> getClinicalDataColumn() {
        return ruleService.getColumnsFromFileType(FileType.CLINICAL_DATA);
    }

    @ModelAttribute("sampleManifest")
    private List<String> getSampleManifestColumn() {
        return ruleService.getColumnsFromFileType(FileType.SAMPLE_MANIFEST);
    }

    @ModelAttribute("factorsPrior")
    private List<String> getFactorsPrioriColumn() {
        return ruleService.getColumnsFromFileType(FileType.FACTOR_PRIOR_TO_PD);
    }

    @ModelAttribute("predefinedMethod")
    private List<String> getPredefinedMethod() {
        return List.of("validateYear", "org.medical.hub.validators.RuleValidation.validateYear");
    }
}
