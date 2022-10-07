package com.epam.esm.controller;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.builder.UserBuilder;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.security.AuthenticationRequest;
import com.epam.esm.entity.security.AuthenticationResponse;
import com.epam.esm.entity.User;
//import com.auth0.jwt.algorithms.Algorithm;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.NotFoundLoginException;
import com.epam.esm.security.JwtProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

//    @PreAuthorize("permitAll()")
//    @PostMapping("/registration")
//    public ResponseEntity<Void> registration(@RequestBody User user) {
//        userService.registration(user, true);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @PreAuthorize("permitAll()")
    @GetMapping("/successful-authentication")
    public ResponseEntity<Void> successfulAuthentication() {
        return new ResponseEntity<>(HttpStatus.OK);
    }



//    @Value("${jwt.secret}")
//    private String jwtSecret;
//    @Value("${jwt.expiration.time}")
//    private int jwtExpirationTime;
//    private final AuthenticationManager authenticationManager;
//    private final UserService userService;
//    private final JwtProvider jwtProvider;
//    private final UserBuilder userBuilder;
//
//    public AuthenticationController(AuthenticationManager authenticationManager,
//                                    UserService userService,
//                                    UserBuilder userBuilder,
//                                    JwtProvider jwtProvider) {
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.jwtProvider = jwtProvider;
//        this.userBuilder = userBuilder;
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<Void> signUpUser(@RequestBody UserDto userDto) throws ExpectationFailedException, NotFoundLoginException, EntityExistException {
//        userService.create(userBuilder.build(userDto));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authenticationRequest) throws NotFoundException, NotFoundLoginException {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                authenticationRequest.getLogin(),
//                authenticationRequest.getPassword())
//        );
//        User user = userService.find(authenticationRequest.getLogin(), authenticationRequest.getPassword());
//        String token = jwtProvider.createToken(user.getLogin(), user.getRoles());
//        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
//    }

//    @PostMapping("/logout")
//    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(httpServletRequest, httpServletResponse, null);
//    }

//    @GetMapping("/api/hello")
//    public String hello(Principal principal) {
//        return "Hello " +principal.getName()+", Welcome to Daily Code Buffer!!";
//    }

//    @GetMapping
//    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws NotFoundException, NotFoundLoginException {
//        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String refreshToken = authorizationHeader.substring("Bearer ".length());
//            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            DecodedJWT decodedJWT = verifier.verify(refreshToken);
//            String login = decodedJWT.getSubject();
//            User user = userService.find(login);
//            String accessToken = jwtProvider.createToken(user.getName(), user.getRoles());
//            return new ResponseEntity<>(new AuthenticationResponse(accessToken), HttpStatus.OK);
//        }
//        return null;
//    }
}