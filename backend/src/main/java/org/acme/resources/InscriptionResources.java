package org.acme.resources;

import java.sql.SQLException;
import java.util.List;

import org.acme.entities.Inscription;
import org.acme.repositories.InscriptionRepository;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/")
public class InscriptionResources {
    @Inject  
    InscriptionRepository inscriptionRepository;

    @Path("inscriptions")
    @GET
    @RolesAllowed("admin")
    public Response getInscriptions(){
        try {
            List<Inscription> inscriptionList = inscriptionRepository.getInscriptions();
            if(inscriptionList.size()>0)
                return Response.ok(inscriptionList).build();
            return Response.status(404).entity("Inscriptions Not Found").build();
        } catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }
    @Path("inscription/{cin}")
    @GET
    @RolesAllowed("student")
    public Response getInscription(@PathParam("cin") String cin){
        try {
            List<Inscription> inscriptionList = inscriptionRepository.getInscription(cin);
            if(inscriptionList.size()>0)
                return Response.ok(inscriptionList).build();
            return Response.status(404).entity("Inscriptions Not Found").build();
        } catch(SQLException sqlException){
            Log.error(sqlException);
            return Response.status(500).entity("An error occured in the server").build();
        }catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }
    

}
