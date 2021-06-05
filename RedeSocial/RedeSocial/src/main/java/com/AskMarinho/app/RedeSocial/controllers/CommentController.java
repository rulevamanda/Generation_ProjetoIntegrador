package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;

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

}
