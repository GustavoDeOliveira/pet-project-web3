package br.edu.ifrs.riogrande.tads.apijogo.app.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Pessoa;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PessoaRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.PessoaRequest;

@Service
public class PessoaService {

	private final PessoaRepository repository;

	@Autowired
	public PessoaService(PessoaRepository repository) {
		this.repository = repository;
	}

	public void salvar(PessoaRequest request) {
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");
		if (request.getCpf() == null) throw new IllegalArgumentException("CPF deve ser informado");
		if (request.getNome() == null) throw new IllegalArgumentException("Nome deve ser informado");

		// mapeamento
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(request.getCpf());
		pessoa.setNome(request.getNome());

		repository.save(pessoa);
	}

	public List<Pessoa> listar() {
		return repository.findAll();
	}

	public Optional<Pessoa> find(String cpf) {
		return repository.findByCpf(cpf);
	}

}
