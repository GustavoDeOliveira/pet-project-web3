package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.LojaService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.ComprarItemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.ItemCompradoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.MetaResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoDetalheResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoResumoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata.ListMeta;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/loja")
public class LojaController extends BaseController {

	private final LojaService lojaService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MetaResponseWrapper<List<ProdutoResumoResponse>, ListMeta>> listar() {

		var produtos = lojaService.listar();

		return listResponse(produtos, (p) -> ProdutoResumoResponse.from(p));
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<ProdutoDetalheResponse>> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {
		
		var produto = lojaService.carregar(id);

		return ResponseEntity.ok(ResponseWrapper.wrap(ProdutoDetalheResponse.from(produto)));
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<ResponseWrapper<ItemCompradoResponse>> comprarItem(
			@PathVariable UUID id)
			throws IllegalArgumentException,
				EntidadeNaoEncontradaException,
				LojaSemEstoqueException {

		var request = new ComprarItemRequest();
		request.setProdutoId(id);

		ItemCompradoResponse response = lojaService.ComprarItem(request);
		
		return ResponseEntity.ok(ResponseWrapper.wrap(response));
	}
}
