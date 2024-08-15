package org.acme.resources;

import org.acme.entities.User;
import org.acme.repositories.UserRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/auth")
public class AuthResource {
    
    @Inject
    TokenService tokenService;

    @Inject
    UserRepository userRepository;

    @Inject
    Logger log;

    public class AuthResponse{
        public String accessToken;
        public String refreshToken;
        public String userRole;
        public AuthResponse(String accessToken, String refreshToken, String userRole) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.userRole = userRole;
        }
    }

    public class RefreshTokenResponse{
        public String acessToken;
        public String refreshToken;

        public RefreshTokenResponse(String acessToken, String refreshToken) {
            this.acessToken = acessToken;
            this.refreshToken = refreshToken;
        }
        
    }   

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        // Validate user credentials
        try{
            User user =userRepository.getUserByUsername(request.getUsername());
            if (user != null && PasswordUtils.checkPassword(request.getPassword(), user.getPasswordHash())) {
                String accessToken = tokenService.generateAccessToken(user);
                //log.error(accessToken);
                String refreshToken = tokenService.generateRefreshToken(user);
                AuthResponse authResponse = new AuthResponse(accessToken, refreshToken, user.getRole().getRoleName());
                return Response.ok().entity(authResponse).build();
            }
            return Response.status(404).entity("User "+request.getUsername()+ " not found").build();
        }catch(Exception e){
            log.error("Error produced");
            log.error(e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(e).build();
        }
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(RegisterRequest request) {
        String hashedPassword = PasswordUtils.hashPassword(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);
        return Response.ok().build();
    }

    @POST
    @Path("/refresh")
    @Consumes("application/json")
    public Response refreshToken(RefreshRequest request) {
        // Validate the refresh token and generate a new JWT
        String newAccessToken = tokenService.accessTokenFromRefreshToken(request.getRefreshToken());
        String newRefreshToken = tokenService.refreshTokenFromRefreshToken(request.getRefreshToken());
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(newAccessToken, newRefreshToken);
        return Response.ok(refreshTokenResponse).build();
    }

}
