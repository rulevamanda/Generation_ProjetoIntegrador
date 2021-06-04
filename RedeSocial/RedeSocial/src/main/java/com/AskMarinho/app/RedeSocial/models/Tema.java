package com.AskMarinho.app.RedeSocial.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "tb_tema")
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	@ManyToMany(mappedBy = "temasRelacionados")
	@JsonIgnoreProperties({"usuarioPostagem", "comentarios", "id_postagem", "temasRelacionados"})
	private List<Postagem> postagens = new ArrayList<>();

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}
	
}

