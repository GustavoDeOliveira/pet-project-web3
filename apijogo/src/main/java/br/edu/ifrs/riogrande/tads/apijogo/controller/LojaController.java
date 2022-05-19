package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.LojaService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.ComprarItemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.ItemCompradoResponse;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/loja")
public class LojaController extends Controller {

	private final LojaService lojaService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produto>> listar() {

		return ResponseEntity.ok(lojaService.listar());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {

		return ResponseEntity.ok(lojaService.carregar(id));
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
