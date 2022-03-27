package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.Pessoa;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.PessoaService;
import br.edu.ifrs.riogrande.tads.apijogo.app.services.dto.PessoaRequest;

@RestController
@RequestMapping("/api/v1")
public class PessoaController { // definir o resource: Pessoa (api Pessoa)

	private final PessoaService service;

	@Autowired // Injeção de Dependência
	public PessoaController(PessoaService service) {
		this.service = service;
	}

	@PostMapping(path = "/pessoas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> nova(
			@RequestBody PessoaRequest body) {

		try {
			service.salvar(body);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException ex) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("erro", ex.getMessage()));
		}
	}



	@GetMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pessoa>> listar() {

		List<Pessoa> pessoas = service.listar();

		return ResponseEntity.ok(pessoas);
	}



	@GetMapping(path = "/pessoas/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(@PathVariable String cpf) {
		// validação
		if ( ! cpf.matches("^\\d{11}$")) {
			return ResponseEntity.badRequest().body(Map.of("erro", "Formato CPF inválido"));
		}

		Optional<Pessoa> pessoa = service.find(cpf);

		// retorno
		if (pessoa.isEmpty()) return ResponseEntity.notFound().build();

		return ResponseEntity.ok(pessoa.get());
	}
}
