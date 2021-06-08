package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.repositories.PostRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "", allowedHeaders = "")

public class PostController {

	private @Autowired PostRepository repositoryP;

	/**
	 * MÃ©todo para buscar todas as postagens
	 * 
	 * @return retorna todas as postagens cadastradas
	 * @author Antonio
	 * @author Bueno
	 * @translator Amanda
	 */
	@GetMapping("/todas")
	public ResponseEntity<List<Post>> allPosts() {
		List<Post> postList = repositoryP.findAll();
		return ResponseEntity.status(200).body(postList);
	}

	/**
	 * MÃ©todo para buscar postagens pelo id
	 * 
	 * @param id - id da postagem
	 * @return retorna a postagem buscada pelo id com um status 200 ou retorna um
	 *         status 404 com uma build vazia
	 * @author Antonio
	 * @author Bueno
	 * @translator Amanda
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Post> idPost(@PathVariable Long id) {
		return repositoryP.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * MÃ©todo para buscar postagens pelo tÃ­tulo
	 * 
	 * @param titulo - podendo nÃ£o ser o tÃ­tulo completo
	 * @return retorna postagens que possuem o tÃ­tulo pesquisado
	 * @author Antonio
	 * @author Bueno
	 * @translator Amanda
	 */
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Post>> titlePost(@PathVariable String title) {
		return ResponseEntity.status(200).body(repositoryP.findAllByTitleContainingIgnoreCase(title));

	}

}