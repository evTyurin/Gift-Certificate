package com.epam.esm.security;

import com.epam.esm.entity.Role;
import com.epam.esm.exception.JwtAuthenticationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.NotFoundLoginException;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.time}")
    private int jwtExpirationTime;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64
                .getEncoder()
                .encodeToString(jwtSecret.getBytes());
    }

    public String createToken(String name, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("roles", roles);

        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(Date
                        .from(LocalDateTime.now()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .setExpiration(Date
                        .from(LocalDateTime.now()
                                .plusMinutes(jwtExpirationTime)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) throws NotFoundException, NotFoundLoginException {
        UserDetails userDetails = userService.map(userService.find(findLogin(token)));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String findLogin(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException("invalid.jwt.token", 40101);
        } catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException("expired.jwt.token", 40102);
        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException("unsupported.jwt.token", 40103);
        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException("invalid.claims", 40104);
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }
}
