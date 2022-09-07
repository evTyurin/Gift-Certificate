package com.epam.esm.controller;

import com.epam.esm.builder.GiftCertificateBuilder;
import com.epam.esm.builder.UserBuilder;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.AuthenticationRequest;
import com.epam.esm.entity.AuthenticationResponse;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.security.JwtProvider;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserBuilder userBuilder;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    UserBuilder userBuilder,
                                    JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.userBuilder = userBuilder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUpUser(@RequestBody UserDto userDto) {
        userService.create(userBuilder.build(userDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) throws NotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getLogin(),
                authenticationRequest.getPassword())
        );
        User user = userService.findByLoginAndPassword(authenticationRequest.getLogin(), authenticationRequest.getPassword());
       String token = jwtProvider.createToken(user.getLogin(), user.getRoles());
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }


}
