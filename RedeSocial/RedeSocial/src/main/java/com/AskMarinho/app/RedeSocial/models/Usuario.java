package com.AskMarinho.app.RedeSocial.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @redactor Amanda
 *
 */
@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;

	@NotNull(message = "Insira um nome.")
	@Size(min = 3, max = 50, message = "O nome não pode ser nulo.")
	private String name;

	@NotNull(message = "Insira um nome de usuário.")
	@Size(min = 3, max = 100, message = "Nome de usuário entre 3 e 15 caracteres.")
	private String userName;

	@NotNull(message = "Insira um endereço de email.")
	@Size(min = 3, max = 100, message = "Endereço de email entre 12 e 25 caracteres.")
	private String email;

	@NotNull(message = "Insira uma senha.")
	@Size(min = 3, max = 255, message = "Senha entre 8 e 25 caracteres.")
	private String password;

	@NotNull(message = "Insira uma data de nascimento.")
	private String birth;

	@NotNull(message = "Insira o gênero.")
	@Size(min = 2, max = 50)
	private String gender;
	
	private String urlImage;
	
	private String tipo;
	
	private String description;

	@OneToMany(mappedBy = "userComment", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "upvoted", "userComment", "reported" })
	private List<Comment> comments;

	@OneToMany(mappedBy = "userPost", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "upvoted", "userPost", "reported" })
	private List<Post> posts;
	
	@ManyToMany
	@JoinTable(name = "userAndFavorites", joinColumns = @JoinColumn(name = "fk_user"), inverseJoinColumns = @JoinColumn(name = "fk_tag"))
	@JsonIgnoreProperties({ "posts", "userTags" })
	private Set<Tag> favorites = new HashSet<>();
	
	@ManyToMany(mappedBy = "userUpvotePost")
	@JsonIgnoreProperties("userUpvotePost")
	private List<Post> upvotePost = new ArrayList<>();
	
	@ManyToMany(mappedBy = "userUpvoteComment")
	@JsonIgnoreProperties("userUpvoteComment")
	private List<Comment> upvoteComment = new ArrayList<>();
	
	@ManyToMany(mappedBy = "userReportPost")
	@JsonIgnoreProperties("userReportPost")
	private List<Post> reportPost = new ArrayList<>();
	
	@ManyToMany(mappedBy = "userReportComment")
	@JsonIgnoreProperties("userReportComment")
	private List<Comment> reportComment = new ArrayList<>();

	public Long getIdUser() {
		return idUser;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getBirth() {
		return birth;
	}

	public String getGender() {
		return gender;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescription() {
		return description;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public Set<Tag> getFavorites() {
		return favorites;
	}

	public List<Post> getUpvotePost() {
		return upvotePost;
	}

	public List<Comment> getUpvoteComment() {
		return upvoteComment;
	}

	public List<Post> getReportPost() {
		return reportPost;
	}

	public List<Comment> getReportComment() {
		return reportComment;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void setFavorites(Set<Tag> favorites) {
		this.favorites = favorites;
	}

	public void setUpvotePost(List<Post> upvotePost) {
		this.upvotePost = upvotePost;
	}

	public void setUpvoteComment(List<Comment> upvoteComment) {
		this.upvoteComment = upvoteComment;
	}

	public void setReportPost(List<Post> reportPost) {
		this.reportPost = reportPost;
	}

	public void setReportComment(List<Comment> reportComment) {
		this.reportComment = reportComment;
	}
	
}