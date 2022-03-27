package br.edu.ifrs.riogrande.tads.apijogo.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Pessoa;

@Repository
public interface PessoaRepository {

	Pessoa save(Pessoa p);

	List<Pessoa> findAll();

	Optional<Pessoa> findByCpf(String cpf);

}
