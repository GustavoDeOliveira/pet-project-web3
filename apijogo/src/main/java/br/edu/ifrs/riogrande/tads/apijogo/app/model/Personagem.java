package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;

@Entity
@Table(name = "personagens")
public class Personagem {

    @Id
    @GeneratedValue
    private final UUID id;
    
    @Column(name = "nome", length = 256, nullable = false, unique = false)
	private String nome;

    @Column(name = "classe", nullable = false, unique = false)
    private EnumClassePersonagem classe;
    
    public Personagem(UUID id) {
		this.id = id;
	}
    
    public String getNome() {
        return nome;
    }
	public void setNome(String nome) {
        this.nome = nome;
    }
    
    public EnumClassePersonagem getClasse() {
        return classe;
    }
    public void setClasse(EnumClassePersonagem classe) {
        this.classe = classe;
    }

    public UUID getId() {
        return id;
    }

    @Override
	public String toString() {
		return String.format("Personagem %s, %s", nome, classe);
	}
}