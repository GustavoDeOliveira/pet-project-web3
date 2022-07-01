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
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ErroResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.MetaResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoDetalheResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ProdutoResumoResponse;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata.ListMeta;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Dados da requisição inválidos.", response = ErroResponse.class),
		@ApiResponse(code = 404, message = "Produto não encontrado.", response = ErroResponse.class),
		@ApiResponse(code = 200, message = "Produto carregado com sucesso.", response = ResponseWrapper.class)})
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(
			@PathVariable UUID id)
			throws EntidadeNaoEncontradaException {
		
		var produto = lojaService.carregar(id);

		return ResponseEntity.ok(ResponseWrapper.wrap(ProdutoDetalheResponse.from(produto)));
	}

	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Dados da requisição inválidos.", response = ErroResponse.class),
		@ApiResponse(code = 404, message = "Produto não encontrado.", response = ErroResponse.class),
		@ApiResponse(code = 422, message = "Não é possível comprar o item.", response = ErroResponse.class),
		@ApiResponse(code = 200, message = "Item comprado com sucesso.", response = ResponseWrapper.class)})
	@PatchMapping(path = "/{id}")
	public ResponseEntity<?> comprarItem(
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
