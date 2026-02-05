package com.seplag.senior.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    // Gera o token com expiracao de 5 minutos conforme o edital
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("seplag-api")
                .withSubject(username)
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    // Valida se o token ainda e valido e a quem pertence
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("seplag-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return ""; // Retorna vazio se o token for invalido ou expirado
        }
    }

    private Instant genExpirationDate() {
        // Define a expiracao rigorosa de 5 minutos
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}