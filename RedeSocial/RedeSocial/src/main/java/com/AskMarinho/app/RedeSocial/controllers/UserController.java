package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.models.UserLogin;
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
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	private @Autowired UserRepository repositoryU;
	private @Autowired UserService serviceU;

	private @Autowired PostRepository repositoryP;

	private @Autowired CommentRepository repositoryC;

	// ----------------------- USUÁRIOS -----------------------

	/**
	 * Método que faz login na plataforma
	 * 
	 * @param user
	 * @return
	 * @author Bueno
	 */

	@PostMapping("/login")
	public ResponseEntity<Object> AuthenticationManagerBuilder(@RequestBody Optional<UserLogin> user) {
		return serviceU.login(user);
	}

	/**
	 * Rota para retornar todos os usuários
	 * 
	 * @author Chelle
	 * @author Amanda
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> searchAll() {
		List<Usuario> listAll = repositoryU.findAll();
		return ResponseEntity.status(200).body(listAll);
	}

	/**
	 * Rota para retornar um usuário pelo nome
	 * 
	 * @param name
	 * @author Amanda
	 * @author Chelle
	 * @return
	 */
	@GetMapping("/name/search")
	public ResponseEntity<Object> searchByName(@RequestParam(defaultValue = "") String name) {
		List<Usuario> listOfNames = repositoryU.findAllByNameContainingIgnoreCase(name);

		if (!listOfNames.isEmpty()) {
			return ResponseEntity.status(200).body(listOfNames);
		} else {
			return ResponseEntity.status(204).body("Erro ao listar usuários.");
		}
	}

	/**
	 * Rota para pegar um usuário pelo id
	 * 
	 * @param id
	 * @author Chelle
	 * @author Amanda
	 * @return
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> searchById(@PathVariable Long id) {
		return repositoryU.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Rota para registrar um usuário
	 * 
	 * @param newUser
	 * @author Amanda
	 * @author Chelle
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody Usuario newUser) {
		return serviceU.registerUser(newUser);
	}

	/**
	 * Rota para atualizar um usuário
	 * 
	 * @param updatedUser
	 * @param idUser
	 * @author Amanda
	 * @author Chelle
	 * @author Antonio
	 * @return
	 */
	@PutMapping("/update/{idUser}")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody Usuario updatedUser,
			@Valid @PathVariable(value = "idUser") Long idUser) {
		return serviceU.updateUser(idUser, updatedUser);
	}

	/**
	 * Rota para excluir um usuário
	 * 
	 * @param id_user
	 * @author Chelle
	 * @author Amanda
	 * @return
	 */
	@DeleteMapping("/delete/{id_user}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id_user) {
		Optional<Usuario> existingUser = repositoryU.findById(id_user);

		if (existingUser.isPresent()) {
			repositoryU.deleteById(id_user);
			return ResponseEntity.status(200).body("USUÁRIO DELETADO");
		} else {
			return ResponseEntity.status(400).body("Erro ao deletar usuário.");
		}
	}

	// ----------------------- POSTAGENS -----------------------

	/**
	 * Rota para retornar postagens com as tags favoritas de um usuário
	 * 
	 * @param idUser
	 * @author Antonio
	 * @return postagens com as tags favoritas de um usuário
	 */
	@GetMapping("/posts/favorites/{idUser}")
	public ResponseEntity<Set<Post>> returnPostsFav(@PathVariable(value = "idUser") Long idUser) {
		return serviceU.postsFavorites(idUser);
	}

	/**
	 * Rota para cadastrar nova postagem
	 * 
	 * @param novaPostagem - objeto passado pelo body da requisição
	 * @return status de 201 com a postagem criada ou um status 400 caso já tenha
	 *         uma postagem com o mesmo título
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PostMapping("/posts/register/{idUser}/{themeName}")
	public ResponseEntity<Object> registerPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "themeName") String themeName, @Valid @RequestBody Post newPost) {
		return serviceU.registerPost(idUser, themeName, newPost);
	}

	/**
	 * Rota para atualizar uma postagem
	 * 
	 * @param id       - id passado pela url
	 * @param postagem - dados passados pelo corpo da requisição
	 * @return retorna um status 201 e a postagem atualizada ou retorna um status
	 *         304 caso a postagem nãoo exista
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PutMapping("/posts/update/{id}")
	public ResponseEntity<Object> updatePost(@PathVariable(value = "id") Long id, @Valid @RequestBody Post post) {
		return serviceU.updatePost(id, post);
	}

	/**
	 * Rota para deletar uma postagem
	 * 
	 * @param id - id passado pela url
	 * @return retorna um status 200 ou retorna um status 400 caso não£o exista uma
	 *         postagem com o id passado
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@DeleteMapping("/posts/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {
		Optional<Post> existingPost = repositoryP.findById(id);

		if (existingPost.isPresent()) {
			repositoryP.deleteById(id);
			return ResponseEntity.status(200).body("POSTAGEM DELETADA");
		} else {
			return ResponseEntity.status(200).body("Erro ao deletar postagem.");
		}
	}

	/**
	 * Rota para adicionar um tema em uma postagem
	 * 
	 * @param themeName
	 * @param idPost
	 * @author Antonio
	 * @return
	 */
	@PutMapping("/posts/add/theme/{themeName}/{idPost}")
	public ResponseEntity<Object> addTheme(@PathVariable(value = "themeName") String themeName,
			@PathVariable(value = "idPost") Long idPost) {
		return serviceU.addTag(idPost, themeName);
	}

	/**
	 * Rota para retirar um tema de um post
	 * 
	 * @param idTheme
	 * @param idPost
	 * @author Antonio
	 * @return
	 */
	@DeleteMapping("/posts/delete/theme/{idTheme}/{idPost}")
	public ResponseEntity<Object> deletePostTheme(@PathVariable(value = "idTheme") Long idTheme,
			@PathVariable(value = "idPost") Long idPost) {
		return serviceU.deletePostTheme(idPost, idTheme);
	}

	/**
	 * . Rota para retornar o número de likes de um post
	 * 
	 * @param idPost
	 * @author Bueno
	 * @author Antonio
	 * @return
	 */
	@GetMapping("/posts/upvotes/{idPost}")
	public ResponseEntity<String> upvotesPost(@PathVariable(value = "idPost") Long idPost) {
		return serviceU.upvotesPost(idPost);
	}

	/**
	 * Rota para retornar o número de reports de um post
	 * 
	 * @param idPost
	 * @author Antonio
	 * @author Bueno
	 * @return
	 */
	@GetMapping("/posts/reports/{idPost}")
	public ResponseEntity<String> reportsPost(@PathVariable(value = "idPost") Long idPost) {
		return serviceU.reportsPosts(idPost);
	}

	// ----------------------- TEMAS -----------------------

	/**
	 * Rota para adicionar uma tag favorita a um usuário
	 * 
	 * @param idUser
	 * @param tagName
	 * @author Chelle
	 * @author Antonio
	 * @return
	 */
	@PutMapping("/add/theme/{idUser}/{tagName}")
	public ResponseEntity<Object> addTags(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "tagName") String tagName) {
		return serviceU.addFavoriteTag(idUser, tagName);
	}

	/**
	 * Rota para retirar um tema favorito de um usuário
	 * 
	 * @param idUser
	 * @param idTag
	 * @author Chelle
	 * @author Antonio
	 * @return
	 */
	@DeleteMapping("/delete/theme/favorites/{idUser}/{idTag}")
	public ResponseEntity<Object> deleteFavoriteTag(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idTag") Long idTag) {

		return serviceU.deleteFavoriteTag(idUser, idTag);
	}

	// ----------------------- COMENTÁRIOS -----------------------

	/**
	 * Rota para cadastrar cometário
	 * 
	 * @param idUsuario      - usuario que está comentando
	 * @param idPostagem     - postagem a ser comentada
	 * @param novoComentario
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PostMapping("/comments/register/{idUser}/{idPost}")
	public ResponseEntity<Object> registerPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost, @Valid @RequestBody Comment newComment) {
		return serviceU.registerComment(idUser, idPost, newComment);
	}

	/**
	 * Rota para atualizar um comentário
	 * 
	 * @param idComentario
	 * @param comentarioAtualizado
	 * @return uma lista com todos comentários, com o status 201, ou um status 400
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@PutMapping("/comments/update/{idComment}")
	public ResponseEntity<Object> updateComment(@PathVariable(value = "idComment") Long idComment,
			@Valid @RequestBody Comment commentUpdated) {
		return serviceU.updateComment(idComment, commentUpdated);

	}

	/**
	 * Rota para deletar um comentário
	 * 
	 * @param idComment
	 * @return uma mensagem para caso o comentário seja deletado ou não
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	@DeleteMapping("/comments/delete/{idComment}")
	public ResponseEntity<String> deleteComment(@PathVariable long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);

		if (existingComment.isPresent()) {
			repositoryC.deleteById(idComment);
			return ResponseEntity.status(200).body("COMENTÁRIO DELETADO");
		} else {
			return ResponseEntity.status(200).body("Erro ao deletar comentário.");
		}
	}

	/**
	 * Rota para receber o número de likes de um comentário
	 * 
	 * @param idComment
	 * @author Antonio
	 * @author Bueno
	 * @return
	 */
	@GetMapping("/comments/upvotes/{idComment}")
	public ResponseEntity<String> upvotesComment(@PathVariable(value = "idComment") Long idComment) {
		return serviceU.upvotesComment(idComment);
	}

	/**
	 * Rota para números de reports em um comentário
	 * 
	 * @param idComment
	 * @author Antonio
	 * @author Bueno
	 * @return
	 */
	@GetMapping("/comments/reports/{idComment}")
	public ResponseEntity<String> reportsComments(@PathVariable(value = "idComment") Long idComment) {
		return serviceU.reportsComments(idComment);
	}

	// ----------------------- DENÚNCIAS -----------------------

	/**
	 * Rota para reportar um post
	 * 
	 * @param idUser
	 * @param idPost
	 * @author Antonio
	 * @return
	 */
	@PostMapping("/reports/post/{idUser}/{idPost}")
	public ResponseEntity<Object> reportPost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost) {

		return serviceU.reportPost(idUser, idPost);
	}

	/**
	 * Rota para reportar um comentário
	 * 
	 * @param idUser
	 * @param idComment
	 * @author Antonio
	 * @return
	 */
	@PostMapping("/reports/comment/{idUser}/{idComment}")
	public ResponseEntity<Object> reportComment(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idComment") Long idComment) {

		return serviceU.reportComment(idUser, idComment);
	}

	/**
	 * Rota para retirar um report
	 * 
	 * @param idReport
	 * @param idUser
	 * @author Antonio
	 * @return
	 */
	@DeleteMapping("/report/delete/{idReport}/{idUser}")
	public ResponseEntity<Object> deleteReport(@PathVariable(value = "idReport") Long idReport,
			@PathVariable(value = "idUser") Long idUser) {
		return serviceU.deleteReport(idReport, idUser);

	}

	// ----------------------- UPVOTES -----------------------

	/**
	 * Rota para dar like em um post
	 * 
	 * @param idUser
	 * @param idPost
	 * @author Antonio
	 * @return
	 */
	@PostMapping("/upvotes/post/{idUser}/{idPost}")
	public ResponseEntity<Object> upvotePost(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idPost") Long idPost) {

		return serviceU.upvotePost(idUser, idPost);
	}

	/**
	 * Rota para dar like em um comentário
	 * 
	 * @param idUser
	 * @param idComment
	 * @author Antonio
	 * @return
	 */
	@PostMapping("/upvotes/comment/{idUser}/{idComment}")
	public ResponseEntity<Object> upvoteComment(@PathVariable(value = "idUser") Long idUser,
			@PathVariable(value = "idComment") Long idComment) {

		return serviceU.upvoteComment(idUser, idComment);
	}

	/**
	 * Rota para retirar um like
	 * 
	 * @param idUpvote
	 * @param idUser
	 * @author Antonio
	 * @return
	 */
	@DeleteMapping("/upvotes/delete/{idUpvote}/{idUser}")
	public ResponseEntity<Object> unupvote(@PathVariable(value = "idUpvote") Long idUpvote,
			@PathVariable(value = "idUser") Long idUser) {
		return serviceU.unupvote(idUpvote, idUser);
	}

}
