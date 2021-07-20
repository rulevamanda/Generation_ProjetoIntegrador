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
	@Size(min = 3, max = 500)
	private String description;

	private String urlImage;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "userUpvotePost", "post", "reported" })
	private List<Comment> comment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userPost")
	@JsonIgnoreProperties({ "reportComment", "reportPost", "upvoteComment", "upvotePost" ,"upvotes", "favorites", "posts", "birth", "comments", "reports" })
	private Usuario userPost;

	@ManyToMany
	@JoinTable(name = "relationTagAndPost", joinColumns = @JoinColumn(name = "fk_post"), inverseJoinColumns = @JoinColumn(name = "fk_tag"))
	@JsonIgnoreProperties({ "posts", "userTags" })
	private Set<Tag> tagRelation = new HashSet<>();
	
	@ManyToMany()
	@JoinTable(name = "upvotesPosts", joinColumns = @JoinColumn(name = "fk_post"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({ "reportComment", "reportPost", "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "upvotePost", "upvoteComment", "favorites" })
	private Set<Usuario> userUpvotePost = new HashSet<>();
	
	@ManyToMany()
	@JoinTable(name = "reportsPosts", joinColumns = @JoinColumn(name = "fk_post"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({  "reportComment", "reportPost", "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "upvotePost", "upvoteComment", "favorites" })
	private Set<Usuario> userReportPost = new HashSet<>();
	
	

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

	public Usuario getUserPost() {
		return userPost;
	}

	public void setUserPost(Usuario userPost) {
		this.userPost = userPost;
	}

	public Set<Tag> getTagRelation() {
		return tagRelation;
	}

	public void setTagRelation(Set<Tag> tagRelation) {
		this.tagRelation = tagRelation;
	}

	public Set<Usuario> getUserUpvotePost() {
		return userUpvotePost;
	}

	public void setUserUpvotePost(Set<Usuario> userUpvotePost) {
		this.userUpvotePost = userUpvotePost;
	}

	public Set<Usuario> getUserReportPost() {
		return userReportPost;
	}

	public void setUserReportPost(Set<Usuario> userReportPost) {
		this.userReportPost = userReportPost;
	}

}