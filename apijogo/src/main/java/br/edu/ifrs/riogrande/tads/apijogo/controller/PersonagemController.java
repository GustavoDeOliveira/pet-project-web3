package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.PersonagemService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.EditarPersonagemRequest;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.NovaPersonagemRequest;

@Validated
@RestController
@RequestMapping("/api/v1/personagens")
public class PersonagemController { // definir o resource: Personagem (api Personagem)

	private final PersonagemService service;

	@Autowired // Injeção de Dependência
	public PersonagemController(PersonagemService service) {
		this.service = service;
	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> nova(
			@RequestBody NovaPersonagemRequest body) {

		try {
			service.salvar(body);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException ex) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("erro", ex.getMessage()));
		}
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editar(
			@PathVariable UUID id,
			@RequestBody EditarPersonagemRequest body) {

		try {
			service.salvar(id, body);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException ex) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("erro", ex.getMessage()));
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Personagem>> listar() {

		List<Personagem> personagens = service.listar();

		return ResponseEntity.ok(personagens);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(
		@PathVariable UUID id) {

		Optional<Personagem> personagem = service.find(id);

		// retorno
		if (personagem.isEmpty()) return ResponseEntity.notFound().build();

		return ResponseEntity.ok(personagem.get());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	Map<String, List<String>> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<String> erros = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());
		return Map.of("erros", erros);
	}
}
