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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.User;
import com.AskMarinho.app.RedeSocial.repositories.CommentRepository;
import com.AskMarinho.app.RedeSocial.repositories.PostRepository;
import com.AskMarinho.app.RedeSocial.repositories.UserRepository;
import com.AskMarinho.app.RedeSocial.services.UserService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	private @Autowired UserRepository repositoryU;
	private @Autowired UserService serviceU;

	private @Autowired PostRepository repositoryP;

	private @Autowired CommentRepository repositoryC;

	// ----------------------- USUÁRIOS -----------------------

	@GetMapping("/todes")
	public ResponseEntity<List<User>> buscarTodes() {
		List<User> listarTodes = repositoryU.findAll();
		return ResponseEntity.status(200).body(listarTodes);
	}

	@GetMapping("/nome/pesquisar")
	public ResponseEntity<Object> buscarPorNome(@RequestParam(defaultValue = "") String nome) {
		List<User> listaDeNomes = repositoryU.findAllByNameContainingIgnoreCase(nome);

		if (!listaDeNomes.isEmpty()) {
			return ResponseEntity.status(200).body(listaDeNomes);
		} else {
			return ResponseEntity.status(204).body("Ooops... Parece que esse usuário ainda não existe!");
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
		return repositoryU.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody User novoUsuario) {
		return serviceU.cadastrarUsuario(novoUsuario)
				.map(emailCadastrado -> ResponseEntity.status(201)
						.body("Usuario: " + novoUsuario.getUserName() + "\nEmail: " + novoUsuario.getEmail()
								+ "\nCADASTRADO"))
				.orElse(ResponseEntity.status(400)
						.body("Erro ao cadastrar. Nome de Usuário ou Email já está sendo utilizado."));
	}

	@PutMapping("/atualizar/{id_usuario}")
	public ResponseEntity<String> atualizarUsuario(@Valid @RequestBody User atualizacaoUsuario,
			@Valid @PathVariable(value = "id_usuario") Long id) {
		return serviceU.atualizarUsuario(id, atualizacaoUsuario)
				.map(atualizarUsuario -> ResponseEntity.status(201)
						.body("Usuario: " + atualizacaoUsuario.getUserName() + "\nEmail: "
								+ atualizacaoUsuario.getEmail() + "\nATUALIZADO"))
				.orElse(ResponseEntity.status(400).body(
						"Erro ao atualizar. Usuário não existe ou o nome de Usuário ou Email já está sendo utilizado."));
	}

	@DeleteMapping("/deletar/{id_usuario}")
	public ResponseEntity<String> deletarUsuario(@PathVariable Long id_usuario) {
		Optional<User> usuarioExistente = repositoryU.findById(id_usuario);

		if (usuarioExistente.isPresent()) {
			repositoryU.deleteById(id_usuario);
			return ResponseEntity.status(200).body("Usuário deletado com sucesso");
		} else {
			return ResponseEntity.status(400).body("Erro ao deletar usuário. \nUsuário não existe");
		}
	}

	// ----------------------- POSTAGENS -----------------------

	/**
	 * MÃ©todo para cadastrar nova postagem
	 * 
	 * @param novaPostagem - objeto passado pelo body da requisiÃ§Ã£o
	 * @return status de 201 com a postagem criada ou um status 400 caso jÃ¡ tenha
	 *         uma postagem com o mesmo tÃ­tulo
	 * @author Antonio
	 */
	@PostMapping("/postagens/cadastrar/{idUsuario}/{nomeTema}")
	public ResponseEntity<String> cadastrarPostagem(@PathVariable(value = "idUsuario") Long idUsuario,
			@PathVariable(value = "nomeTema") String nomeTema, @RequestBody Post novaPostagem) {
		return serviceU.cadastrarPostagem(idUsuario, nomeTema, novaPostagem)
				.map(postagemCriada -> ResponseEntity.status(201)
						.body("Título da postagem: " + novaPostagem.getTitle() + "\nDescrição " + "da postagem: "
								+ novaPostagem.getDescription() + "\nCADASTRADA"))
				.orElse(ResponseEntity.status(200).body("Erro ao cadastrar. Esses título já está sendo utilizado."));
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
	@PutMapping("/postagens/atualizar/{id}")
	public ResponseEntity<String> atualizarPostagem(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Post postagem) {
		return serviceU.atualizarPostagem(id, postagem)
				.map(postagemAtualizada -> ResponseEntity.status(201)
						.body("Título da postagem: " + postagem.getTitle() + "\nDescrição " + "da postagem: "
								+ postagem.getDescription() + "\nATUALIZADA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao atualizar. Essa postagem não existe ou o título em duplicata"));

	}

	/**
	 * MÃ©todo para deletar postagens
	 * 
	 * @param id - id passado pela url
	 * @return retorna um status 200 ou retorna um status 400 caso nÃ£o exista uma
	 *         postagem com o id passado
	 * @author Antonio
	 */
	@DeleteMapping("/postagens/deletar/{id}")
	public ResponseEntity<String> deletaPostagem(@PathVariable long id) {
		Optional<Post> postagemExistente = repositoryP.findById(id);

		if (postagemExistente.isPresent()) {
			repositoryP.deleteById(id);
			return ResponseEntity.status(200).body("Postagem deletada com sucesso.");
		} else {
			return ResponseEntity.status(200).body("Postagem não pode ser deletada, pois não existe.");
		}
	}

	@PutMapping("/postagens/adicionar/tema/{nomeTema}/{idPostagem}")
	public ResponseEntity<String> adicionarTema(@PathVariable(value = "nomeTema") String nomeTema,
			@PathVariable(value = "idPostagem") Long idPostagem) {
		return serviceU.addTag(idPostagem, nomeTema)
				.map(adicionado -> ResponseEntity.status(201).body("ATUALIZADO COM SUCESSO!"))
				.orElse(ResponseEntity.status(200).body("ERRO"));
	}

	@DeleteMapping("/postagens/deletar/tema/{idTema}/{idPostagem}")
	public ResponseEntity<String> deletarTemaPostagem(@PathVariable(value = "idTema") Long idTema,
			@PathVariable(value = "idPostagem") Long idPostagem) {
		return serviceU.deletarTemaDaPostagem(idPostagem, idTema)
				.map(deletado -> ResponseEntity.status(200).body("Tema deletado da postagem com sucesso"))
				.orElse(ResponseEntity.status(404).build());
	}
	
	// ----------------------- TEMAS -----------------------
	
	@PutMapping ("/adicionar/tema/{idUser}/{tagName}")
	public ResponseEntity<String> addTags (@PathVariable (value = "idUser") Long idUser, 
			@PathVariable (value = "tagName") String tagName){
		return serviceU.addFavoriteTag(idUser, tagName)
				.map(addedTag -> ResponseEntity.status(201).body("Tema favorito adicionado"))
				.orElse(ResponseEntity.status(400).build());
	}
	
	@DeleteMapping ("/deletar/tema/favoritos/{idUser}/{idTag}")
	
	public ResponseEntity<String> deleteFavoriteTag (@PathVariable (value = "idUser") Long idUser,
			@PathVariable (value = "idTag") Long idTag) {
		
		return serviceU.deleteFavoriteTag(idUser, idTag)
				.map(deletedTag -> ResponseEntity.status(202).body("Tema favorito deletado com sucesso!"))
				.orElse(ResponseEntity.status(404).build());
	}

	// ----------------------- COMENTÁRIOS -----------------------

	/**
	 * Rota para cadastrar cometário
	 * 
	 * @param idUsuario      - usuario que está comentando
	 * @param idPostagem     - postagem a ser comentada
	 * @param novoComentario
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 * 
	 */
	@PostMapping("/comentarios/cadastrar/{idUsuario}/{idPostagem}")
	public ResponseEntity<List<Comment>> cadastrarPostagem(@PathVariable(value = "idUsuario") Long idUsuario,
			@PathVariable(value = "idPostagem") Long idPostagem, @RequestBody Comment novoComentario) {
		return serviceU.cadastrarComentario(idUsuario, idPostagem, novoComentario)
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
	@PutMapping("/comentarios/atualizar/{idComentario}")
	public ResponseEntity<List<Comment>> atualizarPostagem(@PathVariable(value = "idComentario") Long idComentario,
			@Valid @RequestBody Comment comentarioAtualizado) {
		return serviceU.atualizarComentario(idComentario, comentarioAtualizado)
				.map(postagemAtualizada -> ResponseEntity.status(201).body(repositoryC.findAll()))
				.orElse(ResponseEntity.status(400).build());

	}

	/**
	 * Rota para deletar um comentário
	 * 
	 * @param idComentario
	 * @return uma mensagem para caso o comentário seja deletado ou não
	 */
	@DeleteMapping("/comentarios/deletar/{idComentario}")
	public ResponseEntity<String> deletaComentario(@PathVariable long idComentario) {
		Optional<Comment> comentarioExistente = repositoryC.findById(idComentario);

		if (comentarioExistente.isPresent()) {
			repositoryC.deleteById(idComentario);
			return ResponseEntity.status(200).body("Comentário deletado com sucesso.");
		} else {
			return ResponseEntity.status(200).body("Comentário não pode ser deletado, pois não existe.");
		}
	}

	// ----------------------- DENÚNCIAS -----------------------

	@PostMapping("/denuncias/postagem/{idUser}/{idPost}")
	public ResponseEntity<String> reportPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost) {

		return serviceU.reportPost(idUser, idPost)
				.map(reported -> ResponseEntity.status(201).body("Postagem denunciada"))
				.orElse(ResponseEntity.status(200)
						.body("Postagem ou usuário não existem, ou esse usuário já denunciou esta postagem"));
	}

	@PostMapping("/denuncias/comentario/{idUser}/{idComment}")
	public ResponseEntity<String> reportComment(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idComment") Long idComment) {

		return serviceU.reportComment(idUser, idComment)
				.map(reported -> ResponseEntity.status(201).body("Comentário denunciado"))
				.orElse(ResponseEntity.status(200)
						.body("Comentário ou usuário não existem, ou esse usuário já denunciou este comentário"));
	}

	@DeleteMapping("/denuncias/postagens/deletar/{idReport}/{idUser}")
	public ResponseEntity<String> deleteReport(@PathVariable(value = "idReport") Long idReport,
			@PathVariable(value = "idUser") Long idUser) {
		return serviceU.deleteReport(idReport, idUser)
				.map(deleted -> ResponseEntity.status(202).body("Denúncia retirada"))
				.orElse(ResponseEntity.status(404).build());

	}

}
