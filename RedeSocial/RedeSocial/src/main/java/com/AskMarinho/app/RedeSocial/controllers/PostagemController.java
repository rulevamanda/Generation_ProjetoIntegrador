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
@CrossOrigin(origins = "", allowedHeaders = "")

public class PostagemController {

	@Autowired
	private PostagemRepository repository;
	@Autowired
	private PostagemService service;

	/**
	 * MÃ©todo para buscar todas as postagens
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
	 * MÃ©todo para buscar postagens pelo id
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
	 * MÃ©todo para buscar postagens pelo tÃ­tulo
	 * 
	 * @param titulo - podendo nÃ£o ser o tÃ­tulo completo
	 * @return retorna postagens que possuem o tÃ­tulo pesquisado
	 * @author Antonio
	 * @author Bueno
	 */
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> tituloPostagem(@PathVariable String titulo) {
		return ResponseEntity.status(200).body(repository.findAllByTituloContainingIgnoreCase(titulo));

	}

	/**
	 * MÃ©todo para cadastrar nova postagem
	 * 
	 * @param novaPostagem - objeto passado pelo body da requisiÃ§Ã£o
	 * @return status de 201 com a postagem criada ou um status 400 caso jÃ¡ tenha
	 *         uma postagem com o mesmo tÃ­tulo
	 * @author Antonio
	 */
	@PostMapping("/cadastrar/{idUsuario}/{idTema}")
	public ResponseEntity<String> cadastrarPostagem(@PathVariable(value = "idUsuario") Long idUsuario,
			@PathVariable(value = "idTema") Long idTema, @RequestBody Postagem novaPostagem) {
		return service.cadastrarPostagem(idUsuario, idTema, novaPostagem)
				.map(postagemCriada -> ResponseEntity.status(201)
						.body("TÃ­tulo da postagem: " + novaPostagem.getTitulo() + "\nDescriÃ§Ã£o " + "da postagem: "
								+ novaPostagem.getDescricao() + "\nCADASTRADA"))
				.orElse(ResponseEntity.status(200).body("Erro ao cadastrar. Esses tÃ­tulo jÃ¡ estÃ¡ sendo utilizado."));
	}

	/**
	 * MÃ©todo para atualizar postagens
	 * 
	 * @param id       - id passado pela url
	 * @param postagem - dados passados pelo corpo da requisiÃ§Ã£o
	 * @return retorna um status 201 e a postagem atualizada ou retorna um status
	 *         304 caso a postagem nÃ£o exista
	 * @author Antonio
	 */
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<String> atualizarPostagem(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Postagem postagem) {
		return service.atualizarPostagem(id, postagem)
				.map(postagemAtualizada -> ResponseEntity.status(201)
						.body("TÃ­tulo da postagem: " + postagem.getTitulo() + "\nDescriÃ§Ã£o " + "da postagem: "
								+ postagem.getDescricao() + "\nATUALIZADA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao atualizar. Essa postagem nÃ£o existe ou o tÃ­tulo em duplicata"));

	}

	/**
	 * MÃ©todo para deletar postagens
	 * 
	 * @param id - id passado pela url
	 * @return retorna um status 200 ou retorna um status 400 caso nÃ£o exista uma
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
			return ResponseEntity.status(200).body("Postagem nÃ£o pode ser deletada, pois nÃ£o existe.");
		}
	}

	@PutMapping("adicionar/tema/{idTema}/{idPostagem}")
	public ResponseEntity<String> adicionarTema(@PathVariable (value = "idTema") Long idTema,
			@PathVariable (value = "idPostagem") Long idPostagem){
		return service.adicionarTema(idPostagem, idTema)
				.map(adicionado -> ResponseEntity.status(201).body("ATUALIZADO COM SUCESSO!"))
				.orElse(ResponseEntity.status(200).body("ERRO"));
	}
}