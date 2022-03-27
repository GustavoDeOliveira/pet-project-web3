package br.edu.ifrs.riogrande.tads.apijogo.app.model;

public class Pessoa {

	private String cpf; // chave
	private String nome;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Pessoa " + cpf + " " + nome;
	}

}
