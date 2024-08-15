package org.acme.resources;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.HashSet;

import org.acme.entities.User;
import org.jboss.logging.Logger;

@ApplicationScoped
public class TokenService {

    @Inject
    Logger log;

    @Inject
    JwtConfig jwtConfig;

    public static HashSet<String> validRefreshTokens = new HashSet<>();

    public String accessTokenFromRefreshToken(String refreshToken) {
        log.error(validRefreshTokens.size());
        if (!isValidRefreshToken(refreshToken)) {
            throw new WebApplicationException("Invalid refresh token", Response.Status.UNAUTHORIZED);
        }

        // Generate a new JWT token
        String newToken = Jwt.issuer(jwtConfig.getIssuer()) 
            .upn("user")
            .groups("student") 
            .expiresIn(Duration.ofMinutes(30)) 
            .sign();

        return newToken;
    }

    public String refreshTokenFromRefreshToken(String refreshToken) {
        // Validate the refresh token
        if (!isValidRefreshToken(refreshToken)) {
            throw new WebApplicationException("Invalid refresh token", Response.Status.UNAUTHORIZED);
        }

        // Generate a new JWT token
        String newToken = Jwt.issuer(jwtConfig.getIssuer()) 
            .upn("user")
            .groups("student") 
            .expiresIn(Duration.ofDays(10)) 
            .sign();

        return newToken;
    }

    private boolean isValidRefreshToken(String refreshToken) {
        return validRefreshTokens.contains(refreshToken); 
    }
    
    public String generateAccessToken(User user) {
        return Jwt.issuer(jwtConfig.getIssuer()) // apparently can have different issuer name
                .upn(user.getUsername())
                .groups(user.getRole().getRoleName())
                .expiresIn(Duration.ofMinutes(30))
                .sign();
    }
    
    public String generateRefreshToken(User user) {
        
        
        String token =  Jwt.issuer(jwtConfig.getIssuer()) // apparently can have different issuer name
                .upn(user.getUsername())
                .groups(user.getRole().getRoleName())
                .expiresIn(Duration.ofDays(10))
                .sign();
        validRefreshTokens.add(token);
        return token;
    }
}