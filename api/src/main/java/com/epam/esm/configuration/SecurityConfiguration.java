//package com.epam.esm.configuration;
//
//import com.epam.esm.security.JwtFilter;
//import com.epam.esm.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
////@EnableAutoConfiguration
////@EnableWebSecurity
////@EnableResourceServer
//@AllArgsConstructor
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    //RESOUSCE SERVER CONFIG
//    //TODO
//
//    private final JwtFilter jwtFilter;
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder encoder;
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/certificates").permitAll()
//                .antMatchers("/certificates/{id}").permitAll()
//                .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("user","admin")
//                .antMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("user","admin")
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);
//    }
//
//}
