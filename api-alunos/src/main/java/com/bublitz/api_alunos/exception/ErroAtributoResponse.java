package com.bublitz.api_alunos.exception;

import java.time.Instant;
import java.util.List;

public record ErroAtributoResponse (
		
		// STATUS | MENSAGEM | TIMESTAMP
		
		int status,
		String mensagem,
		Instant timestamp,
		List<ErroAtributo> errosAtributos
		
	) {}
