package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Upvote;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Report;
import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.models.User;
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
	 * @return Retorna um Optional vazio caso o email já esteja registrado no
	 *         database, senão retorna um Optional que salva o novo usuário.
	 * @author Chelle
	 * @author Amanda
	 * @redactor Amanda
	 * @translator Amanda
	 * @since 1.0
	 */
	public Optional<Object> registerUser(User newUser) {
		Optional<User> existingEmal = repositoryU.findByEmail(newUser.getEmail());

		if (existingEmal.isEmpty()) {
			Optional<User> existingUser = repositoryU.findByUserName(newUser.getUserName());

			if (existingUser.isEmpty()) {
				return Optional.ofNullable(repositoryU.save(newUser));
			} else {
				return Optional.empty();
			}

		} else {
			return Optional.empty();
		}
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
	 * @return Retorna um Optional para salvar as novas alterações caso o usuário
	 *         seja encontrado, senão retorna um Optional vazio.
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<User> updateUser(Long id_user, User updatedUser) {
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
						}

					}

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

				return Optional.ofNullable(repositoryU.save(existingUser.get()));
			} else {
				return Optional.empty();
			}

		}
		return Optional.empty();

	}

	// ----------------------- POSTAGENS -----------------------

	/**
	 * MÃ©todo para cadastrar postagens caso nÃ£o haja alguma com o mesmo tÃ­tulo,
	 * caso haja nÃ£o Ã© cadastrado
	 * 
	 * @param idUsuario
	 * @param idTema
	 * @param novaPostagem
	 * @author Antonio
	 * @author Matheus
	 * @returnum Optional com os dados cadastrados da nova postagem ou retorna um
	 *           Optional vazio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> registerPost(Long idUser, String themeName, Post newPost) {

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

				return Optional.ofNullable(repositoryP.save(newPost));

			}

		}

		return Optional.empty();
	}

	/**
	 * MÃ©todo para atualizar postagens
	 * 
	 * @param idPostagem   - id da postagem passado pelo controller
	 * @param novaPostagem - dados da postagem para serem atualizados que foram
	 *                     passados pelo controller
	 * @return retorna um optional com a postagem atualizada ou retorna um empty
	 *         vazio caso a postagem nÃ£o exista
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> updatePost(Long idPost, Post newPost) {
		Optional<Post> existingPost = repositoryP.findById(idPost);

		if (existingPost.isPresent()) {
			Optional<Post> existingTitle = repositoryP.findByTitle(newPost.getTitle());

			if (existingTitle.isEmpty()) {
				existingPost.get().setTitle(newPost.getTitle());
				existingPost.get().setDescription(newPost.getDescription());
				existingPost.get().setUrlImage(newPost.getUrlImage());

				return Optional.ofNullable(repositoryP.save(existingPost.get()));
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Método para adicionar um tema dentro da postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @author Matheus
	 * @return Optional com o tema adicionado ou Optional vazio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> addTag(Long idPost, String tagName) {

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

			return Optional.ofNullable(repositoryP.save(existingPost.get()));

		}
		return Optional.empty();
	}

	/**
	 * Método para deletar um tema de dentro de uma postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @return Optional com a postagem com o tema deletado ou um Optional vazio
	 * @author Antonio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> deletePostTheme(Long idPost, Long idTheme) {
		Optional<Tag> existingTheme = repositoryT.findById(idTheme);

		if (existingTheme.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);

			if (existingPost.isPresent()) {
				if (existingPost.get().getTagRelation().contains(existingTheme.get())) {
					existingPost.get().getTagRelation().remove(existingTheme.get());

					return Optional.ofNullable(repositoryP.save(existingPost.get()));
				}

			}
		}
		return Optional.empty();
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
	 * @return Optional com as mudanças, senão um Optional vazio.
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> addFavoriteTag(Long idUser, String tagName) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findByTagName(tagName);

			if (existingTag.isPresent()) {
				existingUser.get().getFavorites().add(existingTag.get());
				return Optional.ofNullable(repositoryU.save(existingUser.get()));
			} else {
				Tag newTag = new Tag();

				newTag.setTagName(tagName);
				repositoryT.save(newTag);
				existingUser.get().getFavorites().add(newTag);

				return Optional.ofNullable(repositoryU.save(existingUser.get()));
			}
		}
		return Optional.empty();
	}

	/**
	 * Método para deletar uma tag favorita do Usuário.
	 * 
	 * @param idUser - Long
	 * @param idTag  - Long
	 * @author Antonio
	 * @author Chelle
	 * @since 1.0
	 * @return Optional com as mudanças feitas, senão um Optional vazio.
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> deleteFavoriteTag(Long idUser, Long idTag) {

		Optional<User> existingUser = repositoryU.findById(idUser);
		if (existingUser.isPresent()) {
			Optional<Tag> existingTag = repositoryT.findById(idTag);
			if (existingTag.isPresent()) {
				if (existingUser.get().getFavorites().contains(existingTag.get())) {
					existingUser.get().getFavorites().remove(existingTag.get());

					return Optional.ofNullable(repositoryU.save(existingUser.get()));
				}
			}
		}
		return Optional.empty();
	}

	// ----------------------- COMENTÁRIOS -----------------------

	/**
	 * Método para cadastrar novos comentários
	 * 
	 * @param idUsuario      - usuário que está comentando
	 * @param idPostagem     - postagem que está sendo comentada
	 * @param novoComentario
	 * @author Antonio
	 * @return Optional com o comentário cadastrada ou Optional vazio
	 * @redactor Amanda
	 * @translator Amanda
	 */
	public Optional<Object> registerComment(Long idUser, Long idPost, Comment newComment) {

		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPost);

			if (postagemExistente.isPresent()) {
				newComment.setPost(postagemExistente.get());
				newComment.setUserComment(existingUser.get());
				return Optional.ofNullable(repositoryC.save(newComment));
			}
		}

		return Optional.empty();
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
	public Optional<Object> updateComment(Long idComment, Comment updatedComment) {
		Optional<Comment> existingComment = repositoryC.findById(idComment);

		if (existingComment.isPresent()) {
			existingComment.get().setText(updatedComment.getText());

			return Optional.ofNullable(repositoryC.save(existingComment.get()));
		}
		return Optional.empty();
	}

	// ----------------------- DENÚNCIAS -----------------------

	/**
	 * Método para denunciar uma postagem
	 * 
	 * @param idUser
	 * @param idPost
	 * @author Antonio
	 * @return um Optional com a denúncia da postagem ou um Optional vazio
	 * @redactor Amanda
	 */
	public Optional<Object> reportPost(Long idUser, Long idPost) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);

			if (existingPost.isPresent()) {

				Optional<Report> existingReport = repositoryR.findByPostReport(existingPost.get());

				if (existingReport.isPresent()) {

					if (existingReport.get().getUserReport().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingReport.get().getUserReport().add(existingUser.get());

						return Optional.ofNullable(repositoryR.save(existingReport.get()));
					}
				}

				Report newReport = new Report();

				newReport.getUserReport().add(existingUser.get());
				newReport.setPostReport(existingPost.get());

				repositoryR.save(newReport);
				existingPost.get().setReported(newReport);

				repositoryP.save(existingPost.get());
				return Optional.ofNullable(newReport);
			}

		}
		return Optional.empty();
	}

	/**
	 * Método para denunciar um comentário
	 * 
	 * @param idUser
	 * @param idComment
	 * @author Antonio
	 * @return Um Optional com a denúncia do comentário ou um Optional vazio
	 * @redactor Amanda
	 */
	public Optional<Object> reportComment(Long idUser, Long idComment) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);

			if (existingComment.isPresent()) {

				Optional<Report> existingReport = repositoryR.findByCommentReport(existingComment.get());

				if (existingReport.isPresent()) {

					if (existingReport.get().getUserReport().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingReport.get().getUserReport().add(existingUser.get());

						return Optional.ofNullable(repositoryR.save(existingReport.get()));
					}
				}
				Report newReport = new Report();

				newReport.getUserReport().add(existingUser.get());
				newReport.setCommentReport(existingComment.get());

				repositoryR.save(newReport);
				existingComment.get().setReported(newReport);

				repositoryC.save(existingComment.get());
				return Optional.ofNullable(newReport);
			}

		}
		return Optional.empty();
	}

	/**
	 * Método para retirar uma denúncia de comentário ou postagem
	 * 
	 * @param idReport
	 * @param idUser
	 * @author Antonio
	 * @return Optional com a denúncia deletada ou Optional vazio
	 * @redactor Amanda
	 */
	public Optional<Object> deleteReport(Long idReport, Long idUser) {
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

					return Optional.ofNullable(existingReport.get());
				}
			}

		}
		return Optional.empty();
	}

	// ----------------------- LIKES -----------------------
	/**
	 * 
	 * @param idUser
	 * @param idPost
	 * @return
	 * @redactor Amanda
	 */
	public Optional<Object> upvotePost(Long idUser, Long idPost) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);
			if (existingPost.isPresent()) {

				Optional<Upvote> existingUpvote = repositoryL.findByPostUpvote(existingPost.get());

				if (existingUpvote.isPresent()) {

					if (existingUpvote.get().getUserUpvote().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingUpvote.get().getUserUpvote().add(existingUser.get());

						return Optional.ofNullable(repositoryL.save(existingUpvote.get()));
					}
				}
				Upvote newUpvote = new Upvote();

				newUpvote.getUserUpvote().add(existingUser.get());
				newUpvote.setPostUpvote(existingPost.get());

				repositoryL.save(newUpvote);
				existingPost.get().setUpvoted(newUpvote);

				repositoryP.save(existingPost.get());
				return Optional.ofNullable(newUpvote);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param idUser
	 * @param idComment
	 * @return
	 * @redactor Amanda
	 */
	public Optional<Object> upvoteComment(Long idUser, Long idComment) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);
			if (existingComment.isPresent()) {

				Optional<Upvote> existingUpvote = repositoryL.findByCommentUpvote(existingComment.get());

				if (existingUpvote.isPresent()) {

					if (existingUpvote.get().getUserUpvote().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingUpvote.get().getUserUpvote().add(existingUser.get());

						return Optional.ofNullable(repositoryL.save(existingUpvote.get()));
					}
				}
				Upvote newUpvote = new Upvote();

				newUpvote.getUserUpvote().add(existingUser.get());
				newUpvote.setCommentUpvote(existingComment.get());

				repositoryL.save(newUpvote);
				existingComment.get().setUpvoted(newUpvote);

				repositoryC.save(existingComment.get());
				return Optional.ofNullable(newUpvote);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param idLike
	 * @param idUser
	 * @return
	 * @redactor Amanda
	 */
	public Optional<Object> unupvote(Long idUpvote, Long idUser) {
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
					return Optional.ofNullable(existingUpvote.get());
				}
			}
		}
		return Optional.empty();
	}
}
