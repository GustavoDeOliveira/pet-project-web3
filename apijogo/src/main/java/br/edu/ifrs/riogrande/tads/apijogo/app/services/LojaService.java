package br.edu.ifrs.riogrande.tads.apijogo.app.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;
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

        if (produto.getEstoque() < 1)
            throw new LojaSemEstoqueException(produto);

        var item = new Item();
        item.setId(UUID.randomUUID());
        item.setNome(produto.getNome());
        item.setValor(produto.getValor());
        item.setDuracao(produto.getDuracao());
        item.setEfeito(produto.getEfeito());

        itemRepository.save(item);

        produto.setEstoque(produto.getEstoque() - 1);

        repository.save(produto);

        var response = new ItemCompradoResponse();
        response.setItemId(item.getId());

        return response;
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
