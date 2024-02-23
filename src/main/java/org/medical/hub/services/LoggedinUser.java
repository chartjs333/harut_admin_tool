package org.medical.hub.services;

import org.medical.hub.models.User;
import org.medical.hub.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoggedinUser {

    private final UserRepository userRepository;

    public LoggedinUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User currentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<User> byUsername = this.userRepository.findByUsername(authentication.getName());
            if (byUsername.isPresent()) {
                return byUsername.get();
            }

        }

        return null;
    }

}
