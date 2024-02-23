package org.medical.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Principal user) {
        if(user != null){
            return "redirect:/dashboard";
        }
        return "auth/login";
    }
}
