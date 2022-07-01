package br.edu.ifrs.riogrande.tads.apijogo.app.util;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.AtributosPersonagem;

public abstract class ClassePersonagem {

    protected long xp;

    protected int nivel = 1;

    public ClassePersonagem(long xp) {
        addXp(xp);
    }

    public ClassePersonagem() { }

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

    public final ClassePersonagem addXp(long qtd) {
        xp += qtd;
        while (getXpParaProximoNivel() <= xp) {
            nivel++;
        }
        return this;
    }

    public final AtributosPersonagem comoAtributos() {
        AtributosPersonagem atributos = new AtributosPersonagem();

        atributos.setXp(getXp());
        atributos.setNivel(getNivel());
        atributos.setVida(getVida());
        atributos.setAtaque(getAtaque());
        atributos.setDefesa(getDefesa());
        atributos.setXpParaProximoNivel(getXpParaProximoNivel());

        return atributos;
    }
}