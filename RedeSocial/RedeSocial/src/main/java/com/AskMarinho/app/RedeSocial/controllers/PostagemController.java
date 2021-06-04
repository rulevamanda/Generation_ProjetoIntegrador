package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Postagem;
import com.AskMarinho.app.RedeSocial.repositories.PostagemRepository;
import com.AskMarinho.app.RedeSocial.services.PostagemService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostagemController {

	@Autowired
	private PostagemRepository repository;
	@Autowired
	private PostagemService service;

	/**
	 * Método para buscar todas as postagens
	 * 
	 * @return retorna todas as postagens cadastradas
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/todas")
	public ResponseEntity<List<Postagem>> todasPostagens() {
		List<Postagem> listaDePostagem = repository.findAll();
		return ResponseEntity.status(200).body(listaDePostagem);
	}

	/**
	 * Método para buscar postagens pelo id
	 * 
	 * @param id - id da postagem
	 * @return retorna a postagem buscada pelo id com um status 200 ou retorna um
	 *         status 404 com uma build vazia
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Postagem> idPostagem(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Método para buscar postagens pelo título
	 * 
	 * @param titulo - podendo não ser o título completo
	 * @return retorna postagens que possuem o título pesquisado
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> tituloPostagem(@PathVariable String titulo) {
		return ResponseEntity.status(200).body(repository.findAllByTituloContainingIgnoreCase(titulo));

	}

	/**
	 * Método para cadastrar nova postagem
	 * 
	 * @param novaPostagem - objeto passado pelo body da requisição
	 * @return status de 201 com a postagem criada ou um status 400 caso já tenha
	 *         uma postagem com o mesmo título
	 * @author Antonio
	 */
	@PostMapping("/cadastrar/{idUsuario}")
	public ResponseEntity<String> cadastrarPostagem(@PathVariable(value = "idUsuario") Long idUsuario,
			@RequestBody Postagem novaPostagem) {
		return service.cadastrarPostagem(idUsuario, novaPostagem)
				.map(postagemCriada -> ResponseEntity.status(201)
						.body("Título da postagem: " + novaPostagem.getTitulo() + "\nDescrição " + "da postagem: "
								+ novaPostagem.getDescricao() + "\nCADASTRADA"))
				.orElse(ResponseEntity.status(200).body("Erro ao cadastrar. Esses título já está sendo utilizado."));
	}

	/**
	 * Método para atualizar postagens
	 * 
	 * @param id       - id passado pela url
	 * @param postagem - dados passados pelo corpo da requisição
	 * @return retorna um status 201 e a postagem atualizada ou retorna um status
	 *         304 caso a postagem não exista
	 * @author Antonio
	 */
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<String> atualizarPostagem(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Postagem postagem) {
		return service.atualizarPostagem(id, postagem)
				.map(postagemAtualizada -> ResponseEntity.status(201)
						.body("Título da postagem: " + postagem.getTitulo() + "\nDescrição " + "da postagem: "
								+ postagem.getDescricao() + "\nATUALIZADA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao atualizar. Essa postagem não existe ou o título em duplicata"));

	}

	/**
	 * Método para deletar postagens
	 * 
	 * @param id - id passado pela url
	 * @return retorna um status 200 ou retorna um status 400 caso não exista uma
	 *         postagem com o id passado
	 * @author Antonio
	 */
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<String> deletaPostagem(@PathVariable long id) {
		Optional<Postagem> postagemExistente = repository.findById(id);

		if (postagemExistente.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).body("Postagem deletada com sucesso.");
		} else {
			return ResponseEntity.status(200).body("Postagem não pode ser deletada, pois não existe.");
		}
	}

}
