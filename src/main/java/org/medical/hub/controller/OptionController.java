package org.medical.hub.controller;

import org.medical.hub.common.Common;
import org.medical.hub.request.OptionSpamRequest;
import org.medical.hub.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping(value = "/option/spam", name = "create-spam-content")
    public String create(Model model) {
        OptionSpamRequest request = new OptionSpamRequest();
        request.setContent(this.optionService.getSpamContent());
        model.addAttribute("spamContent", request);

        return "option/spam";
    }

    @PostMapping(value = "/option/spam", name = "save-spam-content")
    public String save(@Valid @ModelAttribute("spamContent") OptionSpamRequest rules,
                       BindingResult bindingResult, RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return "option/spam";
        }

        this.optionService.saveSpam(rules);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Spam content successfully.");
        return "redirect:/option/spam";
    }
}
