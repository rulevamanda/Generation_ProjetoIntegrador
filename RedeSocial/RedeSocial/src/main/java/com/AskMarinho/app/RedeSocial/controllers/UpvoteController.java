package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.AskMarinho.app.RedeSocial.models.Upvote;
import com.AskMarinho.app.RedeSocial.repositories.UpvoteRepository;

/**
 * @translator Amanda
 */

@RestController
@RequestMapping("/upvotes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UpvoteController {
	
	private @Autowired UpvoteRepository repositoryL;

	/**
	 * Rota para retornar todos os likes
	 * @author Antonio
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Upvote>> getAll() {
		return ResponseEntity.status(200).body(repositoryL.findAll());
	}

	/**
	 * Rota para retornar um like pelo id
	 * @param idUpvote
	 * @author Antonio
	 * @return
	 */
	@GetMapping("/id/{idUpvote}")
	public ResponseEntity<Upvote> getById(@PathVariable(value = "idUpvote") Long idUpvote) {
		Optional<Upvote> existingUpvote = repositoryL.findById(idUpvote);

		if (existingUpvote.isPresent()) {
			return ResponseEntity.status(200).body(existingUpvote.get());
		}
		return ResponseEntity.status(404).build();
	}
}
