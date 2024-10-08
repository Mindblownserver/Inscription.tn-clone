package org.acme.resources;

import java.sql.SQLException;
import java.util.List;

import org.acme.entities.Etudiant;
import org.acme.exceptions.UnAuthorizedEntityAccess;
import org.acme.repositories.EtudiantRepository;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/api/etudiant")
public class EtudiantResources {
    @Inject
    EtudiantRepository etudiantRepository;

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed("admin")
    public Response getEtudiants(){
        try {
            List<Etudiant> etuList = etudiantRepository.getEtudiants();
            if(etuList.size()>0)
                return Response.ok(etuList).build();
            return Response.status(404).entity("Etudiants Not Found").build();
        }catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }

    @Path("/{cin}")
    @GET
    @RolesAllowed("student")
    public Response getEtudiant(@PathParam("cin") String cin){
        String requestUsername = jwt.getClaim("upn");
        if(!cin.toUpperCase().equals(requestUsername.toUpperCase()) && !requestUsername.equals("admin")){
            throw new UnAuthorizedEntityAccess(String.format("Etudiant %s can't access data of the Etudiant %s", requestUsername, cin));
        }
        try {
            Etudiant etudiant = etudiantRepository.getEtudiantByCin(cin);
            if(etudiant !=null)
                return Response.ok(etudiant).build();
            return Response.status(404).entity("Etudiant Not Found").build();
        } catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch (Exception e) {
            return Response.status(401).entity(e).build();
        }
    }

    @PUT
    @RolesAllowed({"student", "admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateEtudiant(Etudiant etu){
        String requestUsername = jwt.getClaim("upn");
        if(!etu.cin().toUpperCase().equals(requestUsername.toUpperCase()) && !requestUsername.equals("admin")){
            throw new UnAuthorizedEntityAccess(String.format("Etudiant %s can't access data of the Etudiant %s", requestUsername, etu.cin()));
        }
        try{
            boolean exist = etudiantRepository.existEtudiant(etu.cin());
            if(exist){
                boolean isUpdated = etudiantRepository.updateEtudiant(etu);
                if(isUpdated)
                    return Response.ok("Etudiant est mis à jour").build();
                return Response.status(500).entity("L'operation update est échoué").build();
            }else{
                return Response.status(404).entity("Etudiant inexistant").build();
            }
        }catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch(Exception e){
            Log.error(e);
            
            return Response.status(Status.UNAUTHORIZED).entity(e).build();
        }
    }
}
