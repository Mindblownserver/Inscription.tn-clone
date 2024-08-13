package org.acme.resources;

import java.util.List;

import org.acme.entities.Etudiant;
import org.acme.repositories.EtudiantRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/api/etudiants")
public class EtudiantResources {
    @Inject
    EtudiantRepository etudiantRepository;

    @GET
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
}
