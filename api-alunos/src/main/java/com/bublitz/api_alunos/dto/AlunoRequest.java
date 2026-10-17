package com.bublitz.api_alunos.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// REFATORAR PARA RECORD

public class AlunoRequest {
	
	// PRECISA DE @VALID NO CONTROLLER
	
	@NotBlank (message = "Nome não pode ser vazio")
	@Size(max = 50, message = "Nome não pode ultrapassar 50 dígitos")
	private String nome;
	
	@NotBlank(message = "Email não pode ser vazio")
	@Email(message = "Email inválido")
	@Size(max = 50, min = 5, message = "Email não pode ultrapassar 50 dígitos e ter menos que 5 dígitos")
	private String email;
	
	@NotBlank(message = "Senha não pode ser vazia")
	@Size(max = 5, message = "Senha não pode ultrapassar 5 dígitos")
	private String senha;
	
	@NotNull(message = "Data não pode ser vazia")
	@PastOrPresent(message = "Data deve ser anterior ou igual a data atual")
	private LocalDate dataNascimento;
	
	@Max(value = 10, message = "Média não pode ultrapasar o valor 10")
	@PositiveOrZero(message = "Média deve ser positiva e maior/igual a zero")
	private double media;
	
	public AlunoRequest(String nome, String email, String senha, LocalDate dataNascimento, double media) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.media = media;
	}
	
	// Getters & Setters

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}
	
	
	
	
}
