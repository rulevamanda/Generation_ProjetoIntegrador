package com.AskMarinho.app.RedeSocial.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;

	@NotNull(message = "Precisa ter um nome!")
	@Size(min = 3, max = 50, message = "O nome não pode ser nulo")
	private String name;

	@NotNull(message = "Aqui precisa ter um usuário válido!")
	@Size(min = 5, max = 15, message = "User entre 3 e 15")
	private String userName;

	@NotNull
	@Size(min = 12, max = 25, message = "Email precisar ter entre 12 e 25 caracteres!")
	private String email;

	@NotNull(message = "A senha não pode ser nula, please!")
	@Size(min = 8, max = 255)
	private String password;

	@NotNull
	private Date birth;

	@NotNull
	private String gender;

	@NotNull
	@Column(name = "telephone", length = 20)
	private Long telephone;

	@OneToMany(mappedBy = "userComment", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "userComment", "idComment", "post" })
	private List<Comment> comments;

	@OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "userPost", "idPost", "comments", "tagRelation", "comment" })
	private List<Post> posts;

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

	public Date getBirth() {
		return birth;
	}

	public String getGender() {
		return gender;
	}

	public Long getTelephone() {
		return telephone;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<Post> getPosts() {
		return posts;
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

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}