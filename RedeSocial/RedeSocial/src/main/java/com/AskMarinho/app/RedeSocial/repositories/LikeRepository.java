package com.AskMarinho.app.RedeSocial.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Like;
import com.AskMarinho.app.RedeSocial.models.Post;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

	public Optional<Like> findByPostUpvote(Post likePost);
	
	public Optional<Like> findByCommentUpvote(Comment likeComment);
}
