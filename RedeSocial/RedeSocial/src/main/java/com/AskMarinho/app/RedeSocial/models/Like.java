package com.AskMarinho.app.RedeSocial.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "tb_like")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLike;

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(name = "likesAndUser", joinColumns = @JoinColumn(name = "fk_like"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({ "posts","userName", "password", "birth", "gender", "telephone", "comments", "reports", "likes",
			"favorites" })
	private Set<User> userLike = new HashSet<>();

	@OneToOne
	@JsonIgnoreProperties({ "liked", "comment", "userPost", "tagRelation", "reported" })
	private Post postUpvote;

	@OneToOne
	@JsonIgnoreProperties({ "liked", "userComment", "post", "reported" })
	private Comment commentUpvote;

	public long getIdLike() {
		return idLike;
	}

	public Set<User> getUserLike() {
		return userLike;
	}

	public Post getPostUpvote() {
		return postUpvote;
	}

	public Comment getCommentUpvote() {
		return commentUpvote;
	}

	public void setIdLike(long idLike) {
		this.idLike = idLike;
	}

	public void setUserLike(Set<User> userLike) {
		this.userLike = userLike;
	}

	public void setPostUpvote(Post postUpvote) {
		this.postUpvote = postUpvote;
	}

	public void setCommentUpvote(Comment commentUpvote) {
		this.commentUpvote = commentUpvote;
	}

	

}
