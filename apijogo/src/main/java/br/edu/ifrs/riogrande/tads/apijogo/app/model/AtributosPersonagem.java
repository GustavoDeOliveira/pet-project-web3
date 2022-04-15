package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AtributosPersonagem {

    @Column(name = "xp", nullable = false, unique = false)
    private long xp;

    @Column(name = "nivel", nullable = false, unique = false)
    private int nivel = 1;

    @Column(name = "ataque", nullable = false, unique = false)
    private int ataque;

    @Column(name = "defesa", nullable = false, unique = false)
    private int defesa;

    @Column(name = "vida", nullable = false, unique = false)
    private int vida;

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    
}