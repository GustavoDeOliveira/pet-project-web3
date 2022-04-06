package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
    private final EnumClassePersonagem classe;

    @Column(name = "dano", nullable = false, unique = false)
    private int dano;

    @Embedded
    private ClassePersonagem classeInfo;
    
    public Personagem(UUID id, String nome, EnumClassePersonagem classe) throws IllegalArgumentException {
        this.id = id;
        this.nome = nome;
        this.classe = classe;

        switch (classe) {
            case Guerreiro:
                classeInfo = new ClassePersonagemGuerreiro();
                break;
            case Ladino:
                classeInfo = new ClassePersonagemLadino();
                break;
            case Mago:
                classeInfo = new ClassePersonagemMago();
                break;
        
            default:
                throw new IllegalArgumentException("Necessário fornecer uma classe válida.");
        }
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

    public UUID getId() {
        return id;
    }

    public int getAtaque() {
        return classeInfo.getAtaque();
    }

    public int getDefesa() {
        return classeInfo.getDefesa();
    }

    public int getVida() {
        return classeInfo.getVida();
    }

    public int getNivel() {
        return classeInfo.getNivel();
    }

    public long getXp() {
        return classeInfo.getXp();
    }

    public boolean addXp(int qtd) {
        return classeInfo.addXp(qtd);
    }

    @Override
	public String toString() {
		return String.format("Personagem %s, %s nível %d", nome, classe, getNivel());
	}
}