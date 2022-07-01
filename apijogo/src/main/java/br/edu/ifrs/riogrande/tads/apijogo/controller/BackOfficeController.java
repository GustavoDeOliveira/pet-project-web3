package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.riogrande.tads.apijogo.app.model.EfeitoItem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Personagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.Produto;
import br.edu.ifrs.riogrande.tads.apijogo.app.model.enums.EnumClassePersonagem;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.PersonagemRepository;
import br.edu.ifrs.riogrande.tads.apijogo.app.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/backoffice")
public class BackOfficeController {

	private final ProdutoRepository produtoRepository;
	private final PersonagemRepository personagemRepository;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Produto>> seed() {

		if (produtoRepository.count() == 0) {

			List<Produto> produtos = new ArrayList<Produto>() { {
				add(new Produto("Cota de malha", 100.0,
						new EfeitoItem(0, 20, 0, 0), 0, 10));
				add(new Produto("Espada de aço", 80.0,
						new EfeitoItem(20, 0, 0, 0), null, 10));
				add(new Produto("Poção de cura", 10.0,
						new EfeitoItem(0, 0, 0, 50), 1, 20));
				add(new Produto("Benção de vida", 10.0,
						new EfeitoItem(0, 0, 20, 0), 10, 20));
				add(new Produto("Benção poderosa", 10.0,
						new EfeitoItem(200, 200, 200, 20), 10, 1));
			} };

			produtos.forEach((p) -> produtoRepository.save(p));
		}

		if (personagemRepository.count() == 0) {

			List<Personagem> personagens = new ArrayList<Personagem>() { {
				add(new Personagem("Glauber", EnumClassePersonagem.Guerreiro));
				add(new Personagem("Blauber", EnumClassePersonagem.Ladino));
				add(new Personagem("Clauber", EnumClassePersonagem.Mago));
			} };

			personagens.forEach((p) -> personagemRepository.save(p));
		}

		return ResponseEntity.noContent().build();
	}
}
