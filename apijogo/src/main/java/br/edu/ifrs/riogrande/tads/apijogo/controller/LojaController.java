package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.LojaService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.ComprarItemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.ItemCompradoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoDetalheResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoResumoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata.ListMeta;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/loja")
public class LojaController {

	private final LojaService lojaService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<List<ProdutoResumoResponse>, ListMeta>> listar() {

		var produtos = lojaService.listar();
		var total = produtos.size();

		return ResponseEntity.ok(ResponseWrapper.wrap(
			produtos.stream()
				.map(p -> ProdutoResumoResponse.from(p))
				.collect(Collectors.toList()),
			new ListMeta(total, 0, total)));
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<ProdutoDetalheResponse, Void>> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {
		
		var produto = lojaService.carregar(id);

		return ResponseEntity.ok(ResponseWrapper.wrap(ProdutoDetalheResponse.from(produto)));
	}

	@PostMapping(path = "/{id}")
	public ResponseEntity<Object> comprarItem(
			@PathVariable UUID id)
			throws IllegalArgumentException,
				EntidadeNaoEncontradaException,
				LojaSemEstoqueException {

		var request = new ComprarItemRequest();
		request.setProdutoId(id);

		ItemCompradoResponse response = lojaService.ComprarItem(request);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/itens/{itemId}")
			.buildAndExpand(Map.of(
				"itemId", response.getItemId())).toUri();

		return ResponseEntity.created(location).build();
	}
}
