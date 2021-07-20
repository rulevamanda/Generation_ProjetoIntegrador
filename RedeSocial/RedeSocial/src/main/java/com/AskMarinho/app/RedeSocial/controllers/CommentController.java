package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

	@Autowired
	private CommentRepository repositoryC;

	/**
	 * Rota para buscar todos os comentários
	 * 
	 * @author Antonio
	 * @return lista com todos os comentários
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Comment>> allComments() {
		List<Comment> CommentList = repositoryC.findAll();
		return ResponseEntity.status(200).body(CommentList);
	}

	/**
	 * Rota para buscar um comentário especifico pelo id
	 * 
	 * @author Antonio
	 * @param id
	 * @return comentário referente ao id pesquisado ou um status notfound
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Comment> idComment(@PathVariable Long id) {
		return repositoryC.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

}
