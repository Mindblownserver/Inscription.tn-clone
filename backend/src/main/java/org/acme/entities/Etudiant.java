package org.acme.entities;

import java.util.Date;

public record Etudiant(
    String cin,
    String nomFrEtu,
    String prenomFrEtu,
    String nomArEtu,
    String prenomArEtu,
    Date dateNaiss,
    String nomPere,
    String prenomPere,
    String professionPere,
    String nomMere,
    String prenomMere,
    String professionMere,
    byte[] photoEtu // BLOB as byte array
) {}