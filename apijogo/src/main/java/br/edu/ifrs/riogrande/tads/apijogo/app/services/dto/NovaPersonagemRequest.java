package br.edu.ifrs.riogrande.tads.apijogo.app.services.dto;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;

public class NovaPersonagemRequest {

	private EnumClassePersonagem classe;
	private String nome;

	public EnumClassePersonagem getClasse() {
		return classe;
	}

	public void setClasse(EnumClassePersonagem classe) {
		this.classe = classe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    @Override
	public String toString() {
		return String.format("Personagem %s, %s", nome, classe);
	}

}
