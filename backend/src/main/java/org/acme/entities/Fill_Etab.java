package org.acme.entities;

public record Fill_Etab (
    Etablissement etab,
    Filliere fill,
    int periode,
    int prixTotal,
    int prixTr1,
    int prixTr2
){}
