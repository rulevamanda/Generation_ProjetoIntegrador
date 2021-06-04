package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Comentario;

import com.AskMarinho.app.RedeSocial.repositories.ComentarioRepository;
import com.AskMarinho.app.RedeSocial.services.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
	private ComentarioRepository repositoryC;

	@Autowired
	private ComentarioService serviceC;

	/**
	 * Buscar todos os comentários
	 * 
	 * @return lista com todos os comentários
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<Comentario>> todosComentarios() {
		List<Comentario> listaDeComentario = repositoryC.findAll();
		return ResponseEntity.status(200).body(listaDeComentario);
	}

	/**
	 * Buscar um comentário especifico pelo id
	 * 
	 * @param id
	 * @returno comentário referente ao id pesquisado ou um status notfound
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Comentario> idComentario(@PathVariable Long id) {
		return repositoryC.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Rota para cadastrar cometário
	 * 
	 * @param idUsuario      - usuario que está comentando
	 * @param idPostagem     - postagem a ser comentada
	 * @param novoComentario
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 * 
	 */
	@PostMapping("/cadastrar/{idUsuario}/{idPostagem}")
	public ResponseEntity<List<Comentario>> cadastrarPostagem(@PathVariable(value = "idUsuario") Long idUsuario,
			@PathVariable(value = "idPostagem") Long idPostagem, @RequestBody Comentario novoComentario) {
		return serviceC.cadastrarComentario(idUsuario, idPostagem, novoComentario)
				.map(comentario -> ResponseEntity.status(201).body(repositoryC.findAll()))
				.orElse(ResponseEntity.status(400).build());
	}

	/**
	 * Rota para atualizar um comentário
	 * 
	 * @param idComentario
	 * @param comentarioAtualizado
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 */
	@PutMapping("/atualizar/{idComentario}")
	public ResponseEntity<List<Comentario>> atualizarPostagem(@PathVariable(value = "idComentario") Long idComentario,
			@Valid @RequestBody Comentario comentarioAtualizado) {
		return serviceC.atualizarComentario(idComentario, comentarioAtualizado)
				.map(postagemAtualizada -> ResponseEntity.status(201).body(repositoryC.findAll()))
				.orElse(ResponseEntity.status(400).build());

	}

	/**
	 * Rota para deletar um comentário
	 * 
	 * @param idComentario
	 * @return uma mensagem para caso o comentário seja deletado ou não
	 */
	@DeleteMapping("/deletar/{idComentario}")
	public ResponseEntity<String> deletaPostagem(@PathVariable long idComentario) {
		Optional<Comentario> postagemExistente = repositoryC.findById(idComentario);

		if (postagemExistente.isPresent()) {
			repositoryC.deleteById(idComentario);
			return ResponseEntity.status(200).body("Comentário deletado com sucesso.");
		} else {
			return ResponseEntity.status(200).body("Comentário não pode ser deletado, pois não existe.");
		}
	}

}
