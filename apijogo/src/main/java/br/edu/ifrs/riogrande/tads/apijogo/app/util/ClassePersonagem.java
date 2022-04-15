package br.edu.ifrs.riogrande.tads.apijogo.app.util;

public abstract class ClassePersonagem {

    protected long xp;

    protected int nivel = 1;

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