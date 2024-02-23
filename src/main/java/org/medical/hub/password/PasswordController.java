package org.medical.hub.password;

import org.medical.hub.common.Common;
import org.medical.hub.message.ExcelMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    PasswordController(PasswordService passwordService){
        this.passwordService = passwordService;
    }

    @GetMapping(value = "/change-password", name = "change-password")
    public String changePassword() {

        return "password/change";
    }

    @PostMapping(value = "/change-password", name = "change-password-post")
    public String changePasswordPost(@Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
                                     BindingResult result,
                                     RedirectAttributes ra) {
        if(result.hasErrors()){
            return "password/change";
        }

        this.passwordService.changePassword(changePasswordRequest);
        ra.addFlashAttribute(Common.SUCCESS_MESSAGE, ExcelMessages.SUCCESS_PASSWORD_CHANGED);
        return "redirect:/dashboard";
    }

    @ModelAttribute("changePasswordRequest")
    public ChangePasswordRequest changePasswordRequest() {
        return new ChangePasswordRequest();
    }
}
