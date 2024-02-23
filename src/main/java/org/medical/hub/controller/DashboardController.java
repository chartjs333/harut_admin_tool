package org.medical.hub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(value = "/dashboard", name = "dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping(value = "/", name = "home")
    public String home() {
        return "redirect:/dashboard";
    }
}
