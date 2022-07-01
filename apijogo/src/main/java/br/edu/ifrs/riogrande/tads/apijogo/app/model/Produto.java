package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produtos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Produto extends Entidade {
    
    @Column(name = "nome", length = 256, nullable = false, unique = true)
	private String nome;

    @Column(name = "valor", nullable = false, unique = false)
    private double valor;

    @Embedded
    private EfeitoItem efeito;
    
    @Column(name = "duracao", nullable = true, unique = false)
    private Integer duracao;

    @Column(name = "estoque", nullable = false, unique = false)
    private Integer estoque;

    public Produto(String nome, double valor, EfeitoItem efeito, Integer duracao, Integer estoque) {
        this.nome = nome;
        this.valor = valor;
        this.efeito = efeito;
        this.duracao = duracao;
        this.estoque = estoque;
    }

    public Item comprar() throws LojaSemEstoqueException {

        if (this.getEstoque() < 1)
            throw new LojaSemEstoqueException(this);

        this.setEstoque(this.getEstoque() - 1);

        return Item.from(this);
    }
}