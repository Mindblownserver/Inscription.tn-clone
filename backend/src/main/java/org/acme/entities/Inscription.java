package org.acme.entities;

public record Inscription(
    Etudiant etu,
    String au,
    Fill_Etab fill_etab,
    int paid
) {}