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
/**
 * @translator Amanda
 */

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentRepository repositoryC;

	/**
	 * Buscar todos os comentários
	 * 
	 * @return lista com todos os comentários
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Comment>> allComments() {
		List<Comment> CommentList = repositoryC.findAll();
		return ResponseEntity.status(200).body(CommentList);
	}

	/**
	 * Buscar um comentário especifico pelo id
	 * 
	 * @param id
	 * @return comentário referente ao id pesquisado ou um status notfound
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Comment> idComment(@PathVariable Long id) {
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
	@GetMapping("/upvotes/{idComment}")
	public ResponseEntity<String> upvotesComment(@PathVariable(value = "idComment") Long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);
		if (existingComment.isPresent()) {
			if (existingComment.get().getUpvoted() != null) {
				return ResponseEntity.status(202)
						.body("Número de upvotes: " + existingComment.get().getUpvoted().getUserUpvote().size());
			}
			return ResponseEntity.status(202).body("Número de upvotes: 0");

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
	@GetMapping("/reports/{idComment}")
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
