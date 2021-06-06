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

@Entity
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idReport;

	@ManyToMany
	@JoinTable(name = "reportsAndUsers", joinColumns = @JoinColumn(name = "fk_report"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({ "reports", "password", "birth", "gender", "comments", "posts" })
	private Set<User> userReport = new HashSet<>();

	@OneToOne
	@JsonIgnoreProperties({ "reports", "comment", "reports", "userPost", "tagRelation", "reported" })
	private Post postReport;

	@OneToOne
	@JsonIgnoreProperties({ "userComment", "post", "reported" })
	private Comment commentReport;

	public long getIdReport() {
		return idReport;
	}

	public Set<User> getUserReport() {
		return userReport;
	}

	public Post getPostReport() {
		return postReport;
	}

	public Comment getCommentReport() {
		return commentReport;
	}

	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}

	public void setUserReport(Set<User> userReport) {
		this.userReport = userReport;
	}

	public void setPostReport(Post postReport) {
		this.postReport = postReport;
	}

	public void setCommentReport(Comment commentReport) {
		this.commentReport = commentReport;
	}

}
