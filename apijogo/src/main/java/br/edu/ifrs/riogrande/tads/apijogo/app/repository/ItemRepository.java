package br.edu.ifrs.riogrande.tads.apijogo.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;

public interface ItemRepository extends Repository<Item, UUID> {

	Item save(Item p);

	List<Item> findByPersonagemId(UUID personagemId);

	Optional<Item> findById(UUID id);

	void removeById(UUID id);

}
