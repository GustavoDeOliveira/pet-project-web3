package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue
    private UUID id;
    
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

    public Item comprar() throws LojaSemEstoqueException {

        if (this.getEstoque() < 1)
            throw new LojaSemEstoqueException(this);

        this.setEstoque(this.getEstoque() - 1);

        return Item.from(this);
    }
}