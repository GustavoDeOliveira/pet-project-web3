package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itens")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends Entidade {

    @ManyToOne
    @JoinColumn(name = "personagem_id", nullable = true)
    private Personagem personagem;
    
    @Column(name = "nome", length = 256, nullable = false, unique = false)
	private String nome;

    @Column(name = "valor", nullable = false, unique = false)
    private double valor;

    @Embedded
    private EfeitoItem efeito;
    
    @Column(name = "duracao", nullable = true, unique = false)
    private Integer duracao;

    public static Item from(Produto produto) {
        var item = new Item();
        item.setNome(produto.getNome());
        item.setValor(produto.getValor());
        item.setDuracao(produto.getDuracao());
        item.setEfeito(produto.getEfeito());

        return item;
    }
}