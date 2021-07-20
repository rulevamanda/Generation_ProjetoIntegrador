package com.AskMarinho.app.RedeSocial.services;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.models.UserLogin;
import com.AskMarinho.app.RedeSocial.repositories.CommentRepository;
import com.AskMarinho.app.RedeSocial.repositories.PostRepository;
import com.AskMarinho.app.RedeSocial.repositories.TagRepository;
import com.AskMarinho.app.RedeSocial.repositories.UserRepository;

@Service
public class UserService {

	private @Autowired UserRepository repositoryU;

	private @Autowired TagRepository repositoryT;

	private @Autowired PostRepository repositoryP;

	private @Autowired CommentRepository repositoryC;

	// ----------------------- USUÁRIOS -----------------------

	/**
	 * Método para cadastrar um novo usuário onde verifica se o email informado já
	 * foi cadastrado ou não.
	 * 
	 * @param novoEmail - String representando a entidade Usuario
	 * @return o usuário cadastrado ou um erro.
	 * @author Chelle
	 * @author Amanda
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 * @since 1.0
	 */
	public ResponseEntity<Usuario> registerUser(Usuario newUser) {
		if (newUser.getName().contains("{") || newUser.getName().contains("}") || newUser.getName().contains("/")
				|| newUser.getName().contains("\\") || newUser.getName().contains("%")
				|| newUser.getName().contains("$") || newUser.getName().contains("&") || newUser.getName().contains("*")
				|| newUser.getName().contains("|") || newUser.getName().contains("@") || newUser.getName().contains("*")
				|| newUser.getName().contains("(") || newUser.getName().contains(")")
				|| newUser.getName().contains("§")) {
			return ResponseEntity.status(303).build();
		}
		if (newUser.getEmail().contains("{") || newUser.getEmail().contains("}") || newUser.getEmail().contains("/")
				|| newUser.getEmail().contains("\\") || newUser.getEmail().contains("%")
				|| newUser.getEmail().contains("$") || newUser.getEmail().contains("&")
				|| newUser.getEmail().contains("*") || newUser.getEmail().contains("|")
				|| newUser.getEmail().contains("*") || newUser.getEmail().contains("(")
				|| newUser.getEmail().contains(")") || newUser.getEmail().contains("§")) {
			return ResponseEntity.status(403).build();
		}
		if (newUser.getUserName().contains("{") || newUser.getName().contains("}")
				|| newUser.getUserName().contains("/") || newUser.getUserName().contains("\\")
				|| newUser.getUserName().contains("%") || newUser.getUserName().contains("$")
				|| newUser.getUserName().contains("&") || newUser.getUserName().contains("*")
				|| newUser.getUserName().contains("|") || newUser.getUserName().contains("@")
				|| newUser.getUserName().contains("*") || newUser.getUserName().contains("(")
				|| newUser.getUserName().contains(")") || newUser.getUserName().contains("§")) {
			return ResponseEntity.status(405).build();		
		}

		Optional<Usuario> existingEmal = repositoryU.findByEmail(newUser.getEmail());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String passwordEncoder = encoder.encode(newUser.getPassword());
		newUser.setPassword(passwordEncoder);

		if (existingEmal.isEmpty()) {
			Optional<Usuario> existingUser = repositoryU.findByUserName(newUser.getUserName());

			if (existingUser.isEmpty()) {
				return ResponseEntity.status(201).body(repositoryU.save(newUser));
			} else {
				return ResponseEntity.status(400).build();
			}

		} else {
			return ResponseEntity.status(404).build();
		}
	}

	/**
	 * Método de login
	 * 
	 * @param newUser
	 * @return Usuário logado com seu token ou erro respectivo
	 * @author Bueno
	 */
	public ResponseEntity<UserLogin> login(Optional<UserLogin> newUser) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> user = repositoryU.findByEmail(newUser.get().getEmail());

		if (user.isPresent()) {
			if (newUser.get().getPassword() != null) {
				if (encoder.matches(newUser.get().getPassword(), user.get().getPassword())) {
					String auth = newUser.get().getEmail() + ":" + newUser.get().getPassword();
					byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

					String authHeader = "Basic " + new String(encodedAuth);

					newUser.get().setToken(authHeader);
					newUser.get().setName(user.get().getName());
					newUser.get().setEmail(user.get().getEmail());
					newUser.get().setIdUser(user.get().getIdUser());
					newUser.get().setUrlImage(user.get().getUrlImage());
					newUser.get().setTipo(user.get().getTipo());

					return ResponseEntity.status(200).body(newUser.get());
				} else {
					ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
				}
			} else {
				ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}

		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	/**
	 * Método para atualizar um usuário existente com base em verificação de Id.
	 * 
	 * @param id_usuario    - Long
	 * @param updateUsuario - String
	 * @since 1.0
	 * @author Chelle
	 * @author Amanda
	 * @author Antonio
	 * @return Retorna o usuário atualizado ou um erro.
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Usuario> updateUser(Long id_user, Usuario updatedUser) {
		if (updatedUser.getName().contains("{") || updatedUser.getName().contains("}")
				|| updatedUser.getName().contains("/") || updatedUser.getName().contains("\\")
				|| updatedUser.getName().contains("%") || updatedUser.getName().contains("$")
				|| updatedUser.getName().contains("&") || updatedUser.getName().contains("*")
				|| updatedUser.getName().contains("|") || updatedUser.getName().contains("@")
				|| updatedUser.getName().contains("*") || updatedUser.getName().contains("(")
				|| updatedUser.getName().contains(")") || updatedUser.getName().contains("§")) {
			return ResponseEntity.status(303).build();
		}
		if (updatedUser.getEmail().contains("{") || updatedUser.getEmail().contains("}")
				|| updatedUser.getEmail().contains("/") || updatedUser.getEmail().contains("\\")
				|| updatedUser.getEmail().contains("%") || updatedUser.getEmail().contains("$")
				|| updatedUser.getEmail().contains("&") || updatedUser.getEmail().contains("*")
				|| updatedUser.getEmail().contains("|") || updatedUser.getEmail().contains("*")
				|| updatedUser.getEmail().contains("(") || updatedUser.getEmail().contains(")")
				|| updatedUser.getEmail().contains("§")) {
			return ResponseEntity.status(403).build();
		}
		if (updatedUser.getUserName().contains("{") || updatedUser.getName().contains("}")
				|| updatedUser.getUserName().contains("/") || updatedUser.getUserName().contains("\\")
				|| updatedUser.getUserName().contains("%") || updatedUser.getUserName().contains("$")
				|| updatedUser.getUserName().contains("&") || updatedUser.getUserName().contains("*")
				|| updatedUser.getUserName().contains("|") || updatedUser.getUserName().contains("@")
				|| updatedUser.getUserName().contains("*") || updatedUser.getUserName().contains("(")
				|| updatedUser.getUserName().contains(")") || updatedUser.getUserName().contains("§")) {
			return ResponseEntity.status(405).build();
		}

		Boolean update = false;
		Optional<Usuario> existingUser = repositoryU.findById(id_user);

		if (existingUser.isPresent()) {
			Optional<Usuario> emailExistente = repositoryU.findByEmail(updatedUser.getEmail());

			if (emailExistente.isEmpty()) {

				Optional<Usuario> usuarioRepetido = repositoryU.findByUserName(updatedUser.getUserName());
				if (usuarioRepetido.isEmpty()) {

					update = true;

				} else {

					if (updatedUser.getUserName().equals(existingUser.get().getUserName())) {
						update = true;
					} else {
						return ResponseEntity.status(444).build();
					}

				}

			} else {
				if (updatedUser.getEmail().equals(existingUser.get().getEmail())) {

					Optional<Usuario> usuarioRepetido = repositoryU.findByUserName(updatedUser.getUserName());
					if (usuarioRepetido.isEmpty()) {

						update = true;

					} else {

						if (updatedUser.getUserName().equals(existingUser.get().getUserName())) {
							update = true;
						} else {
							return ResponseEntity.status(406).build();
						}

					}

				} else {
					return ResponseEntity.status(304).build();
				}
			}
			if (update) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

				String senhaEncoder = encoder.encode(updatedUser.getPassword());
				updatedUser.setPassword(senhaEncoder);

				existingUser.get().setName(updatedUser.getName());
				existingUser.get().setPassword(updatedUser.getPassword());
				existingUser.get().setGender(updatedUser.getGender());
				existingUser.get().setBirth(updatedUser.getBirth());
				existingUser.get().setEmail(updatedUser.getEmail());
				existingUser.get().setUserName(updatedUser.getUserName());
				existingUser.get().setUrlImage(updatedUser.getUrlImage());
				existingUser.get().setDescription(updatedUser.getDescription());

				repositoryU.save(existingUser.get());
				return ResponseEntity.status(201).body(existingUser.get());
			}

		}
		return ResponseEntity.status(404).build();

	}

	// ----------------------- POSTAGENS -----------------------

	/**
	 * Método para retornar as postagens com as tags favoritas de um usuário
	 * 
	 * @param idUser
	 * @author Antonio
	 * @return Lista de postagens com as tags favoritas de um usuário ou um status
	 *         404
	 */
	public ResponseEntity<Set<Post>> postsFavorites(Long idUser) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);
		Set<Post> posts = new HashSet<>();
		if (existingUser.isPresent()) {
			Set<Tag> tagFavorites = existingUser.get().getFavorites();

			for (Tag tags : tagFavorites) {
				List<Post> existingPost = repositoryP.findAllByTagRelation(tags);

				for (int i = 0; i < existingPost.size(); i++) {
					posts.add(existingPost.get(i));
				}
			}

			return ResponseEntity.status(202).body(posts);
		}
		return ResponseEntity.status(404).build();
	}
	
	public ResponseEntity<Set<Post>> postsFavoritesTag(Long idUser, String tagName) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);
		Set<Post> posts = new HashSet<>();
		if (existingUser.isPresent()) {
			Set<Tag> tagFavorites = existingUser.get().getFavorites();
			
			Set<Tag> tagSearch =  repositoryT.findAllByTagNameContainingIgnoreCase(tagName);
			
			Set<Tag> intersectTags = new HashSet<>();
			
			
			for (Tag tagsIntersect1 : tagFavorites) {
				for (Tag tagIntersect2 : tagSearch) {
					if (tagsIntersect1 == tagIntersect2) {
						intersectTags.add(tagsIntersect1);
					}
				}
			}
			
			for (Tag tags : intersectTags) {
				
				List<Post> existingPost = repositoryP.findAllByTagRelation(tags);

				for (int i = 0; i < existingPost.size(); i++) {
					posts.add(existingPost.get(i));
				}
			}

			return ResponseEntity.status(202).body(posts);
		}
		return ResponseEntity.status(404).build();
	}
	
	public ResponseEntity<Set<Post>> allPostsTags() {
		
		Set<Post> posts = new HashSet<>();
		
			List<Tag> allTags = repositoryT.findAll();

			for (Tag tags : allTags) {
				List<Post> existingPost = repositoryP.findAllByTagRelation(tags);

				for (int i = 0; i < existingPost.size(); i++) {
					posts.add(existingPost.get(i));
				}
			}

			return ResponseEntity.status(202).body(posts);

	}
	
	
	public ResponseEntity<Set<Post>> postsFavoritesTagName(String tagName) {
		
		Set<Post> posts = new HashSet<>();
		
			Set<Tag> tagFavorites = repositoryT.findAllByTagNameContainingIgnoreCase(tagName);

			for (Tag tags : tagFavorites) {
				List<Post> existingPost = repositoryP.findAllByTagRelation(tags);

				for (int i = 0; i < existingPost.size(); i++) {
					posts.add(existingPost.get(i));
				}
			}

			return ResponseEntity.status(202).body(posts);

	}
	
	
	
	
	

	/**
	 * MÃ©todo para cadastrar postagens caso nÃ£o haja alguma com o mesmo tÃ­tulo,
	 * caso haja nÃ£o Ã© cadastrado
	 * 
	 * @param idUsuario
	 * @param idTema
	 * @param novaPostagem
	 * @author Antonio
	 * @author Matheus
	 * @returnum lista de postagens cadastradas ou o erro no cadastro
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Post> registerPost(Long idUser, String tagName, Post newPost) {
		if (tagName.contains("{") || tagName.contains("}") || tagName.contains("/") || tagName.contains("\\")
				|| tagName.contains("%") || tagName.contains("$") || tagName.contains("&") || tagName.contains("*")
				|| tagName.contains("|") || tagName.contains("@") || tagName.contains("*") || tagName.contains("(")
				|| tagName.contains(")") || tagName.contains("§")) {
			return ResponseEntity.status(303).build();
		}

		if (newPost.getTitle() == null) {
			return ResponseEntity.status(403).build();
		}
		if (newPost.getDescription() == null) {
			return ResponseEntity.status(405).build();
		}

		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Tag> existingTheme = repositoryT.findByTagName(tagName);

			if (existingTheme.isEmpty()) {
				Tag novoTema = new Tag();
				novoTema.setTagName(tagName);
				repositoryT.save(novoTema);
				newPost.getTagRelation().add(novoTema);
			} else {
				newPost.getTagRelation().add(existingTheme.get());
			}

			newPost.setUserPost(existingUser.get());

			repositoryP.save(newPost);

			return ResponseEntity.status(201).body(newPost);

		}
		return ResponseEntity.status(400).build();

	}

	/**
	 * MÃ©todo para atualizar postagens
	 * 
	 * @param idPostagem   - id da postagem passado pelo controller
	 * @param novaPostagem - dados da postagem para serem atualizados que foram
	 *                     passados pelo controller
	 * @return lista de postagens cadastradas ou o erro na atualização
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Post> updatePost(Long idPost, Post newPost) {
		if (newPost.getTitle() == null) {
			return ResponseEntity.status(403).build();
		}
		if (newPost.getDescription() == null) {
			return ResponseEntity.status(405).build();
		}
		Optional<Post> existingPost = repositoryP.findById(idPost);

		if (existingPost.isPresent()) {

			existingPost.get().setTitle(newPost.getTitle());
			existingPost.get().setDescription(newPost.getDescription());
			existingPost.get().setUrlImage(newPost.getUrlImage());

			repositoryP.save(existingPost.get());
			return ResponseEntity.status(201).body(existingPost.get());

		} else {
			return ResponseEntity.status(400).build();
		}
	}

	/**
	 * Método para adicionar um tema dentro da postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @author Matheus
	 * @return tema adicionado na postagem ou seu respectivo erro
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Post> addTag(Long idPost, String tagName) {
		if (tagName.contains("{") || tagName.contains("}") || tagName.contains("/") || tagName.contains("\\")
				|| tagName.contains("%") || tagName.contains("$") || tagName.contains("&") || tagName.contains("*")
				|| tagName.contains("|") || tagName.contains("@") || tagName.contains("*") || tagName.contains("(")
				|| tagName.contains(")") || tagName.contains("§")) {
			return ResponseEntity.status(403).build();
		}

		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findByTagName(tagName);
			if (existingTag.isEmpty()) {
				Tag newTag = new Tag();
				newTag.setTagName(tagName);
				repositoryT.save(newTag);
				existingPost.get().getTagRelation().add(newTag);
			} else {
				existingPost.get().getTagRelation().add(existingTag.get());
			}

			repositoryP.save(existingPost.get());

			return ResponseEntity.status(201).body(existingPost.get());

		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * Método para deletar um tema de dentro de uma postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @return tema retirado ou seu respectivo erro
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Post> deletePostTheme(Long idPost, Long idTheme) {
		Optional<Tag> existingTheme = repositoryT.findById(idTheme);

		if (existingTheme.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);

			if (existingPost.isPresent()) {
				if (existingPost.get().getTagRelation().contains(existingTheme.get())) {
					existingPost.get().getTagRelation().remove(existingTheme.get());
					repositoryP.save(existingPost.get());

					return ResponseEntity.status(202).body(existingPost.get());
				} else {
					return ResponseEntity.status(404).build();
				}

			}
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * Método retorna o número de likes no post
	 * 
	 * @param idPost
	 * @return retorna o número de likes ou um status 404 caso o post não exista
	 * @author Antonio
	 * @author Bueno
	 */
	/*public ResponseEntity<String> upvotesPost(Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getUpvoted() != null) {
				return ResponseEntity.status(202)
						.body("Número de likes: " + existingPost.get().getUpvoted().getUserUpvote().size());
			}
			return ResponseEntity.status(202).body("Número de likes: 0");

		}
		return ResponseEntity.status(404).build();
	}*/

	/**
	 * Método para retornar o número de denúncias de um post
	 * 
	 * @param idPost
	 * @author Antonio
	 * @author Bueno
	 * @return número de denúncias ou status 404 caso o post não exista
	 */
	/*public ResponseEntity<String> reportsPosts(Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getReported() != null) {
				return ResponseEntity.status(202)
						.body("Número de denúncias: " + existingPost.get().getReported().getUserReport().size());
			}
			return ResponseEntity.status(202).body("Número de denúncias: 0");

		}
		return ResponseEntity.status(404).build();
	}*/

	// ----------------------- TEMAS FAVORITOS -----------------------

	/**
	 * Método para adicionar tag favorita do Usuário.
	 * 
	 * @param idUser  - Long
	 * @param tagName - String
	 * @author Antonio
	 * @author Chelle
	 * @since 1.0
	 * @return tag adicionada ou seu respectivo erro
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Usuario> addFavoriteTag(Long idUser, String tagName) {
		if (tagName.contains("{") || tagName.contains("}") || tagName.contains("/") || tagName.contains("\\")
				|| tagName.contains("%") || tagName.contains("$") || tagName.contains("&") || tagName.contains("*")
				|| tagName.contains("|") || tagName.contains("@") || tagName.contains("*") || tagName.contains("(")
				|| tagName.contains(")") || tagName.contains("§")) {
			return ResponseEntity.status(403).build();
		}

		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findByTagName(tagName);

			if (existingTag.isPresent()) {
				existingUser.get().getFavorites().add(existingTag.get());
				repositoryU.save(existingUser.get());
			} else {
				Tag newTag = new Tag();

				newTag.setTagName(tagName);
				repositoryT.save(newTag);
				existingUser.get().getFavorites().add(newTag);
				repositoryU.save(existingUser.get());
			}
			return ResponseEntity.status(201).body(existingUser.get());
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * Método para deletar uma tag favorita do Usuário.
	 * 
	 * @param idUser - Long
	 * @param idTag  - Long
	 * @author Antonio
	 * @author Chelle
	 * @since 1.0
	 * @return tag deletada ou seu respectivo erro
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Object> deleteFavoriteTag(Long idUser, Long idTag) {

		Optional<Usuario> existingUser = repositoryU.findById(idUser);
		if (existingUser.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findById(idTag);
			if (existingTag.isPresent()) {
				if (existingUser.get().getFavorites().contains(existingTag.get())) {
					existingUser.get().getFavorites().remove(existingTag.get());
					repositoryU.save(existingUser.get());
					return ResponseEntity.status(202).body("TEMA FAVORITO DELETADO");
				} else {
					return ResponseEntity.status(200).body("Esse usuário não possui esse tema");
				}
			} else {
				return ResponseEntity.status(404).body("Tema não existe");
			}
		}
		return ResponseEntity.status(400).body("Usuário não existe");
	}

	// ----------------------- COMENTÁRIOS -----------------------

	/**
	 * Método para cadastrar novos comentários
	 * 
	 * @param idUsuario      - usuário que está comentando
	 * @param idPostagem     - postagem que está sendo comentada
	 * @param novoComentario
	 * @author Antonio
	 * @return post com o comentário cadastrado ou seu respectivo erro
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Comment> registerComment(Long idUser, Long idPost, Comment newComment) {
		if (newComment.getText() == null) {
			return ResponseEntity.status(403).build();
		}

		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPost);

			if (postagemExistente.isPresent()) {
				newComment.setPost(postagemExistente.get());
				newComment.setUserComment(existingUser.get());
				repositoryC.save(newComment);
				//return ResponseEntity.status(201).body(repositoryP.findAllByComment(newComment));
				return ResponseEntity.status(201).body(newComment);
			} else {
				ResponseEntity.status(400).build();
			}
		}
		return ResponseEntity.status(404).build();

	}

	/**
	 * Método para atualizar comentários
	 * 
	 * @param idComentario
	 * @param comentarioAtualizado
	 * @author Antonio
	 * @return Optional com o comentário atualizado ou Optional vazio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public ResponseEntity<Comment> updateComment(Long idComment, Comment updatedComment) {
		if (updatedComment.getText() == null) {
			return ResponseEntity.status(403).build();
		}

		Optional<Comment> existingComment = repositoryC.findById(idComment);

		if (existingComment.isPresent()) {
			existingComment.get().setText(updatedComment.getText());

			repositoryC.save(existingComment.get());

			//return ResponseEntity.status(201).body(repositoryP.findAllByComment(existingComment.get()));
			return ResponseEntity.status(201).body(existingComment.get());
		}
		return ResponseEntity.status(404).build();
	}

	/**
	 * Método retorna o número de likes em um comentário
	 * 
	 * @param idComment
	 * @return retorna o número de likes ou status 404
	 * @author Antonio
	 * @author Bueno
	 */
	/*public ResponseEntity<String> upvotesComment(Long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);
		if (existingComment.isPresent()) {
			if (existingComment.get().getUpvoted() != null) {
				return ResponseEntity.status(202)
						.body("Número de upvotes: " + existingComment.get().getUpvoted().getUserUpvote().size());
			}
			return ResponseEntity.status(202).body("Número de upvotes: 0");
		}
		return ResponseEntity.status(404).build();
	}*/

	/**
	 * Método pega o número de denúncias em comentário
	 * 
	 * @param idComment
	 * @return retorna número de denúncias ou status 404
	 * @author Antonio
	 * @author Bueno
	 */
	/*public ResponseEntity<String> reportsComments(Long idComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);
		if (existingComment.isPresent()) {
			if (existingComment.get().getReported() != null) {
				return ResponseEntity.status(202)
						.body("Número de denúncias: " + existingComment.get().getReported().getUserReport().size());
			}
			return ResponseEntity.status(202).body("Número de denúncias: 0");

		}
		return ResponseEntity.status(404).build();
	}*/

	// ----------------------- DENÚNCIAS -----------------------

	/**
	 * Método para denunciar uma postagem
	 * 
	 * @param idUser
	 * @param idPost
	 * @author Antonio
	 * @return postagem denunciada ou seu respectivo erro
	 * @redactor Amanda
	 */
	public ResponseEntity<Post> reportPost(Long idUser, Long idPost) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);
			if (existingPost.isPresent()) {

					if (existingPost.get().getUserReportPost().contains(existingUser.get())) {
						existingPost.get().getUserReportPost().remove(existingUser.get());
						existingUser.get().getReportPost().remove(existingPost.get());
					} else {
						existingPost.get().getUserReportPost().add(existingUser.get());
						existingUser.get().getReportPost().add(existingPost.get());
						
					}
					
				repositoryU.save(existingUser.get());
				repositoryP.save(existingPost.get());

				return ResponseEntity.status(201).body(existingPost.get());
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * Método para denunciar um comentário
	 * 
	 * @param idUser
	 * @param idComment
	 * @author Antonio
	 * @return comentário denunciado ou seu respectivo erro
	 * @redactor Amanda
	 */
	public ResponseEntity<Comment> reportComment(Long idUser, Long idComment) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);
			if (existingComment.isPresent()) {

					if (existingComment.get().getUserReportComment().contains(existingUser.get())) {
						existingComment.get().getUserReportComment().remove(existingUser.get());
						existingUser.get().getReportComment().remove(existingComment.get());
					} else {
						existingComment.get().getUserReportComment().add(existingUser.get());
						existingUser.get().getReportComment().add(existingComment.get());
						
					}
					
				repositoryU.save(existingUser.get());
				repositoryC.save(existingComment.get());

				return ResponseEntity.status(201).body(existingComment.get());
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * Método para retirar uma denúncia de comentário ou postagem
	 * 
	 * @param idReport
	 * @param idUser
	 * @author Antonio
	 * @return denúncia retirada ou seu respectivo erro
	 * @redactor Amanda
	 */
	/*public ResponseEntity<Object> deleteReport(Long idReport, Long idUser) {
		Optional<Report> existingReport = repositoryR.findById(idReport);

		if (existingReport.isPresent()) {
			Optional<Usuario> existingUser = repositoryU.findById(idUser);

			if (existingUser.isPresent()) {

				if (existingReport.get().getUserReport().contains(existingUser.get())) {
					existingReport.get().getUserReport().remove(existingUser.get());

					if (existingReport.get().getUserReport().isEmpty()) {
						if (existingReport.get().getPostReport() != null) {
							Optional<Post> existingPost = repositoryP
									.findById(existingReport.get().getPostReport().getIdPost());
							existingPost.get().setReported(null);
							existingReport.get().setPostReport(null);
							repositoryP.save(existingPost.get());
						} else if (existingReport.get().getCommentReport() != null) {
							Optional<Comment> existingComment = repositoryC
									.findById(existingReport.get().getCommentReport().getIdComment());

							existingComment.get().setReported(null);
							existingReport.get().setCommentReport(null);
							repositoryC.save(existingComment.get());
						}

						repositoryR.deleteById(idReport);
					} else {
						repositoryR.save(existingReport.get());
					}

					return ResponseEntity.status(202).body("DENÚNCIA RETIRADA");
				}
			}
			return ResponseEntity.status(400).body("Esse usuário não existe");

		}
		return ResponseEntity.status(400).body("Essa denúncia não existe");
	}*/

	// ----------------------- LIKES -----------------------
	/**
	 * método para curtir um post
	 * 
	 * @param idUser
	 * @param idPost
	 * @author Antonio
	 * @return postagem curtida ou respectivo erro
	 * @redactor Amanda
	 */
	public ResponseEntity<Post> upvotePost(Long idUser, Long idPost) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);
			if (existingPost.isPresent()) {

				

					if (existingPost.get().getUserUpvotePost().contains(existingUser.get())) {
						existingPost.get().getUserUpvotePost().remove(existingUser.get());
						existingUser.get().getUpvotePost().remove(existingPost.get());
					} else {
						existingPost.get().getUserUpvotePost().add(existingUser.get());
						existingUser.get().getUpvotePost().add(existingPost.get());
						
					}
					
				repositoryU.save(existingUser.get());
				repositoryP.save(existingPost.get());

				return ResponseEntity.status(201).body(existingPost.get());
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * método para curtir um comentário
	 * 
	 * @param idUser
	 * @param idComment
	 * @author Antonio
	 * @return comentário curtido ou seu respectivo erro
	 * @redactor Amanda
	 */
	public ResponseEntity<Comment> upvoteComment(Long idUser, Long idComment) {
		Optional<Usuario> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);
			if (existingComment.isPresent()) {

				

					if (existingComment.get().getUserUpvoteComment().contains(existingUser.get())) {
						existingComment.get().getUserUpvoteComment().remove(existingUser.get());
						existingUser.get().getUpvoteComment().remove(existingComment.get());
					} else {
						existingComment.get().getUserUpvoteComment().add(existingUser.get());
						existingUser.get().getUpvoteComment().add(existingComment.get());
						
					}
					
				repositoryU.save(existingUser.get());
				repositoryC.save(existingComment.get());

				return ResponseEntity.status(201).body(existingComment.get());
			}
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.status(400).build();
	}

	/**
	 * método para descurtir um post ou comentário
	 * 
	 * @param idLike
	 * @param idUser
	 * @author Antonio
	 * @return postagem ou comentário descurtido ou seu respectivo erro
	 * @redactor Amanda
	 */
	/*public ResponseEntity<Object> unupvote(Long idUpvote, Long idUser) {
		Optional<Upvote> existingUpvote = repositoryL.findById(idUpvote);

		if (existingUpvote.isPresent()) {
			Optional<Usuario> existingUser = repositoryU.findById(idUser);

			if (existingUser.isPresent()) {
				if (existingUpvote.get().getUserUpvote().contains(existingUser.get())) {
					existingUpvote.get().getUserUpvote().remove(existingUser.get());

					if (existingUpvote.get().getUserUpvote().isEmpty()) {
						if (existingUpvote.get().getPostUpvote() != null) {
							Optional<Post> existingPost = repositoryP
									.findById(existingUpvote.get().getPostUpvote().getIdPost());

							existingPost.get().setUpvoted(null);
							existingUpvote.get().setPostUpvote(null);

							repositoryP.save(existingPost.get());
						} else if (existingUpvote.get().getCommentUpvote() != null) {
							Optional<Comment> existingComment = repositoryC
									.findById(existingUpvote.get().getCommentUpvote().getIdComment());

							existingComment.get().setUpvoted(null);
							existingUpvote.get().setCommentUpvote(null);

							repositoryC.save(existingComment.get());
						}
						repositoryL.deleteById(idUpvote);
					} else {
						repositoryL.save(existingUpvote.get());
					}
					return ResponseEntity.status(202).body("CURTIDA RETIRADA");
				}
			}
			return ResponseEntity.status(400).body("Esse usuário não existe");
		}
		return ResponseEntity.status(400).body("Esse like não existe");
	}*/
}
