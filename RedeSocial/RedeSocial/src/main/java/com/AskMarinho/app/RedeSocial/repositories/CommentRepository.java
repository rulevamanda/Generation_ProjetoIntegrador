package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findAllByTextContainingIgnoreCase(String texto);
}
