package com.AskMarinho.app.RedeSocial.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	@Size(min = 1, max = 100)
	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userComment")
	@JsonIgnoreProperties({ "upvotes","comments", "posts", "idUser", "userName", "telephone", "password", "birth", "reports",
			"favorites" })
	private User userComment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post")
	@JsonIgnoreProperties({"upvoted", "comment", "userPost", "idPost", "tagRelation", "reported" })
	private Post post;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "postReport", "idReport", "idUser", "commentReport"})
	private Report reported;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"postUpvote", "commentUpvote"})
	private Upvote upvoted;

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

	public User getUserComment() {
		return userComment;
	}

	public void setUserComment(User userComment) {
		this.userComment = userComment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
