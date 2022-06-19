package br.edu.ifrs.riogrande.tads.apijogo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.EntidadeNaoEncontradaException;
import br.edu.ifrs.riogrande.tads.apijogo.app.exceptions.LojaSemEstoqueException;
import br.edu.ifrs.riogrande.tads.apijogo.controller.dto.ErroResponse;

@ControllerAdvice
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<ErroResponse> entidadeNaoEncontradaExceptionHandler(EntidadeNaoEncontradaException ex) {
		return serviceExceptionHandler(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErroResponse> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		return serviceExceptionHandler(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LojaSemEstoqueException.class)
	protected ResponseEntity<ErroResponse> lojaSemEstoqueExceptionHandler(LojaSemEstoqueException ex) {
		return serviceExceptionHandler(ex, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<ErroResponse> constraintViolationExceptionHandler(ConstraintViolationException ex) {

		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		List<String> erros = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		.body(new ErroResponse(erros));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> erros = ex.getFieldErrors().stream()
				.map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErroResponse(erros));
	}

	private ResponseEntity<ErroResponse> serviceExceptionHandler(Exception ex, HttpStatus status) {
		return ResponseEntity.status(status)
		.body(new ErroResponse(Collections.singletonList(ex.getMessage())));
	}
}
