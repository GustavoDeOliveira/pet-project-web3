package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.InventarioService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.PersonagemService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionadoNoInventarioResponse;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionarNoInventarioRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionarXpRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AtualizarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.CriarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ErroResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ItemResumoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.MetaResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.PersonagemDetalheResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.PersonagemResumoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata.ListMeta;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/personagens")
public class PersonagemController extends BaseController {

	private final PersonagemService service;
	private final InventarioService inventarioService;

	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Dados da requisição inválidos.", response = ErroResponse.class),
		@ApiResponse(code = 201, message = "Personagem criada com sucesso.", response = Object.class,
			responseHeaders = {@ResponseHeader(name = "location")})})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(
			@RequestBody CriarPersonagemRequest body) {

		var response = service.salvar(body);

		return created(
			"/{personagemId}",
			Map.of("personagemId", response.getId()));
	}

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MetaResponseWrapper<List<PersonagemResumoResponse>, ListMeta>> listar() {

		return listResponse(service.listar(), (p) -> PersonagemResumoResponse.from(p));
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<PersonagemDetalheResponse>> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {

		var personagem = service.carregar(id);

		return ResponseEntity.ok(ResponseWrapper.wrap(
			PersonagemDetalheResponse.from(personagem)));
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizar(
			@PathVariable UUID id,
			@RequestBody AtualizarPersonagemRequest body)
			throws	IllegalArgumentException,
					EntidadeNaoEncontradaException {

		service.salvar(id, body);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> remover(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {

		service.remover(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseWrapper<PersonagemDetalheResponse>> adicionarXp(
			@PathVariable UUID id,
			@RequestBody AdicionarXpRequest request)
			throws EntidadeNaoEncontradaException {
		service.adicionarXp(id, request);
		var personagem = service.carregar(id);

		return ResponseEntity.ok(ResponseWrapper.wrap(
			PersonagemDetalheResponse.from(personagem)));
	}

	@PatchMapping(path = "/{personagemId}/inventario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> adicionarNoInventario(
			@PathVariable UUID personagemId,
			@RequestBody AdicionarNoInventarioRequest body)
			throws IllegalArgumentException,
			EntidadeNaoEncontradaException {

		AdicionadoNoInventarioResponse response = inventarioService.adicionarNoInventario(personagemId, body);
		
		return created(
			"/{personagemId}/inventario/{itemId}", 
			Map.of(
				"personagemId", personagemId,
				"itemId", response.getItemId()));
	}

	@GetMapping(path = "/{personagemId}/inventario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MetaResponseWrapper<List<ItemResumoResponse>, ListMeta>> listarInventario(
			@PathVariable UUID personagemId) {

		return listResponse(inventarioService.listar(personagemId), (i) -> ItemResumoResponse.from(i));
	}

	@GetMapping(path = "/{personagemId}/inventario/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> lerItem(
			@PathVariable UUID personagemId,
			@PathVariable UUID itemId)
			throws EntidadeNaoEncontradaException {

		return ResponseEntity.ok(inventarioService.carregar(personagemId, itemId));
	}
}
