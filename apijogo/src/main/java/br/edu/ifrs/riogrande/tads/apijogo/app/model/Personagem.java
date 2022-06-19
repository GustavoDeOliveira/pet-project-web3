package br.edu.ifrs.riogrande.tads.apijogo.app.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;
import lombok.Data;

@Entity
@Data
@Table(name = "personagens")
public class Personagem {

    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(name = "nome", length = 256, nullable = false, unique = false)
	private String nome;

    @Column(name = "classe", nullable = false, unique = false)
    private EnumClassePersonagem classe;

    @Column(name = "dano", nullable = false, unique = false)
    private int dano;

    @Embedded
    private AtributosPersonagem atributos;

    @OneToMany(mappedBy = "personagem")
    private List<Item> inventario;
}