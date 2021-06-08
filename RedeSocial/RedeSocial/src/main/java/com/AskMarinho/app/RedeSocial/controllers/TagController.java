package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.repositories.TagRepository;

/**
 * 
 * @translator Amanda
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TagController {

	@Autowired
	private TagRepository repositoryT;

	@GetMapping("/todos")
	public ResponseEntity<List<Tag>> getAll() {
		return ResponseEntity.status(200).body(repositoryT.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Tag> getById(@PathVariable long id) {
		return repositoryT.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tag>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(repositoryT.findAllByTagNameContainingIgnoreCase(name));
	}

}
