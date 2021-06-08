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
import com.AskMarinho.app.RedeSocial.models.Like;
import com.AskMarinho.app.RedeSocial.repositories.LikeRepository;

@RestController
@RequestMapping("/curtidas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LikeController {
	
	private @Autowired LikeRepository repositoryL;

	@GetMapping("/todos")
	public ResponseEntity<List<Like>> getAll() {
		return ResponseEntity.status(200).body(repositoryL.findAll());
	}

	@GetMapping("/id/{idLike}")
	public ResponseEntity<Like> getById(@PathVariable(value = "idLike") Long idLike) {
		Optional<Like> existingLike = repositoryL.findById(idLike);

		if (existingLike.isPresent()) {
			return ResponseEntity.status(200).body(existingLike.get());
		}
		return ResponseEntity.status(404).build();
	}
}
