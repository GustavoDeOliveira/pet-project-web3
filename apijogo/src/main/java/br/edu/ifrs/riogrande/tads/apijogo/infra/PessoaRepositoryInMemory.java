package br.edu.ifrs.riogrande.tads.apijogo.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Pessoa;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PessoaRepository;

@Component
public class PessoaRepositoryInMemory implements PessoaRepository {

	private static Map<String, Pessoa> memory = new HashMap<>();

	@Override
	public Pessoa save(Pessoa p) {
		memory.put(p.getCpf(), p);
		return p;
	}

	@Override
	public List<Pessoa> findAll() {
		return new ArrayList<Pessoa>(memory.values());
	}

	@Override
	public Optional<Pessoa> findByCpf(String cpf) {
		Pessoa p = memory.get(cpf);
		return Optional.ofNullable(p);
	}


}
