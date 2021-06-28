package com.AskMarinho.app.RedeSocial.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @redactor Amanda
 *
 */
@Entity
@Table(name = "tb_upvote")
public class Upvote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUpvote;

	@ManyToMany()
	@JoinTable(name = "upvotesAndUser", joinColumns = @JoinColumn(name = "fk_upvote"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({ "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "upvotes", "favorites" })
	private Set<Usuario> userUpvote = new HashSet<>();

	@OneToOne
	@JsonIgnoreProperties({ "upvoted", "comment", "userPost", "tagRelation", "reported" })
	private Post postUpvote;

	@OneToOne
	@JsonIgnoreProperties({ "upvoted", "userComment", "post", "reported" })
	private Comment commentUpvote;

	public long getIdUpvote() {
		return idUpvote;
	}

	public void setIdUpvote(long idUpvote) {
		this.idUpvote = idUpvote;
	}

	public Set<Usuario> getUserUpvote() {
		return userUpvote;
	}

	public void setUserUpvote(Set<Usuario> userUpvote) {
		this.userUpvote = userUpvote;
	}

	public Post getPostUpvote() {
		return postUpvote;
	}

	public void setPostUpvote(Post postUpvote) {
		this.postUpvote = postUpvote;
	}

	public Comment getCommentUpvote() {
		return commentUpvote;
	}

	public void setCommentUpvote(Comment commentUpvote) {
		this.commentUpvote = commentUpvote;
	}

	

}
