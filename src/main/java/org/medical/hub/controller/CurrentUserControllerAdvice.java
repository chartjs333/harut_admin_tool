package org.medical.hub.controller;

import org.medical.hub.auth.MyUserDetail;
import org.medical.hub.models.User;
import org.medical.hub.services.LoggedinUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    private final LoggedinUser loggedinUser;

    public CurrentUserControllerAdvice(LoggedinUser loggedinUser) {
        this.loggedinUser = loggedinUser;
    }

    @ModelAttribute("currentUser")
    public User currentLoginUser(@AuthenticationPrincipal MyUserDetail userDetails) {

        return this.loggedinUser.currentLoginUser();
    }
}
