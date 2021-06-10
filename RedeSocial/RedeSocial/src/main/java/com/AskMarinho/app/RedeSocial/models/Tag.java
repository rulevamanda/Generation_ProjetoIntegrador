package com.AskMarinho.app.RedeSocial.models;

import java.util.ArrayList;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
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
@Table(name = "tag")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTag;

	@NotNull
	@Size(min = 3, max = 100)
	private String tagName;

	@ManyToMany(mappedBy = "tagRelation")
	@JsonIgnoreProperties({"upvoted", "userPost", "comments", "tagRelation", "comment", "reported" })
	private List<Post> posts = new ArrayList<>();

	// editando aqui --user
	@ManyToMany(mappedBy = "favorites")
	@JsonIgnoreProperties({ "upvotes", "posts", "reports", "favorites", "comments", "password", "email", "birth", "gender", "telephone" })
	private List<User> userTags;

	public long getIdTag() {
		return idTag;
	}

	public void setIdTag(long idTag) {
		this.idTag = idTag;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<User> getUserTags() {
		return userTags;
	}

	public void setUserTags(List<User> userTags) {
		this.userTags = userTags;
	}

	
}
