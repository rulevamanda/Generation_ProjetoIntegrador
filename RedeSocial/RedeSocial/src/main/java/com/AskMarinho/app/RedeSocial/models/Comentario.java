package com.AskMarinho.app.RedeSocial.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "comentario")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idComentario;

	@NotNull
	@Size(min = 1, max = 100)
	private String texto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuarioComentario")
	@JsonIgnoreProperties({"comentarios", "postagens", "idUsuario", "nomeUsuario", "telefone", "senha", "nascimento"})
	private Usuario usuarioComentario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "postagem")
	@JsonIgnoreProperties({"comentarios","usuarioPostagem", "id_postagem"})
	private Postagem postagem;

	public long getIdComentario() {
		return idComentario;
	}

	public void setId(long id) {
		this.idComentario = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getUsuarioComentario() {
		return usuarioComentario;
	}

	public void setUsuarioComentario(Usuario usuarioComentario) {
		this.usuarioComentario = usuarioComentario;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}
	
}
