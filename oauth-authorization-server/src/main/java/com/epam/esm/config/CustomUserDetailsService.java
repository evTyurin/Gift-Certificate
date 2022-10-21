package com.epam.esm.config;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Instantiates a new JwtUserDetailsService.
     *
     * @param userService the user service
     */
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userService.find(login);
//       return userService.map(user);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), map(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> map(List<Role> userRole) {
        return userRole
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
