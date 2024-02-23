package org.medical.hub.customer;

import lombok.AllArgsConstructor;
import org.medical.hub.common.Common;
import org.medical.hub.common.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class CreateCustomerMailController {

    private static final String ADD_VIEW = "customer/create";
    private final CustomerService customerService;


    @GetMapping(value = Routes.Mail.ADD_CUSTOMER_DETAILS)
    public String addCorrespondent(@PathVariable("mailId") Long mailId,
                                   Model model) {

        model.addAttribute("action", "");
        return ADD_VIEW;
    }

    @PostMapping(value = Routes.Mail.STORE_CUSTOMER_DETAILS, name = "save-mail-customer")
    public String saveCorrespondent(@PathVariable("mailId") Long mailId,
                                    @Valid @ModelAttribute("customer") CustomerRequest customerRequest,
                                    BindingResult bindingResult,
                                    RedirectAttributes ra) {
        if(bindingResult.hasErrors()){
            return ADD_VIEW;
        }

        this.customerService.saveCustomerAndLinkWithMail(customerRequest, mailId);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, "Correspondent details saved successfully.");
        return Common.REDIRECT+Routes.Mail.GET;
    }


    @ModelAttribute("customer")
    private CustomerRequest customer() {
        return new CustomerRequest();
    }
}
