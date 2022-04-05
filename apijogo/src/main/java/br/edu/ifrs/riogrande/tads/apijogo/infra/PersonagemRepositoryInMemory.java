package br.edu.ifrs.riogrande.tads.apijogo.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PersonagemRepository;

public class PersonagemRepositoryInMemory implements PersonagemRepository {

	private static Map<UUID, Personagem> memory = new HashMap<>();

	@Override
	public Personagem save(Personagem p) {
		memory.put(p.getId(), p);
		return p;
	}

	@Override
	public List<Personagem> findAll() {
		return new ArrayList<Personagem>(memory.values());
	}

	@Override
	public Optional<Personagem> findById(UUID id) {
		Personagem p = memory.get(id);
		return Optional.ofNullable(p);
	}

}
