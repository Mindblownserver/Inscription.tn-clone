package org.acme.resources;

import java.util.List;

import org.acme.entities.Etudiant;
import org.acme.repositories.EtudiantRepository;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class EtudiantResources {
    @Inject
    EtudiantRepository etudiantRepository;

    @Path("/etudiant")
    @GET
    @RolesAllowed("admin")
    public Response getEtudiants(){
        try {
            List<Etudiant> etuList = etudiantRepository.getEtudiants();
            if(etuList.size()>0)
                return Response.ok(etuList).build();
            return Response.status(404).entity("Etudiants Not Found").build();
        } catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }

    @Path("/etudiant/{cin}")
    @GET
    @RolesAllowed("student")
    public Response getEtudiant(@PathParam("cin") String cin){
        try {
            Etudiant etudiant = etudiantRepository.getEtudiantByCin(cin);
            if(etudiant !=null)
                return Response.ok(etudiant).build();
            return Response.status(404).entity("Etudiant Not Found").build();
        } catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }
    // get etudiants by etab

}
