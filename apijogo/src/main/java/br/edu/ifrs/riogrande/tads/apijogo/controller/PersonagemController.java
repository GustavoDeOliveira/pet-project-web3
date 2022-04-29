package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import lombok.RequiredArgsConstructor;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Item;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.InventarioService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.PersonagemService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionadoNoInventarioResponse;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AdicionarNoInventarioRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.AtualizarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.requests.CriarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.responses.PersonagemCriadoResponse;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/personagens")
public class PersonagemController {

	private final PersonagemService service;
	private final InventarioService inventarioService;

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonagemCriadoResponse> criar(
			@RequestBody CriarPersonagemRequest body) {

		return ResponseEntity.ok(service.salvar(body));
	}

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Personagem>> listar() {

		return ResponseEntity.ok(service.listar());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {

		return ResponseEntity.ok(service.carregar(id));
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


	@PostMapping(path = "/{personagemId}/inventario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> adicionarNoInventario(
			@PathVariable UUID personagemId,
			@RequestBody AdicionarNoInventarioRequest body) throws IllegalArgumentException, EntidadeNaoEncontradaException {

		AdicionadoNoInventarioResponse response = inventarioService.adicionarNoInventario(personagemId, body);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{personagemId}/inventario/{itemId}")
			.buildAndExpand(Map.of(
				"personagemId", personagemId,
				"itemId", response.getItemId())).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping(path = "/{personagemId}/inventario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Item>> listarInventario(
			@PathVariable UUID personagemId) {

		return ResponseEntity.ok(inventarioService.listar(personagemId));
	}

	@GetMapping(path = "/{personagemId}/inventario/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> lerItem(
			@PathVariable UUID personagemId,
			@PathVariable UUID itemId)
			throws EntidadeNaoEncontradaException {

		return ResponseEntity.ok(inventarioService.carregar(personagemId, itemId));
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	Map<String, String> entidadeNaoEncontradaExceptionHandler(EntidadeNaoEncontradaException ex) {
		return Map.of("erro", ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	Map<String, String> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		return Map.of("erro", ex.getMessage());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	Map<String, List<String>> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<String> erros = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());
		return Map.of("erros", erros);
	}
}
