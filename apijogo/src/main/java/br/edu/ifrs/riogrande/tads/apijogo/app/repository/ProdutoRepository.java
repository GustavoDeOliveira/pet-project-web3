package br.edu.ifrs.riogrande.tads.apijogo.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;

public interface ProdutoRepository extends Repository<Produto, UUID> {

	Produto save(Produto p);

	Optional<Produto> findById(UUID id);

	List<Produto> findAll();

	void removeById(UUID id);

	int count();

}
