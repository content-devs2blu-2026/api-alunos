package com.bublitz.api_alunos.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AlunoNaoEncontradoException.class)
	ResponseEntity <ErroResponse> handleAlunoNaoEncontradoException(AlunoNaoEncontradoException ex) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(404, ex.getMessage(), Instant.now()));
	}
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	ResponseEntity <ErroResponse> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResponse(409, ex.getMessage(), Instant.now()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity <ErroAtributoResponse> handleValidacaoRequest (MethodArgumentNotValidException ex) {
		
		List <ErroAtributo> errosAtributo = 
				ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(erro -> new ErroAtributo(erro.getField(), erro.getDefaultMessage()))
				.toList();
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ErroAtributoResponse(400, "Erro de Validação", Instant.now(), errosAtributo));
	}
	
	
	
}
