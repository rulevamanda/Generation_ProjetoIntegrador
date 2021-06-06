package com.AskMarinho.app.RedeSocial.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	public Optional<Report> findByPostReport(Post reportPost);

	public Optional<Report> findByCommentReport(Comment reportComment);
}
