package br.edu.ifrs.riogrande.tads.apijogo.app.model;

public class ClassePersonagemGuerreiro extends ClassePersonagem {

    public int getAtaque() {
        int nvl = getNivel();
        return (int) ((nvl*10)+(nvl*0.1));
    }

    public int getDefesa() {
        int nvl = getNivel();
        return (int) ((nvl*10)+(nvl*0.5));
    }

    public int getVida() {
        int nvl = getNivel();
        return (int) ((nvl*10)+(nvl));
    }

    protected long getXpParaProximoNivel() {
        int nvl = getNivel();
        return nvl * 10 + (int) Math.pow(nvl, 2);
    }
}