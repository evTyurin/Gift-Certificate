package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service("customUserDetailsService")
//@Component("customUserDetailsService")
//@Service
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    /**
     * Instantiates a new JwtUserDetailsService.
     *
     * @param userService the user service
     */
    @Autowired
    public CustomUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userService.find(login);
        return userService.map(user);
    }

}
