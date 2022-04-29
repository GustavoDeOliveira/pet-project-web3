package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class EfeitoItem {

    @Column(name = "ataque", nullable = false, unique = false)
    private int ataque;

    @Column(name = "defesa", nullable = false, unique = false)
    private int defesa;

    @Column(name = "vida", nullable = false, unique = false)
    private int vida;

    @Column(name = "cura", nullable = false, unique = false)
    private int cura;
}