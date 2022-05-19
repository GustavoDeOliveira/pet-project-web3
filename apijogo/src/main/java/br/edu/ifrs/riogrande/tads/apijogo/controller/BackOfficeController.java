package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.EfeitoItem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/backoffice")
public class BackOfficeController extends Controller {

	private final ProdutoRepository produtoRepository;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produto>> seed() {

		List<Produto> produtos = new ArrayList<Produto>() { {
			add(new Produto(UUID.randomUUID(), "Cota de malha", 100.0,
					new EfeitoItem(0, 20, 0, 0), null, 10));
			add(new Produto(UUID.randomUUID(), "Espada de aço", 80.0,
					new EfeitoItem(20, 0, 0, 0), null, 10));
			add(new Produto(UUID.randomUUID(), "Poção de cura", 10.0,
					new EfeitoItem(0, 0, 0, 50), 1, 20));
			add(new Produto(UUID.randomUUID(), "Benção de vida", 10.0,
					new EfeitoItem(0, 0, 20, 0), 10, 20));
		} };

		produtos.forEach((p) -> produtoRepository.save(p));

		return ResponseEntity.ok(produtoRepository.findAll());
	}
}
