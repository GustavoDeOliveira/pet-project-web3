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
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionarXpRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AtualizarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.CriarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.PersonagemCriadoResponse;

@Service
public class PersonagemService {

	private final PersonagemRepository repository;

	@Autowired
	public PersonagemService(PersonagemRepository repository) {
		this.repository = repository;
	}

	public PersonagemCriadoResponse salvar(CriarPersonagemRequest request) throws IllegalArgumentException {
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");

		// mapeamento da requisição
		Personagem personagem = new Personagem(request.getNome(), request.getClasse());

		// execução
		personagem = repository.save(personagem);

		// mapeamento da resposta
		PersonagemCriadoResponse response = new PersonagemCriadoResponse();
		response.setId(personagem.getId());

		return response;
	}

	public void salvar(UUID id, AtualizarPersonagemRequest request)
		throws
		IllegalArgumentException,
		EntidadeNaoEncontradaException {
			
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");
		if (request.getNome() == null) throw new IllegalArgumentException("Nome deve ser informado");

		Optional<Personagem> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Personagem.class);

		// mapeamento
		Personagem personagem = p.get();

		personagem.setNome(request.getNome());

		repository.save(personagem);
	}

	public List<Personagem> listar() {
		return repository.findAll();
	}

	public Personagem carregar(UUID id) throws EntidadeNaoEncontradaException {
		Optional<Personagem> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Personagem.class);

		return p.get();
	}

	public void remover(UUID id)
			throws EntidadeNaoEncontradaException {

		Optional<Personagem> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Personagem.class);
		
		repository.removeById(id);
	}

	public void adicionarXp(UUID id, AdicionarXpRequest request) throws EntidadeNaoEncontradaException {

		Optional<Personagem> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Personagem.class);

		var personagem = p.get();

		personagem.adicionarExperiencia(request.getXp());

		personagem = repository.save(personagem);
	}

}
