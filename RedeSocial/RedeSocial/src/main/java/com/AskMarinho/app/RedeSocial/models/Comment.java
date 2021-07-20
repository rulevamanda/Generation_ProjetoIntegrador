package com.AskMarinho.app.RedeSocial.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idComment;

	@NotNull
	@Size(min = 1, max = 155)
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new java.sql.Date(System.currentTimeMillis());

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userComment")
	@JsonIgnoreProperties({ "reportComment", "reportPost", "upvoteComment", "upvotePost", "upvotes","comments", "posts", "telephone", "password", "birth", "reports",
			"favorites" })
	private Usuario userComment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post")
	@JsonIgnoreProperties({"upvoted", "comment", "reported" })
	private Post post;
	
	@ManyToMany()
	@JoinTable(name = "upvotesComments", joinColumns = @JoinColumn(name = "fk_comment"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({  "reportComment", "reportPost", "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "upvotePost", "upvoteComment", "favorites" })
	private Set<Usuario> userUpvoteComment = new HashSet<>();
	
	@ManyToMany()
	@JoinTable(name = "reportsComments", joinColumns = @JoinColumn(name = "fk_comment"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({  "reportComment", "reportPost", "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "upvotePost", "upvoteComment", "favorites" })
	private Set<Usuario> userReportComment = new HashSet<>();


	public long getIdComment() {
		return idComment;
	}

	public void setIdComment(long idComment) {
		this.idComment = idComment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Usuario getUserComment() {
		return userComment;
	}

	public void setUserComment(Usuario userComment) {
		this.userComment = userComment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Set<Usuario> getUserUpvoteComment() {
		return userUpvoteComment;
	}

	public void setUserUpvoteComment(Set<Usuario> userUpvoteComment) {
		this.userUpvoteComment = userUpvoteComment;
	}

	public Set<Usuario> getUserReportComment() {
		return userReportComment;
	}

	public void setUserReportComment(Set<Usuario> userReportComment) {
		this.userReportComment = userReportComment;
	}
	
	

}
