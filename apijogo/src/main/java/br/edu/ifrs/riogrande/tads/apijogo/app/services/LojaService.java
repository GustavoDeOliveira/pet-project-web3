package br.edu.ifrs.riogrande.tads.apijogo.app.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.ItemRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.ProdutoRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.ComprarItemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.ItemCompradoResponse;

@Service
public class LojaService {
    
    private final ProdutoRepository repository;
    private final ItemRepository itemRepository;

    @Autowired
    public LojaService(ProdutoRepository repository, ItemRepository itemRepository) {
		this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public ItemCompradoResponse ComprarItem(ComprarItemRequest request)
            throws EntidadeNaoEncontradaException, LojaSemEstoqueException {

        // validação
        Objects.requireNonNull(request, "É necessária uma requsição");
        if (request.getProdutoId() == null) throw new IllegalArgumentException("Id do produto deve ser informado");
        
        var produto = repository
            .findById(request.getProdutoId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                request.getProdutoId(),
                Produto.class));

        var item = produto.comprar();

        item = itemRepository.save(item);
        produto = repository.save(produto);

        return new ItemCompradoResponse(item.getId());
    }

    public List<Produto> listar() {
		return repository.findAll();
	}

	public Produto carregar(UUID id) throws EntidadeNaoEncontradaException {
		Optional<Produto> p = repository.findById(id);

		if (p.isEmpty()) throw new EntidadeNaoEncontradaException(id, Produto.class);

		return p.get();
	}
}
