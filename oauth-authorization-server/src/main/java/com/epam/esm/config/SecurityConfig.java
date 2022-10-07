package com.epam.esm.config;

import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.CustomAuthenticationProvider;
import com.epam.esm.service.impl.CustomUserDetailsServiceImpl;
import com.mysql.cj.protocol.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(CustomUserDetailsServiceImpl.class)
public class SecurityConfig {

//    private final DataSource dataSource;
//
//    @Autowired
//    public SecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//
//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;

//    @Autowired
////    @Qualifier("customUserDetailsService")
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    @Qualifier("customUserDetailsService")
//    private CustomUserDetailsServiceImpl sdsdsd;

//    @Autowired
//    PasswordEncoder passwordEncoder;

//    @Bean
//    UserDetailsService userDetailsService() {
//
//        return new MyUserDetailsService(); // to be created
//    }


//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
        )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Autowired
//    public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
//        authenticationManagerBuilder
//                .authenticationProvider(customAuthenticationProvider);
//    }

}
