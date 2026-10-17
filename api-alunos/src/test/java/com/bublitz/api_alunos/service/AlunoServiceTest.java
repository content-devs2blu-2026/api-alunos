package com.bublitz.api_alunos.service;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bublitz.api_alunos.dto.AlunoRequest;
import com.bublitz.api_alunos.dto.AlunoResponse;
import com.bublitz.api_alunos.exception.AlunoNaoEncontradoException;
import com.bublitz.api_alunos.exception.EmailJaCadastradoException;

public class AlunoServiceTest {
	
	private AlunoService service; 
	
	@BeforeEach
	void setup() {
		service = new AlunoService();
	}
	
	// LISTAR
	
	@Test
	void deveRetornarListaVaziaQuandoNaoHouverAlunos() {
		// Act
		List<AlunoResponse> resultado = service.listarAlunos();
		
		// Assert 
	    Assertions.assertThat(resultado).isNotNull().isEmpty();
	}
	
	@Test
	void deveRetornarListaComAlunosQuandoHouverAlunos() {
		
		// Arrange
		
		AlunoRequest aluno1 = new AlunoRequest (
				"Henrique", 
				"henrique@email.com", 
				"senha", LocalDate.of(2001, 10, 11), 8);
		
		AlunoRequest aluno2 = new AlunoRequest (
				"Maria", 
				"maria@email.com", 
				"senha", LocalDate.of(2011, 11, 18), 7);
		
		service.cadastrarAluno(aluno1);
		service.cadastrarAluno(aluno2);

		// Act
		List<AlunoResponse> resultado = service.listarAlunos();
		
		// Assert
		Assertions.assertThat(resultado).isNotNull().hasSize(2);
		
	}
	
	// RETORNAR POR ID
	
	@Test
	void deveRetornarAlunoCorrespondenteSeAlunoComIdExistir() {
		// Arrange
		AlunoRequest aluno = new AlunoRequest (
				"Maria", 
				"maria@email.com", 
				"senha", LocalDate.of(2011, 11, 18), 7);
		
		AlunoResponse cadastrado = service.cadastrarAluno(aluno);
		
		int idExistente = cadastrado.getId();
		
		// Act
		
		AlunoResponse resultado = service.obterAlunoPorId(idExistente);
		
		// Assert
		
		Assertions.assertThat(resultado).isNotNull();
		Assertions.assertThat(resultado.getId()).isEqualTo(idExistente);
		Assertions.assertThat(resultado.getNome()).isEqualTo("Maria");
		Assertions.assertThat(resultado.getEmail()).isEqualTo("maria@email.com");
	}
	
	@Test
	void deveLancarExcecaoQuandoIdNaoExistir() {
		// Assert
		
		int idInexistente = 100;
		
		// Act & Assert
		Assertions.assertThatThrownBy(() -> service.obterAlunoPorId(idInexistente))
			.isInstanceOf(AlunoNaoEncontradoException.class)
			.hasMessage("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
	}
	
	// CADASTRAR
	
	@Test
	void deveCadastrarAlunoQuandoDadosForemValidos() {
		// service = new AlunoService(); --- Bah, tem o setup agora
		
		AlunoRequest request = new AlunoRequest (
				"Henrique", 
				"henrique@email.com", 
				"senha", LocalDate.of(2001, 10, 11), 8);
		
		AlunoResponse response = service.cadastrarAluno(request);
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getId()).isGreaterThan(0);
		
	}
	
	@Test
	void deveLancarExcecaoQuandoEmailForDuplicado() {
		// service = new AlunoService(); --- Bah, tem o setup agora
		
		AlunoRequest alunoEmail = new AlunoRequest (
				"Henrique", 
				"henrique@email.com", 
				"senha", LocalDate.of(2001, 10, 11), 8);
		
		AlunoRequest alunoComMesmoEmail = new AlunoRequest (
				"Henrique", 
				"henrique@email.com", 
				"senha", LocalDate.of(2001, 10, 11), 8);

		
		service.cadastrarAluno(alunoEmail);
		
		Assertions.assertThatThrownBy(() -> service.cadastrarAluno(alunoComMesmoEmail))
			.isInstanceOf(EmailJaCadastradoException.class)
			.hasMessage("E-MAIL JÁ CADASTRADO: Já existe um aluno com este e-mail.");
		
	}
	
	// ATUALIZAR
	
	@Test
	void deveAtualizarAlunoQuandoDadosForemValidos() {
		
		// Arrange
		AlunoRequest aluno = new AlunoRequest (
				"Maria", 
				"maria@email.com", 
				"senha", 
				LocalDate.of(2011, 11, 18), 
				7
		);
		
		AlunoResponse cadastrado = service.cadastrarAluno(aluno);
		
		// versao atualizada
		
		AlunoRequest alunoModificado = new AlunoRequest (
				"Maria Eduarda", 
				"mariaEduarda@email.com", 
				"senha", 
				LocalDate.of(2011, 11, 18), 
				8
		);
		
		// Act
		
		AlunoResponse atualizado = service.atualizarAluno(cadastrado.getId(), alunoModificado);
				
		// Assert
		Assertions.assertThat(atualizado).isNotNull();
		
		Assertions.assertThat(atualizado.getId()).isEqualTo(cadastrado.getId());
		Assertions.assertThat(atualizado.getNome()).isEqualTo("Maria Eduarda");
		Assertions.assertThat(atualizado.getEmail()).isEqualTo("mariaEduarda@email.com");
		Assertions.assertThat(atualizado.getMedia()).isEqualTo(8);
		
	}
	
	@Test
	void deveLancarExcecaoQuandoEmailAtualizadoForDuplicado() {
		
		// Arrange 
		AlunoRequest alunoEmail = new AlunoRequest (
				"Henrique Guilherme", 
				"henriqueGui@email.com", // email antigo
				"senha", 
				LocalDate.of(2001, 10, 11), 
				8
		);
		
		AlunoRequest alunoComMesmoEmail = new AlunoRequest (
				"Henrique Mota", 
				"henrique@email.com", 
				"senha",
				LocalDate.of(2001, 10, 11), 
				8
		);
		
		service.cadastrarAluno(alunoComMesmoEmail);
		AlunoResponse resposta = service.cadastrarAluno(alunoEmail);
		
		// Tenta atualizar 
		AlunoRequest atualizacaoInvalida = new AlunoRequest (
				"Henrique Guilherme", 
				"henrique@email.com", // email novo (invalido)
				"senha", 
				LocalDate.of(2001, 10, 11), 
				8
		);
		
		
		// Act & Assert
		Assertions.assertThatThrownBy(()-> service.atualizarAluno(resposta.getId(), atualizacaoInvalida))
			.isInstanceOf(EmailJaCadastradoException.class)
			.hasMessageContainingAll("E-MAIL JÁ CADASTRADO: Já existe um aluno com este e-mail.");
	}
	
	@Test
	void deveLancarExcecaoQuandoAlunoNaoExistir() {
		
		// Arrange
		
		AlunoRequest alunoModificado = new AlunoRequest (
				"Maria Eduarda", 
				"mariaEduarda@email.com", 
				"senha", 
				LocalDate.of(2011, 11, 18), 
				8
		);
				
		// Act & Assert
		Assertions.assertThatThrownBy(() -> service.atualizarAluno(99, alunoModificado))
			.isInstanceOf(AlunoNaoEncontradoException.class)
			.hasMessage("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
	}
	
	
	// DELETAR
	
	@Test
	void deveDeletarAlunoEfetivamente() {
		// Arrange
		
		AlunoRequest aluno = new AlunoRequest (
				"Maria Eduarda", 
				"mariaEduarda@email.com", 
				"senha", 
				LocalDate.of(2011, 11, 18), 
				8
		);
		
		AlunoResponse alunoCadastrado = service.cadastrarAluno(aluno);
		
		
		// Act
		
		service.deletarAluno(alunoCadastrado.getId());
		
		// Assert
		
		List<AlunoResponse> listaAtualizada = service.listarAlunos();
		Assertions.assertThat(listaAtualizada).isEmpty();
	}
	
	@Test
	void deveLancarExcecaoSeAlunoNaoExistir() {
		
		// Act & Assert
		Assertions.assertThatThrownBy(() -> service.deletarAluno(99))
			.isInstanceOf(AlunoNaoEncontradoException.class)
			.hasMessage("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
	}
	
}
