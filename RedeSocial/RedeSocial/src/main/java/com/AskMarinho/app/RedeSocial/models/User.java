package com.AskMarinho.app.RedeSocial.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	@JsonIgnoreProperties({ "liked", "userComment", "idComment", "post", "reported" })
	private List<Comment> comments;

	@OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "liked", "userPost", "idPost", "comments", "tagRelation", "comment", "reported" })
	private List<Post> posts;

	@ManyToMany(mappedBy = "userReport", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "postReport", "userReport", "idReport" })
	private List<Report> reports = new ArrayList<>();
	
	
	@ManyToMany(mappedBy = "userLike", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("userLike")
	private List<Like> likes = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "userAndFavorites", joinColumns = @JoinColumn(name = "fk_user"), inverseJoinColumns = @JoinColumn(name = "fk_tag"))
	@JsonIgnoreProperties({ "posts", "idTag", "userTags" })
	private Set<Tag> favorites = new HashSet<>();

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getTelephone() {
		return telephone;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Set<Tag> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Tag> favorites) {
		this.favorites = favorites;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
}