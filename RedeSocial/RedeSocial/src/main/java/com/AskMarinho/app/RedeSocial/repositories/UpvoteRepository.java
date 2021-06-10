package com.AskMarinho.app.RedeSocial.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Upvote;
import com.AskMarinho.app.RedeSocial.models.Post;

/**
 * 
 * @redactor Amanda
 *
 */
@Repository
public interface UpvoteRepository extends JpaRepository<Upvote, Long>{

	public Optional<Upvote> findByPostUpvote(Post upvotePost);
	
	public Optional<Upvote> findByCommentUpvote(Comment upvoteComment);
}
