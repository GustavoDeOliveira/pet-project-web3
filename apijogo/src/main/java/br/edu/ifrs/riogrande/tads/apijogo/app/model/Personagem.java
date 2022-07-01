package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.util.ClasseGuerreiro;
import br.edu.ifrs.riogrande.tads.apijogo.app.util.ClasseLadino;
import br.edu.ifrs.riogrande.tads.apijogo.app.util.ClasseMago;
import br.edu.ifrs.riogrande.tads.apijogo.app.util.ClassePersonagem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "personagens")
public class Personagem extends Entidade {

    @Column(name = "nome", length = 256, nullable = false, unique = false)
	private String nome;

    @Column(name = "classe", nullable = false, unique = false)
    private EnumClassePersonagem classeEnum;

    @Column(name = "dano", nullable = false, unique = false)
    private int dano;

    @Embedded
    private AtributosPersonagem atributos;

    @OneToMany(mappedBy = "personagem")
    private List<Item> inventario;

    public Personagem(String nome, EnumClassePersonagem classe) {

		if (classe == null) throw new IllegalArgumentException("Classe deve ser informada");
        if (nome == null) throw new IllegalArgumentException("Nome deve ser informado");

        this.nome = nome;
        this.classeEnum = classe;
        
        setAtributos(getClasse(0).comoAtributos());
    }

    public void adicionarExperiencia(long xp) {
        
		if (xp < 0)
            throw new IllegalArgumentException("Não é possível remover xp de uma personagem.");

        long xpAtual = getAtributos().getXp();

        setAtributos(getClasse(xpAtual)
            .addXp(xp)
            .comoAtributos());
    }

    public ClassePersonagem getClasse(long xp) {

        switch (classeEnum) {
            case Guerreiro:
                return new ClasseGuerreiro(xp);
            case Ladino:
                return new ClasseLadino(xp);
            case Mago:
                return new ClasseMago(xp);
        
            default:
                throw new IllegalArgumentException("Necessário fornecer uma classe válida.");
        }
    }
}