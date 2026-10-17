package com.bublitz.api_alunos.exception;

import java.time.Instant;

public record ErroResponse (
			
		// STATUS | MENSAGEM | TIMESTAMP
		
		int status,
		String mensagem,
		Instant timestamp
		
	) {}
