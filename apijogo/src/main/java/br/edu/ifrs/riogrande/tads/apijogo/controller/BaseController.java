package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.MetaResponseWrapper;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.metadata.ListMeta;

public abstract class BaseController {

	protected <To, Td> ResponseEntity<MetaResponseWrapper<List<Td>, ListMeta>> listResponse(List<To> list, Function<? super To,? extends Td> mappingFunction) {

		var total = list.size();

		return ResponseEntity.ok(MetaResponseWrapper.wrap(
			list.stream()
				.map(mappingFunction)
				.collect(Collectors.toList()),
			new ListMeta(total, 0, total)));
	}

	protected ResponseEntity<Object> created(String path, Map<String, UUID> map) {

		return ResponseEntity.created(
			ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(path)
				.buildAndExpand(map)
				.toUri()
			).build();
	}
}
