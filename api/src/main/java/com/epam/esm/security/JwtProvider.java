package com.epam.esm.security;

import com.epam.esm.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.time}")
    private int jwtExpirationTime;

    @Autowired
    private UserDetailsService userDetailsService;

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

    public String getLoginFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //TODO
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            //Invalid JWT token
        } catch (ExpiredJwtException ex) {
            //Expired JWT token
        } catch (UnsupportedJwtException ex) {
            //Unsupported JWT token
        } catch (IllegalArgumentException ex) {
            //JWT claims string is empty
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!bearerToken.isEmpty() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }
}
