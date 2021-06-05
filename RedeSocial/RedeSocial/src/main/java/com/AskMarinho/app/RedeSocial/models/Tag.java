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
	@JsonIgnoreProperties({ "userPost", "comments", "idPost", "tagRelation" })
	private List<Post> posts = new ArrayList<>();

	public long getIdTag() {
		return idTag;
	}

	public String getTagName() {
		return tagName;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setIdTag(long idTag) {
		this.idTag = idTag;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
