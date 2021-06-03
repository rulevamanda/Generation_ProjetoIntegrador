package com.AskMarinho.app.RedeSocial.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table (name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@NotNull (message = "Precisa ter um nome!")
	@Size (min = 3, max = 50, message = "O nome não pode ser nulo")
	private String nome;
	
	@NotNull (message = "Aqui precisa ter um usuário válido!")
	@Size (min = 5, max = 15, message = "User entre 3 e 15")
	private String nomeUsuario;
	
	@NotNull
	@Size (min = 12, max = 25, message = "Email precisar ter entre 12 e 25 caracteres!")
	private String email;
	
	@NotNull (message = "A senha não pode ser nula, please!")
	@Size (min = 8, max = 255)
	private String senha;
	
	@NotNull
	private Date nascimento;
	
	@NotNull
	private String genero;
	
	@NotNull
	@Column (name = "telefone", length = 20)
	private Long telefone;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
}