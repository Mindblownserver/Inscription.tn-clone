package org.acme.entities;

public record Etablissement(
    String idEtab,
    String nomFrEtab,
    String nomArEtab,
    University uni,
    byte[] photoEtab // Changed to byte array
) {}

