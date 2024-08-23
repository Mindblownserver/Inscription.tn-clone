package org.acme.services;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import org.acme.entities.User;
import org.acme.exceptions.ExpiredRefreshTokenException;
import org.jboss.logging.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped
public class TokenService {

    @Inject
    Logger log;

    @Inject
    JwtConfig jwtConfig;

    DecodedJWT decodedJWT;

    public static HashMap<String, String> validRefreshTokens = new HashMap<>();

    public String accessTokenFromRefreshToken(String refreshToken) {
        if (!isValidRefreshToken(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }

        String newToken = Jwt.issuer(jwtConfig.getIssuer()) 
            .upn(getUsernameFromJwt(refreshToken))
            .groups("student") 
            .expiresIn(Duration.ofSeconds(6)) 
            .sign();

        return newToken;
    }

    public String refreshTokenFromRefreshToken(String refreshToken) {
        if (!isValidRefreshToken(refreshToken)) {
            throw new ExpiredRefreshTokenException();
        }

        String newToken = Jwt.issuer(jwtConfig.getIssuer()) 
            .upn(getUsernameFromJwt(refreshToken))
            .groups("student") 
            .expiresIn(Duration.ofDays(10)) 
            .sign();

        validRefreshTokens.put(getUsernameFromJwt(refreshToken),newToken);

        return newToken;
    }

    private boolean isValidRefreshToken(String refreshToken) {
        String storedRefreshToken= validRefreshTokens.get(getUsernameFromJwt(refreshToken));
        if(storedRefreshToken!=null && storedRefreshToken.equals(refreshToken)){
            return getExpiredDateFromJwt(refreshToken).after(new Date());
        }
        return false; 
    }
    // To be Finished
    public String generateAccessToken(User user) {
        return Jwt.issuer(jwtConfig.getIssuer()) // apparently can have different issuer name
                .upn(user.getUsername())
                .groups(user.getRole().getRoleName())
                .expiresIn(Duration.ofSeconds(3))
                .sign();
    }
    
    public String generateRefreshToken(User user) {  
        String token =  Jwt.issuer(jwtConfig.getIssuer()) // apparently can have different issuer name
                .upn(user.getUsername())
                .groups(user.getRole().getRoleName())
                .expiresIn(Duration.ofDays(10))
                .sign();
        validRefreshTokens.put(user.getUsername(),token);
        return token;
    }
    
    public String getUsernameFromJwt(String token){
        try{
            decodedJWT = JWT.decode(token);
            
            return decodedJWT.getClaim("upn").asString();
        }catch(JWTDecodeException exception){
            throw new ExpiredRefreshTokenException();
        }
    }

    public Date getExpiredDateFromJwt(String token){
        try{
            decodedJWT = JWT.decode(token);
            
            return decodedJWT.getExpiresAt();
        }catch(JWTDecodeException exception){
            throw new ExpiredRefreshTokenException();
        }
    }
}