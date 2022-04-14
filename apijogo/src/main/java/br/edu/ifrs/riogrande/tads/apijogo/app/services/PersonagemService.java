package br.edu.ifrs.riogrande.tads.apijogo.app.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PersonagemRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.EditarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.NovaPersonagemRequest;

@Service
public class PersonagemService {

	private final PersonagemRepository repository;

	@Autowired
	public PersonagemService(PersonagemRepository repository) {
		this.repository = repository;
	}

	public void salvar(NovaPersonagemRequest request) throws IllegalArgumentException {
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");
		if (request.getClasse() == null) throw new IllegalArgumentException("Classe deve ser informada");
		if (request.getNome() == null) throw new IllegalArgumentException("Nome deve ser informado");

		// mapeamento
		Personagem personagem = new Personagem(
			UUID.randomUUID(),
			request.getNome(),
			request.getClasse());

		repository.save(personagem);
	}

	public void salvar(UUID id, EditarPersonagemRequest request)
		throws
		IllegalArgumentException,
		EntidadeNaoEncontradaException {
			
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");
		if (request.getNome() == null) throw new IllegalArgumentException("Nome deve ser informado");

		Optional<Personagem> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Personagem.class.getSimpleName());

		// mapeamento
		Personagem personagem = p.get();

		personagem.setNome(request.getNome());

		repository.save(personagem);
	}

	public List<Personagem> listar() {
		return repository.findAll();
	}

	public Optional<Personagem> find(UUID id) {
		return repository.findById(id);
	}

}
