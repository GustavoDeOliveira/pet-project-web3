package br.edu.ifrs.riogrande.tads.apijogo.app.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.ItemRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PersonagemRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionadoNoInventarioResponse;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionarNoInventarioRequest;

@Service
public class InventarioService {

	private final ItemRepository itemRepository;
	private final PersonagemRepository personagemRepository;

	@Autowired
	public InventarioService(ItemRepository itemRepository, PersonagemRepository personagemRepository) {
		this.itemRepository = itemRepository;
		this.personagemRepository = personagemRepository;
	}

	public AdicionadoNoInventarioResponse adicionarNoInventario(UUID personagemId, AdicionarNoInventarioRequest request) throws IllegalArgumentException, EntidadeNaoEncontradaException {
		// validação
		Objects.requireNonNull(request, "É necessária uma requsição");
		Objects.requireNonNull(personagemId, "É necessário um PersonagemId");
		if (request.getItemId() == null) throw new IllegalArgumentException("ItemId deve ser informado");

		// mapeamento da requisição
		Personagem personagem = personagemRepository
			.findById(personagemId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(personagemId, Personagem.class));

		Item item = itemRepository
			.findById(request.getItemId())
			.orElseThrow(() -> new EntidadeNaoEncontradaException(request.getItemId(), Item.class));

		// execução
		item.setPersonagem(personagem);
		item = itemRepository.save(item);

		// mapeamento da resposta
		AdicionadoNoInventarioResponse resposta = new AdicionadoNoInventarioResponse();
		resposta.setItemId(item.getId());

		return resposta;
	}

	public List<Item> listar(UUID personagemId) {
		return itemRepository.findByPersonagemId(personagemId);
	}

	public Item carregar(UUID personagemId, UUID itemId) throws EntidadeNaoEncontradaException {
		personagemRepository
			.findById(personagemId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(personagemId, Personagem.class));

		Item item = itemRepository
			.findById(itemId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(itemId, Item.class));

		return item;
	}

	public void remover(UUID personagemId, UUID itemId)
			throws EntidadeNaoEncontradaException {

		personagemRepository
			.findById(personagemId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(personagemId, Personagem.class));

		itemRepository
			.findById(itemId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(itemId, Item.class));
		
		itemRepository.removeById(itemId);
	}

}
