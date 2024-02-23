package org.medical.hub.auth;

import org.medical.hub.models.User;
import org.medical.hub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> byUserName = userRepository.findByUsername(userName);

        byUserName.orElseThrow(()-> new UsernameNotFoundException("Username not found."));
        return byUserName.map(MyUserDetail::new).get();
    }
}
