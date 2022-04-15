package br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests;

public class AtualizarPersonagemRequest {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    @Override
	public String toString() {
		return String.format("Personagem %s", nome);
	}

}
