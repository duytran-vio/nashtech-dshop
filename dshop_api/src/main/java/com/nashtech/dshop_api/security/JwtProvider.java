package com.nashtech.dshop_api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.nashtech.dshop_api.data.entities.User;
import com.nashtech.dshop_api.exceptions.InvalidTokenException;

@Component
public class JwtProvider {

    private final Algorithm algorithm;

    public JwtProvider(@Value("${security.jwt.token.secret-key}") String jwtSecret){
        this.algorithm = Algorithm.HMAC256(jwtSecret);
    } 
    
    public String generateAccessToken (User user) {
        try {
            return JWT.create()
                        .withSubject(user.getUsername())
                        .withClaim("username", user.getUsername())
                        .withClaim("role", user.getRole().getRoleName())
                        .withExpiresAt(genAccessExpirationDate())
                        .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error while generating token", e);
        }
    }

    public String validateToken (String token) {
        try {
            return JWT.require(algorithm)
                        .build()
                        .verify(token)
                        .getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }
}
