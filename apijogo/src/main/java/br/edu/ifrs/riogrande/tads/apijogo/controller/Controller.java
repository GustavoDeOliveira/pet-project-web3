package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;

public abstract class Controller {
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
