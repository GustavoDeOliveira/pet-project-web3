package br.edu.ifrs.riogrande.tads.apijogo.app.exceptions;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;

public class LojaSemEstoqueException extends Exception {
    
    public LojaSemEstoqueException(Produto p) {
        super(String.format("O produto %s '%s' está fora de estoque na loja.", p.getId(), p.getNome()));
    }
}
