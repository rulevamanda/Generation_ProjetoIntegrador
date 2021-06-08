package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.repositories.CommentRepository;

@RestController
@RequestMapping("/comentarios")
public class CommentController {

	@Autowired
	private CommentRepository repositoryC;

	/**
	 * Buscar todos os comentários
	 * 
	 * @return lista com todos os comentários
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<Comment>> todosComentarios() {
		List<Comment> listaDeComentario = repositoryC.findAll();
		return ResponseEntity.status(200).body(listaDeComentario);
	}

	/**
	 * Buscar um comentário especifico pelo id
	 * 
	 * @param id
	 * @returno comentário referente ao id pesquisado ou um status notfound
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Comment> idComentario(@PathVariable Long id) {
		return repositoryC.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Método retorna o número de likes em um comentário
	 * 
	 * @param idComment
	 * @return retorna o número de likes
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/curtidas/{idComment}")
	public ResponseEntity<String> upvotesComment(@PathVariable(value = "idComment") Long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);
		if (existingComment.isPresent()) {
			if (existingComment.get().getLiked() != null) {
				return ResponseEntity.status(202)
						.body("Número de likes: " + existingComment.get().getLiked().getUserLike().size());
			}
			return ResponseEntity.status(202).body("Número de likes: 0");

		}
		return ResponseEntity.status(404).build();
	}

	/**
	 * Método pega o número de denúncias em comentário
	 * 
	 * @param idComment
	 * @return retorna número de denúncias
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/denuncias/{idComment}")
	public ResponseEntity<String> reportsComments(@PathVariable(value = "idComment") Long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);
		if (existingComment.isPresent()) {
			if (existingComment.get().getReported() != null) {
				return ResponseEntity.status(202)
						.body("Número de denúncias: " + existingComment.get().getReported().getUserReport().size());
			}
			return ResponseEntity.status(202).body("Número de denúncias: 0");

		}
		return ResponseEntity.status(404).build();
	}

}
