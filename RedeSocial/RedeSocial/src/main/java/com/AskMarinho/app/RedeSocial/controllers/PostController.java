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
	@GetMapping("/titulo/{title}")
	public ResponseEntity<List<Post>> titlePost(@PathVariable String title) {
		return ResponseEntity.status(200).body(repositoryP.findAllByTitleContainingIgnoreCase(title));

	}

	/**
	 * Método retorna o número de likes no post
	 * 
	 * @param idPost
	 * @return retorna o número de likes
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/curtidas/{idPost}")
	public ResponseEntity<String> upvotesPost(@PathVariable(value = "idPost") Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getUpvoted() != null) {
				return ResponseEntity.status(202)
						.body("Número de likes: " + existingPost.get().getUpvoted().getUserUpvote().size());
			}
			return ResponseEntity.status(202).body("Número de likes: 0");

		}
		return ResponseEntity.status(404).build();
	}
	
	
	/**
	 * Método pega o número de denúncias em um post
	 * @param idPost
	 * @return retorna número de denúncias
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/denuncias/{idPost}")
	public ResponseEntity<String> reportsPost(@PathVariable(value = "idPost") Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getReported() != null) {
				return ResponseEntity.status(202)
						.body("Número de denúncias: " + existingPost.get().getReported().getUserReport().size());
			}
			return ResponseEntity.status(202).body("Número de denúncias: 0");

		}
		return ResponseEntity.status(404).build();
	}

}