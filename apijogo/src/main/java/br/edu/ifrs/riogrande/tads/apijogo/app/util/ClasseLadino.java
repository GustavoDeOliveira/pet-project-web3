package br.edu.ifrs.riogrande.tads.apijogo.app.util;

public class ClasseLadino extends ClassePersonagem {

    public ClasseLadino(long xp) {
        super(xp);
	}

	public int getAtaque() {
        int nvl = getNivel();
        return nvl + (int) Math.pow(nvl/2, 2);
    }

    public int getDefesa() {
        int nvl = getNivel();
        return nvl + (int) Math.pow(nvl/2, 2);
    }

    public int getVida() {
        int nvl = getNivel();
        return nvl + (int) Math.pow(nvl/2, 2);
    }

    protected long getXpParaProximoNivel() {
        int nvl = getNivel();
        return nvl * 10 + (int) Math.pow(nvl/2, 2);
    }
}