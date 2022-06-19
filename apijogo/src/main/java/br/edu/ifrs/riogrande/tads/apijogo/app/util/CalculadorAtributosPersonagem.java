package br.edu.ifrs.riogrande.tads.apijogo.app.util;

import org.springframework.stereotype.Component;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.AtributosPersonagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;

@Component
public class CalculadorAtributosPersonagem {

    public AtributosPersonagem Calcular(EnumClassePersonagem enumClasse, long xp) {

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

        classe.addXp(xp);

        return classe.comoAtributos();
    }

}
