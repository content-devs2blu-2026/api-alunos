package com.bublitz.api_alunos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bublitz.api_alunos.dto.AlunoRequest;
import com.bublitz.api_alunos.dto.AlunoResponse;
import com.bublitz.api_alunos.exception.AlunoNaoEncontradoException;
import com.bublitz.api_alunos.exception.EmailJaCadastradoException;
import com.bublitz.api_alunos.model.Aluno;


@Service
public class AlunoService {
	
	private final List <Aluno> alunos;
	private int id = 1;
	
	// CONSTRUTOR
	public AlunoService() {
		alunos = new ArrayList<>();		
	}
	
	// RETORNAR LISTA
	public List<AlunoResponse> listarAlunos() { 
		
		List <Aluno> alunosModel = alunos;
		List<AlunoResponse> alunosResponse = new ArrayList<>();
		
		for (Aluno a : alunosModel) {
			alunosResponse.add(new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getDataNascimento(), a.getMedia()));
		}
		
		return alunosResponse;
	}
	
	// RETORNAR POR ID
	public AlunoResponse obterAlunoPorId(int id) {
		
		// return alunos.stream().filter(aluno -> aluno.getId() == id).findFirst().orElse(null);
		
		for (Aluno a : alunos) {
			
			if (a.getId() == id) {
				return new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getDataNascimento(), a.getMedia());
				
			} 	
		}
		
		throw new AlunoNaoEncontradoException("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
	}
	
	// CADASTRAR
	public AlunoResponse cadastrarAluno(AlunoRequest request) {
		
		for (Aluno a : alunos) {
			
			if (a.getEmail().equalsIgnoreCase(request.getEmail())) {
				
				throw new EmailJaCadastradoException("E-MAIL JÁ CADASTRADO: Já existe um aluno com este e-mail.");
			}
		}
		
		alunos.add(new Aluno(id, request.getNome(), request.getEmail(), request.getSenha(), request.getDataNascimento(), request.getMedia()));
		id++;
		
		
		// RETORNO AO CRIAR
		Aluno alunoCadastrado = alunos.getLast();
		return new AlunoResponse(alunoCadastrado.getId(), alunoCadastrado.getNome(), alunoCadastrado.getEmail(), alunoCadastrado.getDataNascimento(), alunoCadastrado.getMedia());
		
		
	}
	
	// ATUALIZAR
	public AlunoResponse atualizarAluno(int id, AlunoRequest request) {
		
		for (Aluno a : alunos) {
			
			if (a.getEmail().equalsIgnoreCase(request.getEmail()) && id != a.getId()) {
				
				throw new EmailJaCadastradoException("E-MAIL JÁ CADASTRADO: Já existe um aluno com este e-mail.");
			}
		}

		for (Aluno a : alunos) {
			
			if(a.getId() == id) {
				
				a.setNome(request.getNome());
				a.setEmail(request.getEmail());
				a.setDataNascimento(request.getDataNascimento());
				a.setMedia(request.getMedia());
				
				
				
				// 
				
				return new AlunoResponse(a.getId(), a.getNome(), a.getEmail(), a.getDataNascimento(), a.getMedia());
			}
		}
		
		throw new AlunoNaoEncontradoException("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
		
	}
	
	// DELETAR
	public void deletarAluno(int id) {
		boolean removed = alunos.removeIf(aluno -> aluno.getId() == id);
		
		if (!removed) {
	        throw new AlunoNaoEncontradoException("ALUNO NÃO ENCONTRADO: Não existe um aluno com este id.");
	    }

	}
}
