package com.AskMarinho.app.RedeSocial.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "postagem")
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_postagem;

	@NotNull
	@Size(min = 3, max = 100)
	private String titulo;

	@NotNull
	@Size(min = 10, max = 500)
	private String descricao;

	private String url_imagem;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new java.sql.Date(System.currentTimeMillis());


	@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"postagem", "idComentario", "usuarioComentario"})
	private List<Comentario> comentarios;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuarioPostagem")
	@JsonIgnoreProperties({"postagens", "idUsuario", "nomeUsuario", "nomeUsuario", "nascimento", "comentarios"})
	private Usuario usuarioPostagem;

	@ManyToMany
	@JoinTable(
			name = "relacaoTP",
			joinColumns = @JoinColumn(name = "fk_postagem"),
			inverseJoinColumns = @JoinColumn(name = "fk_tema"))
	//@JsonIgnoreProperties({"postagens","id"})
	private Set<Tema> temasRelacionados = new HashSet<>();

	public long getId_postagem() {
		return id_postagem;
	}

	public void setId_postagem(long id_postagem) {
		this.id_postagem = id_postagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl_imagem() {
		return url_imagem;
	}

	public void setUrl_imagem(String url_imagem) {
		this.url_imagem = url_imagem;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Usuario getUsuarioPostagem() {
		return usuarioPostagem;
	}

	public void setUsuarioPostagem(Usuario usuarioPostagem) {
		this.usuarioPostagem = usuarioPostagem;
	}

	public Set<Tema> getTemasRelacionados() {
		return temasRelacionados;
	}

	public void setTemasRelacionados(Set<Tema> temasRelacionados) {
		this.temasRelacionados = temasRelacionados;
	}
	
	
}
