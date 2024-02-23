package org.medical.hub.password;

import org.medical.hub.models.User;
import org.medical.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean changePassword(ChangePasswordRequest passwordRequest){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            Optional<User> byUsername = this.userRepository.findByUsername(userDetails.getUsername());
            if(byUsername.isPresent()){
                User user = byUsername.get();

                if(this.passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())){
                    user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
                    userRepository.save(user);
                    return true;
                }
            }
        }
        return false;
    }
}
