package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class AtributosPersonagem {

    @Column(name = "xp", nullable = false, unique = false)
    private long xp;

    @Column(name = "nivel", nullable = false, unique = false)
    private int nivel = 1;

    @Column(name = "xp_para_proximo_nivel", nullable = false, unique = false)
    private long xpParaProximoNivel;

    @Column(name = "ataque", nullable = false, unique = false)
    private int ataque;

    @Column(name = "defesa", nullable = false, unique = false)
    private int defesa;

    @Column(name = "vida", nullable = false, unique = false)
    private int vida;

    public long getXp() {
        return xp;
    }
}