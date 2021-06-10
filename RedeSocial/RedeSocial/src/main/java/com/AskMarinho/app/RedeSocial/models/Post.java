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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
/**
 * 
 * @redactor Amanda
 *
 */
@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPost;

	@NotNull
	@Size(min = 3, max = 100)
	private String title;

	@NotNull
	@Size(min = 10, max = 500)
	private String description;

	private String urlImage;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "upvoted", "post", "idComment", "userComment", "reported" })
	private List<Comment> comment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userPost")
	@JsonIgnoreProperties({ "upvotes", "favorites","posts", "idUser", "name", "userName", "birth", "comments", "reports" })
	private User userPost;

	@ManyToMany
	@JoinTable(name = "relationTagAndPost", joinColumns = @JoinColumn(name = "fk_post"), inverseJoinColumns = @JoinColumn(name = "fk_tag"))
	@JsonIgnoreProperties({ "posts", "idTag", "userTags" })
	private Set<Tag> tagRelation = new HashSet<>();

	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "postReport", "idReport", "idUser", "commentReport", "favorites" })
	private Report reported;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"postUpvote", "commentUpvote"})
	private Upvote upvoted;

	public long getIdPost() {
		return idPost;
	}

	public void setIdPost(long idPost) {
		this.idPost = idPost;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public User getUserPost() {
		return userPost;
	}

	public void setUserPost(User userPost) {
		this.userPost = userPost;
	}

	public Set<Tag> getTagRelation() {
		return tagRelation;
	}

	public void setTagRelation(Set<Tag> tagRelation) {
		this.tagRelation = tagRelation;
	}

	public Report getReported() {
		return reported;
	}

	public void setReported(Report reported) {
		this.reported = reported;
	}

	public Upvote getUpvoted() {
		return upvoted;
	}

	public void setUpvoted(Upvote upvoted) {
		this.upvoted = upvoted;
	}

	

}