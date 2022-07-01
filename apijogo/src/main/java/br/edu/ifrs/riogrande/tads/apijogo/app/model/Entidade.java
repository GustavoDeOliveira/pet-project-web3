package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class Entidade {
    @Id
    private final UUID id;

    public Entidade() {
        id = UUID.randomUUID();
    }
}
