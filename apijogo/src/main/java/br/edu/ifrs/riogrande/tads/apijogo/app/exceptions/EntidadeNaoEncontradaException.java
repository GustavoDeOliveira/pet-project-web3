package br.edu.ifrs.riogrande.tads.apijogo.app.exceptions;

import java.util.UUID;

public class EntidadeNaoEncontradaException extends Exception {

    public EntidadeNaoEncontradaException(UUID entityId, String entityClass) {
        super(String.format("Não foi possível encontrar %s com id %s", entityClass, entityId));
    }

    public EntidadeNaoEncontradaException(UUID entityId, Class<?> entityClass) {
        this(entityId, entityClass.getSimpleName());
    }

}
