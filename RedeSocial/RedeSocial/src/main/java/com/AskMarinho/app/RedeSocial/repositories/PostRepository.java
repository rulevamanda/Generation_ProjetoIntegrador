package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
 
	public List<Post>findAllByTitleContainingIgnoreCase (String title);
	
	public Optional<Post> findByTitle (String title);

}
