package org.acme.resources;

import java.sql.SQLException;

import org.acme.entities.Etablissement;
import org.acme.exceptions.UnAuthorizedEntityAccess;
import org.acme.repositories.EtabRepository;
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

@Path("/api/university")
@Consumes(MediaType.APPLICATION_JSON)
public class EtabResource {
    
    @Inject
    EtabRepository etabRepository;

    @Inject
    JsonWebToken jwt;

    @Path("{idFac}")
    @GET
    @RolesAllowed("university")
    public Response getEtab(@PathParam("idFac") String idFac){
        String requestUsername = jwt.getClaim("upn");
        if(!idFac.toUpperCase().equals(requestUsername.toUpperCase())){
            throw new UnAuthorizedEntityAccess(String.format("Etab %s can't access data of the Etab %s", requestUsername, idFac));
        }
        try {
            Etablissement etab = etabRepository.getEtablissementById(idFac.toUpperCase());
            if(etab !=null)
                return Response.ok(etab).build();
            return Response.status(404).entity("Etablissement Not Found").build();
        } catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch (Exception e) {
            return Response.status(401).entity(e).build();
        }
    }

    @PUT
    @RolesAllowed({"university", "admin"})
    @Transactional
    public Response updateEtab(Etablissement etab){
        try{
            boolean exist = etabRepository.existEtablissement(etab.idEtab());
            if(exist){
                boolean isUpdated = etabRepository.updateEtudiant(etab);
                if(isUpdated)
                    return Response.ok("Etablissement est mis à jour").build();
                return Response.status(500).entity("L'operation update est échoué").build();
            }else{
                return Response.status(404).entity("Etablissement inexistant").build();
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
