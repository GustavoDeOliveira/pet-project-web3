package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public abstract class ClassePersonagem {

    @Column(name = "xp", nullable = false, unique = false)
    protected long xp;

    @Column(name = "nivel", nullable = false, unique = false)
    protected int nivel;

    public abstract int getAtaque();
    public abstract int getDefesa();
    public abstract int getVida();

    protected abstract long getXpParaProximoNivel();

    public final int getNivel() {
        return nivel;
    }
    
    public final long getXp() {
        return xp;
    }

    public final boolean addXp(int qtd) {
        xp += qtd;
        if (getXpParaProximoNivel() <= xp) {
            nivel++;
            return true;
        }
        return false;
    }
}