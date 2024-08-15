package org.acme.entities;

public record Etablissement(
    String idEtab,
    String nomFrEtab,
    String nomArEtab,
    University uni,
    String photoEtab // Changed to byte array
) {}

