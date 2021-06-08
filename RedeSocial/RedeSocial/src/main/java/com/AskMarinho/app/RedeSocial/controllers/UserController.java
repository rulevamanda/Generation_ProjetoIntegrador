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

/**
 * @redactor Amanda
 * @translator Amanda
 *
 */
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
	public ResponseEntity<List<User>> searchAll() {
		List<User> listAll = repositoryU.findAll();
		return ResponseEntity.status(200).body(listAll);
	}

	@GetMapping("/nome/pesquisar")
	public ResponseEntity<Object> searchByName(@RequestParam(defaultValue = "") String name) {
		List<User> listOfNames = repositoryU.findAllByNameContainingIgnoreCase(name);

		if (!listOfNames.isEmpty()) {
			return ResponseEntity.status(200).body(listOfNames);
		} else {
			return ResponseEntity.status(204).body("Erro ao listar usuários.");
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> searchById(@PathVariable Long id) {
		return repositoryU.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<String> registerUser(@Valid @RequestBody User newUser) {
		return serviceU.registerUser(newUser)
				.map(registeredEmail -> ResponseEntity.status(201)
						.body("Usuario: " + newUser.getUserName() + "\nEmail: " + newUser.getEmail()
								+ "\nUSUÁRIO CADASTRADO"))
				.orElse(ResponseEntity.status(400)
						.body("Erro ao cadastrar usuário."));
	}

	@PutMapping("/atualizar/{id_usuario}")
	public ResponseEntity<String> updateUser(@Valid @RequestBody User updatedUser,
			@Valid @PathVariable(value = "id_usuario") Long id) {
		return serviceU.updateUser(id, updatedUser)
				.map(updateUser -> ResponseEntity.status(201)
						.body("Usuario: " + updatedUser.getUserName() + "\nEmail: "
								+ updatedUser.getEmail() + "\nUSUÁRIO ATUALIZADO"))
				.orElse(ResponseEntity.status(400).body(
						"Erro ao atualizar usuário."));
	}

	@DeleteMapping("/deletar/{id_user}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id_user) {
		Optional<User> existingUser = repositoryU.findById(id_user);

		if (existingUser.isPresent()) {
			repositoryU.deleteById(id_user);
			return ResponseEntity.status(200).body("USUÁRIO DELETADO");
		} else {
			return ResponseEntity.status(400).body("Erro ao deletar usuário.");
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
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PostMapping("/postagens/cadastrar/{idUser}/{themeName}")
	public ResponseEntity<String> registerPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "themeName") String themeName, @RequestBody Post newPost) {
		return serviceU.registerPost(idUser, themeName, newPost)
				.map(postCreated -> ResponseEntity.status(201)
						.body("Título da postagem: " + newPost.getTitle() + "\nDescrição " + "da postagem: "
								+ newPost.getDescription() + "\nPOSTAGEM CADASTRADA"))
				.orElse(ResponseEntity.status(200).body("Erro ao cadastrar título."));
	}

	/**
	 * MÃ©todo para atualizar postagens
	 * 
	 * @param id       - id passado pela url
	 * @param postagem - dados passados pelo corpo da requisiÃ§Ã£o
	 * @return retorna um status 201 e a postagem atualizada ou retorna um status
	 *         304 caso a postagem nÃ£o exista
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PutMapping("/postagens/atualizar/{id}")
	public ResponseEntity<String> updatePost(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Post post) {
		return serviceU.updatePost(id, post)
				.map(updatePost -> ResponseEntity.status(201)
						.body("Título da postagem: " + post.getTitle() + "\nDescrição " + "da postagem: "
								+ post.getDescription() + "\nPOSTAGEM ATUALIZADA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao atualizar postagem"));

	}

	/**
	 * MÃ©todo para deletar postagens
	 * 
	 * @param id - id passado pela url
	 * @return retorna um status 200 ou retorna um status 400 caso nÃ£o exista uma
	 *         postagem com o id passado
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@DeleteMapping("/postagens/deletar/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {
		Optional<Post> existingPost = repositoryP.findById(id);

		if (existingPost.isPresent()) {
			repositoryP.deleteById(id);
			return ResponseEntity.status(200).body("POSTAGEM DELETADA");
		} else {
			return ResponseEntity.status(200).body("Erro ao deletar postagem.");
		}
	}

	@PutMapping("/postagens/adicionar/tema/{nomeTema}/{idPostagem}")
	public ResponseEntity<String> addTheme(@PathVariable(value = "nomeTema") String themeName,
			@PathVariable(value = "idPostagem") Long idPost) {
		return serviceU.addTag(idPost, themeName)
				.map(added -> ResponseEntity.status(201).body("TEMA ADICIONADO"))
				.orElse(ResponseEntity.status(200).body("Erro ao adicionar Tema."));
	}

	@DeleteMapping("/postagens/deletar/tema/{idTema}/{idPostagem}")
	public ResponseEntity<String> deletePostTheme(@PathVariable(value = "idTema") Long idTheme,
			@PathVariable(value = "idPostagem") Long idPost) {
		return serviceU.deletePostTheme(idPost, idTheme)
				.map(deleted -> ResponseEntity.status(200).body("TEMA DA POSTAGEM DELETADO"))
				.orElse(ResponseEntity.status(404).build());
	}

	// ----------------------- TEMAS -----------------------

	@PutMapping("/adicionar/tema/{idUser}/{tagName}")
	public ResponseEntity<String> addTags(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "tagName") String tagName) {
		return serviceU.addFavoriteTag(idUser, tagName)
				.map(addedTag -> ResponseEntity.status(201).body("TEMA FAVORITO ADICIONADO"))
				.orElse(ResponseEntity.status(400).build());
	}

	@DeleteMapping("/deletar/tema/favoritos/{idUser}/{idTag}")

	public ResponseEntity<String> deleteFavoriteTag(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idTag") Long idTag) {

		return serviceU.deleteFavoriteTag(idUser, idTag)
				.map(deletedTag -> ResponseEntity.status(202).body("TEMA FOVORITO DELETADO"))
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
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PostMapping("/comentarios/cadastrar/{idUser}/{idPost}")
	public ResponseEntity<List<Comment>> registerPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost, @RequestBody Comment newComment) {
		return serviceU.registerComment(idUser, idPost, newComment)
				.map(comment -> ResponseEntity.status(201).body(repositoryC.findAll()))
				.orElse(ResponseEntity.status(400).build());
	}

	/**
	 * Rota para atualizar um comentário
	 * 
	 * @param idComentario
	 * @param comentarioAtualizado
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PutMapping("/comentarios/atualizar/{idComment}")
	public ResponseEntity<List<Comment>> updatePost(@PathVariable(value = "idComment") Long idComment,
			@Valid @RequestBody Comment commentUpdated) {
		return serviceU.updateComment(idComment, commentUpdated)
				.map(postagemAtualizada -> ResponseEntity.status(201).body(repositoryC.findAll()))
				.orElse(ResponseEntity.status(400).build());

	}

	/**
	 * Rota para deletar um comentário
	 * 
	 * @param idComentario
	 * @return uma mensagem para caso o comentário seja deletado ou não
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@DeleteMapping("/comentarios/deletar/{idComment}")
	public ResponseEntity<String> deleteComment(@PathVariable long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);

		if (existingComment.isPresent()) {
			repositoryC.deleteById(idComment);
			return ResponseEntity.status(200).body("COMENTÁRIO DELETADO");
		} else {
			return ResponseEntity.status(200).body("Erro ao deletar comentário.");
		}
	}

	// ----------------------- DENÚNCIAS -----------------------

	@PostMapping("/denuncias/postagem/{idUser}/{idPost}")
	public ResponseEntity<String> reportPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost) {

		return serviceU.reportPost(idUser, idPost)
				.map(reported -> ResponseEntity.status(201).body("POSTAGEM DENUNCIADA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao denunciar postagem."));
	}

	@PostMapping("/denuncias/comentario/{idUser}/{idComment}")
	public ResponseEntity<String> reportComment(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idComment") Long idComment) {

		return serviceU.reportComment(idUser, idComment)
				.map(reported -> ResponseEntity.status(201).body("COMENTÁRIO DENUNCIADO"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao denunciar comentário."));
	}

	@DeleteMapping("/denuncias/deletar/{idReport}/{idUser}")
	public ResponseEntity<String> deleteReport(@PathVariable(value = "idReport") Long idReport,
			@PathVariable(value = "idUser") Long idUser) {
		return serviceU.deleteReport(idReport, idUser)
				.map(deleted -> ResponseEntity.status(202).body("DENÚNCIA RETIRADA"))
				.orElse(ResponseEntity.status(404).build());

	}

	// ----------------------- UPVOTES -----------------------

	@PostMapping("/likes/postagem/{idUser}/{idPost}")
	public ResponseEntity<String> upvotePost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost) {

		return serviceU.upvotePost(idUser, idPost).map(upvoted -> ResponseEntity.status(201).body("POSTAGEM CURTIDA"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao curtir postagem."));
	}

	@PostMapping("/likes/comentario/{idUser}/{idComment}")
	public ResponseEntity<String> upvoteComment(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idComment") Long idComment) {

		return serviceU.upvoteComment(idUser, idComment).map(upvoted -> ResponseEntity.status(201).body("COMENTÁRIO CURTIDO"))
				.orElse(ResponseEntity.status(200)
						.body("Erro ao curtir comentário."));
	}
	
	@DeleteMapping("/likes/deletar/{idLike}/{idUser}")
	public ResponseEntity<String> unupvote(@PathVariable(value = "idLike") Long idUpvote,
			@PathVariable(value = "idUser") Long idUser) {
		return serviceU.unupvote(idUpvote, idUser)
				.map(deleted -> ResponseEntity.status(202).body("CURTIDA RETIRADA"))
				.orElse(ResponseEntity.status(404).build());

	}

}
