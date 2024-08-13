package org.acme.entities;

public record University(
    String idUni,
    String nomFrUni,
    String nomArUni,
    byte[] photoUni
) {}