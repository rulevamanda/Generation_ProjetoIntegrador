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
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idReport;

	@ManyToMany()
	@JoinTable(name = "reportsAndUsers", joinColumns = @JoinColumn(name = "fk_report"), inverseJoinColumns = @JoinColumn(name = "fk_user"))
	@JsonIgnoreProperties({ "upvotes", "reports", "password", "birth", "gender", "comments", "posts", "favorites" })
	private Set<Usuario> userReport = new HashSet<>();

	@OneToOne
	@JsonIgnoreProperties({ "upvoted", "reports", "comment", "reports", "userPost", "tagRelation", "reported" })
	private Post postReport;

	@OneToOne
	@JsonIgnoreProperties({ "upvoted", "userComment", "post", "reported" })
	private Comment commentReport;

	public long getIdReport() {
		return idReport;
	}

	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}

	public Set<Usuario> getUserReport() {
		return userReport;
	}

	public void setUserReport(Set<Usuario> userReport) {
		this.userReport = userReport;
	}

	public Post getPostReport() {
		return postReport;
	}

	public void setPostReport(Post postReport) {
		this.postReport = postReport;
	}

	public Comment getCommentReport() {
		return commentReport;
	}

	public void setCommentReport(Comment commentReport) {
		this.commentReport = commentReport;
	}

	

}
