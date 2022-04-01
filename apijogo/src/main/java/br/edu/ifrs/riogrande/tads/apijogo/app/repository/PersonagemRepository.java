package br.edu.ifrs.riogrande.tads.apijogo.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;

public interface PersonagemRepository extends Repository<Personagem, UUID> {

	Personagem save(Personagem p);

	List<Personagem> findAll();

	Optional<Personagem> findById(UUID id);

}
