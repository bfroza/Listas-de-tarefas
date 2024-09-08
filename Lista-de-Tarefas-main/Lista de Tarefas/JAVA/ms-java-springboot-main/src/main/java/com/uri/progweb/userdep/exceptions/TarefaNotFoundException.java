package com.uri.progweb.userdep.exceptions;

public class TarefaNotFoundException extends RuntimeException {

    public TarefaNotFoundException() {
        super("Tarefa não encontrado!");
    }

    public TarefaNotFoundException(String msg) {
        super(msg);
    }
}
