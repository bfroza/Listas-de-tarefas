package com.uri.progweb.userdep.exceptions;

public class PrioridadeNotFoundException extends RuntimeException {
    public PrioridadeNotFoundException(String s) {
        super("Prioridade não encontrada");
    }
}
