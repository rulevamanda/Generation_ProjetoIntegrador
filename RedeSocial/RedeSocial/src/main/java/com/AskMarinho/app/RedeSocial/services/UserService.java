package com.AskMarinho.app.RedeSocial.services;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Upvote;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Report;
import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.models.User;
import com.AskMarinho.app.RedeSocial.models.UserLogin;
import com.AskMarinho.app.RedeSocial.repositories.CommentRepository;
import com.AskMarinho.app.RedeSocial.repositories.UpvoteRepository;
import com.AskMarinho.app.RedeSocial.repositories.PostRepository;
import com.AskMarinho.app.RedeSocial.repositories.ReportRepository;
import com.AskMarinho.app.RedeSocial.repositories.TagRepository;
import com.AskMarinho.app.RedeSocial.repositories.UserRepository;

@Service
public class UserService {

	private @Autowired UserRepository repositoryU;

	private @Autowired TagRepository repositoryT;

	private @Autowired PostRepository repositoryP;

	private @Autowired CommentRepository repositoryC;

	private @Autowired ReportRepository repositoryR;

	private @Autowired UpvoteRepository repositoryL;

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
	public ResponseEntity<Object> registerUser(User newUser) {
		Optional<User> existingEmal = repositoryU.findByEmail(newUser.getEmail());

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String passwordEncoder = encoder.encode(newUser.getPassword());
		newUser.setPassword(passwordEncoder);

		if (existingEmal.isEmpty()) {
			Optional<User> existingUser = repositoryU.findByUserName(newUser.getUserName());

			if (existingUser.isEmpty()) {
				repositoryU.save(newUser);
				return ResponseEntity.status(201).body("Usuario: " + newUser.getUserName() + "\nEmail: "
						+ newUser.getEmail() + "\nUSUÁRIO CADASTRADO");
			} else {
				return ResponseEntity.status(200).body("Já existe um usuário com esse nome");
			}

		} else {
			return ResponseEntity.status(200).body("Já existe um usuário com esse email");
		}
	}

	/**
	 * Método de login
	 * 
	 * @param newUser
	 * @return Usuário logado com seu token ou erro respectivo
	 * @author Bueno
	 */
	public Optional<UserLogin> login(Optional<UserLogin> newUser) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> user = repositoryU.findByEmail(newUser.get().getEmail());

		if (user.isPresent()) {
			if (encoder.matches(newUser.get().getPassword(), user.get().getPassword())) {
				String auth = newUser.get().getEmail() + ":" + newUser.get().getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

				String authHeader = "Basic " + new String(encodedAuth);

				newUser.get().setToken(authHeader);
				newUser.get().setName(user.get().getName());

				return newUser;
			}
		}
		return null;
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
	public ResponseEntity<Object> updateUser(Long id_user, User updatedUser) {
		Boolean update = false;
		Optional<User> existingUser = repositoryU.findById(id_user);

		if (existingUser.isPresent()) {
			Optional<User> emailExistente = repositoryU.findByEmail(updatedUser.getEmail());

			if (emailExistente.isEmpty()) {

				Optional<User> usuarioRepetido = repositoryU.findByUserName(updatedUser.getUserName());
				if (usuarioRepetido.isEmpty()) {

					update = true;

				} else {

					if (updatedUser.getUserName().equals(existingUser.get().getUserName())) {
						update = true;
					} else {
						return ResponseEntity.status(200).body("Já estão usando esse nome de usuário");
					}

				}

			} else {
				if (updatedUser.getEmail().equals(existingUser.get().getEmail())) {

					Optional<User> usuarioRepetido = repositoryU.findByUserName(updatedUser.getUserName());
					if (usuarioRepetido.isEmpty()) {

						update = true;

					} else {

						if (updatedUser.getUserName().equals(existingUser.get().getUserName())) {
							update = true;
						} else {
							return ResponseEntity.status(200).body("Já estão usando esse nome de usuário");
						}

					}

				} else {
					return ResponseEntity.status(200).body("Já estão usando esse email");
				}
			}
			if (update) {
				existingUser.get().setName(updatedUser.getName());
				existingUser.get().setTelephone(updatedUser.getTelephone());
				existingUser.get().setPassword(updatedUser.getPassword());
				existingUser.get().setGender(updatedUser.getGender());
				existingUser.get().setBirth(updatedUser.getBirth());
				existingUser.get().setEmail(updatedUser.getEmail());
				existingUser.get().setUserName(updatedUser.getUserName());

				repositoryU.save(existingUser.get());
				return ResponseEntity.status(201).body("Usuário: " + updatedUser.getUserName() + "\nEmail: "
						+ updatedUser.getEmail() + "\nUSUÁRIO ATUALIZADO");
			}

		}
		return ResponseEntity.status(200).body("Esse usuário não existe");

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
		Optional<User> existingUser = repositoryU.findById(idUser);
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
	public ResponseEntity<Object> registerPost(Long idUser, String themeName, Post newPost) {

		Optional<Post> existingPost = repositoryP.findByTitle(newPost.getTitle());

		if (existingPost.isEmpty()) {
			Optional<User> existingUser = repositoryU.findById(idUser);

			if (existingUser.isPresent()) {
				Optional<Tag> existingTheme = repositoryT.findByTagName(themeName);

				if (existingTheme.isEmpty()) {
					Tag novoTema = new Tag();
					novoTema.setTagName(themeName);
					repositoryT.save(novoTema);
					newPost.getTagRelation().add(novoTema);
				} else {
					newPost.getTagRelation().add(existingTheme.get());
				}

				newPost.setUserPost(existingUser.get());

				repositoryP.save(newPost);

				return ResponseEntity.status(201).body(repositoryP.findAll());

			}
			return ResponseEntity.status(200).body("Esse usuário não existe");

		}

		return ResponseEntity.status(200).body("Já existe uma postagem com esse nome");
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
	public ResponseEntity<Object> updatePost(Long idPost, Post newPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);

		if (existingPost.isPresent()) {
			Optional<Post> existingTitle = repositoryP.findByTitle(newPost.getTitle());

			if (existingTitle.isEmpty()) {
				existingPost.get().setTitle(newPost.getTitle());
				existingPost.get().setDescription(newPost.getDescription());
				existingPost.get().setUrlImage(newPost.getUrlImage());

				repositoryP.save(existingPost.get());
				return ResponseEntity.status(201).body(repositoryP.findAll());
			} else {
				return ResponseEntity.status(200).body("Esse título já existe");
			}
		} else {
			return ResponseEntity.status(200).body("Essa postagem não existe");
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
	public ResponseEntity<Object> addTag(Long idPost, String tagName) {

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

			return ResponseEntity.status(201).body("TEMA ADICIONADO");

		}
		return ResponseEntity.status(200).body("Essa postagem não existe");
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
	public ResponseEntity<Object> deletePostTheme(Long idPost, Long idTheme) {
		Optional<Tag> existingTheme = repositoryT.findById(idTheme);

		if (existingTheme.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);

			if (existingPost.isPresent()) {
				if (existingPost.get().getTagRelation().contains(existingTheme.get())) {
					existingPost.get().getTagRelation().remove(existingTheme.get());
					repositoryP.save(existingPost.get());

					return ResponseEntity.status(202).body("TEMA RETIRADO");
				} else {
					return ResponseEntity.status(200).body("Essa postagem não tem esse tema");
				}

			}
			return ResponseEntity.status(200).body("Essa postagem não existe");
		}
		return ResponseEntity.status(200).body("Esse tema não existe");
	}

	/**
	 * Método retorna o número de likes no post
	 * 
	 * @param idPost
	 * @return retorna o número de likes ou um status 404 caso o post não exista
	 * @author Antonio
	 * @author Bueno
	 */
	public ResponseEntity<String> upvotesPost(Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getUpvoted() != null) {
				return ResponseEntity.status(202)
						.body("Número de likes: " + existingPost.get().getUpvoted().getUserUpvote().size());
			}
			return ResponseEntity.status(202).body("Número de likes: 0");

		}
		return ResponseEntity.status(404).build();
	}

	/**
	 * Método para retornar o número de denúncias de um post
	 * 
	 * @param idPost
	 * @author Antonio
	 * @author Bueno
	 * @return número de denúncias ou status 404 caso o post não exista
	 */
	public ResponseEntity<String> reportsPosts(Long idPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);
		if (existingPost.isPresent()) {
			if (existingPost.get().getReported() != null) {
				return ResponseEntity.status(202)
						.body("Número de denúncias: " + existingPost.get().getReported().getUserReport().size());
			}
			return ResponseEntity.status(202).body("Número de denúncias: 0");

		}
		return ResponseEntity.status(404).build();
	}

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
	public ResponseEntity<Object> addFavoriteTag(Long idUser, String tagName) {
		Optional<User> existingUser = repositoryU.findById(idUser);

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
			return ResponseEntity.status(201).body("TEMA FAVORITO ADICIONADO");
		}
		return ResponseEntity.status(200).body("Esse usuário não existe");
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

		Optional<User> existingUser = repositoryU.findById(idUser);
		if (existingUser.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findById(idTag);
			if (existingTag.isPresent()) {
				if (existingUser.get().getFavorites().contains(existingTag.get())) {
					existingUser.get().getFavorites().remove(existingTag.get());
					repositoryU.save(existingUser.get());
					return ResponseEntity.status(202).body("TEMA FOVORITO DELETADO");
				} else {
					return ResponseEntity.status(200).body("Esse usuário não possui esse tema");
				}
			} else {
				return ResponseEntity.status(200).body("Tema não existe");
			}
		}
		return ResponseEntity.status(200).body("Usuário não existe");
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
	public ResponseEntity<Object> registerComment(Long idUser, Long idPost, Comment newComment) {

		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPost);

			if (postagemExistente.isPresent()) {
				newComment.setPost(postagemExistente.get());
				newComment.setUserComment(existingUser.get());
				repositoryC.save(newComment);
				return ResponseEntity.status(201).body(repositoryP.findAllByComment(newComment));
			} else {
				ResponseEntity.status(200).body("Essa postagem não existe");
			}
		}
		return ResponseEntity.status(200).body("Esse usuário não existe");

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
	public ResponseEntity<Object> updateComment(Long idComment, Comment updatedComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);

		if (existingComment.isPresent()) {
			existingComment.get().setText(updatedComment.getText());

			repositoryC.save(existingComment.get());

			return ResponseEntity.status(201).body(repositoryP.findAllByComment(updatedComment));
		}
		return ResponseEntity.status(200).body("Esse comentário não existe");
	}

	/**
	 * Método retorna o número de likes em um comentário
	 * 
	 * @param idComment
	 * @return retorna o número de likes ou status 404
	 * @author Antonio
	 * @author Bueno
	 */
	public ResponseEntity<String> upvotesComment(Long idComment) {
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
	 * @return retorna número de denúncias ou status 404
	 * @author Antonio
	 * @author Bueno
	 */
	public ResponseEntity<String> reportsComments(Long idComment) {
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
	public ResponseEntity<Object> reportPost(Long idUser, Long idPost) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);

			if (existingPost.isPresent()) {

				Optional<Report> existingReport = repositoryR.findByPostReport(existingPost.get());

				if (existingReport.isPresent()) {

					if (existingReport.get().getUserReport().contains(existingUser.get())) {
						return ResponseEntity.status(200).body("Esse usuário já denunciou essa postagem");
					} else {
						existingReport.get().getUserReport().add(existingUser.get());

						return ResponseEntity.status(201).body(repositoryR.save(existingReport.get()));
					}
				}

				Report newReport = new Report();

				newReport.getUserReport().add(existingUser.get());
				newReport.setPostReport(existingPost.get());

				repositoryR.save(newReport);
				existingPost.get().setReported(newReport);

				repositoryP.save(existingPost.get());
				return ResponseEntity.status(201).body(newReport);
			}

		}
		return ResponseEntity.status(200).body("Esse usuário não existe");
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
	public ResponseEntity<Object> reportComment(Long idUser, Long idComment) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);

			if (existingComment.isPresent()) {

				Optional<Report> existingReport = repositoryR.findByCommentReport(existingComment.get());

				if (existingReport.isPresent()) {

					if (existingReport.get().getUserReport().contains(existingUser.get())) {
						return ResponseEntity.status(200).body("Esse usuário já denunciou esse comentário");
					} else {
						existingReport.get().getUserReport().add(existingUser.get());

						return ResponseEntity.status(201).body(repositoryR.save(existingReport.get()));
					}
				}
				Report newReport = new Report();

				newReport.getUserReport().add(existingUser.get());
				newReport.setCommentReport(existingComment.get());

				repositoryR.save(newReport);
				existingComment.get().setReported(newReport);

				repositoryC.save(existingComment.get());
				return ResponseEntity.status(201).body(newReport);
			}

		}
		return ResponseEntity.status(200).body("Esse usuário não existe");
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
	public ResponseEntity<Object> deleteReport(Long idReport, Long idUser) {
		Optional<Report> existingReport = repositoryR.findById(idReport);

		if (existingReport.isPresent()) {
			Optional<User> existingUser = repositoryU.findById(idUser);

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
			return ResponseEntity.status(200).body("Esse usuário não existe");

		}
		return ResponseEntity.status(200).body("Essa denúncia não existe");
	}

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
	public ResponseEntity<Object> upvotePost(Long idUser, Long idPost) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);
			if (existingPost.isPresent()) {

				Optional<Upvote> existingUpvote = repositoryL.findByPostUpvote(existingPost.get());

				if (existingUpvote.isPresent()) {

					if (existingUpvote.get().getUserUpvote().contains(existingUser.get())) {
						return ResponseEntity.status(200).body("Esse usuário já curtiu essa postagem");
					} else {
						existingUpvote.get().getUserUpvote().add(existingUser.get());

						return ResponseEntity.status(201).body(repositoryL.save(existingUpvote.get()));
					}
				}
				Upvote newUpvote = new Upvote();

				newUpvote.getUserUpvote().add(existingUser.get());
				newUpvote.setPostUpvote(existingPost.get());

				repositoryL.save(newUpvote);
				existingPost.get().setUpvoted(newUpvote);

				repositoryP.save(existingPost.get());

				return ResponseEntity.status(201).body(newUpvote);
			}
			return ResponseEntity.status(200).body("Essa postagem não existe");
		}
		return ResponseEntity.status(200).body("Esse usuário não existe");
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
	public ResponseEntity<Object> upvoteComment(Long idUser, Long idComment) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);
			if (existingComment.isPresent()) {

				Optional<Upvote> existingUpvote = repositoryL.findByCommentUpvote(existingComment.get());

				if (existingUpvote.isPresent()) {

					if (existingUpvote.get().getUserUpvote().contains(existingUser.get())) {
						return ResponseEntity.status(200).body("Esse usuário já curtiu esse comentário");
					} else {
						existingUpvote.get().getUserUpvote().add(existingUser.get());

						return ResponseEntity.status(201).body(repositoryL.save(existingUpvote.get()));
					}
				}
				Upvote newUpvote = new Upvote();

				newUpvote.getUserUpvote().add(existingUser.get());
				newUpvote.setCommentUpvote(existingComment.get());

				repositoryL.save(newUpvote);
				existingComment.get().setUpvoted(newUpvote);

				repositoryC.save(existingComment.get());
				return ResponseEntity.status(201).body(newUpvote);
			}
			return ResponseEntity.status(200).body("Esse Comentário não existe");
		}
		return ResponseEntity.status(200).body("Esse usuário não existe");
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
	public ResponseEntity<Object> unupvote(Long idUpvote, Long idUser) {
		Optional<Upvote> existingUpvote = repositoryL.findById(idUpvote);

		if (existingUpvote.isPresent()) {
			Optional<User> existingUser = repositoryU.findById(idUser);

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
			return ResponseEntity.status(200).body("Esse usuário não existe");
		}
		return ResponseEntity.status(200).body("Esse like não existe");
	}
}
