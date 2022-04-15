package br.edu.ifrs.riogrande.tads.apijogo.app.util;

import org.springframework.stereotype.Component;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.AtributosPersonagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;

@Component
public class CalculadorAtributosPersonagem {

    public AtributosPersonagem Calcular(EnumClassePersonagem enumClasse, int nivel, long xp) {

        ClassePersonagem classe;

        switch (enumClasse) {
            case Guerreiro:
                classe = new ClasseGuerreiro();
                break;
            case Ladino:
                classe = new ClasseLadino();
                break;
            case Mago:
                classe = new ClasseMago();
                break;
        
            default:
                throw new IllegalArgumentException("Necessário fornecer uma classe válida.");
        }

        AtributosPersonagem atributos = new AtributosPersonagem();

        atributos.setXp(xp);
        atributos.setNivel(nivel);
        atributos.setVida(classe.getVida());
        atributos.setAtaque(classe.getAtaque());
        atributos.setDefesa(classe.getDefesa());

        return atributos;
    }

}
