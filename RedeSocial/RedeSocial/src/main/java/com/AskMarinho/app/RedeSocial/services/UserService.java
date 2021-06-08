package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Like;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Report;
import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.models.User;
import com.AskMarinho.app.RedeSocial.repositories.CommentRepository;
import com.AskMarinho.app.RedeSocial.repositories.LikeRepository;
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

	private @Autowired LikeRepository repositoryL;

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
	 * @since 1.0
	 */
	public Optional<Object> cadastrarUsuario(User novoUsuario) {
		Optional<User> emailExistente = repositoryU.findByEmail(novoUsuario.getEmail());

		if (emailExistente.isEmpty()) {
			Optional<User> usuarioExistente = repositoryU.findByUserName(novoUsuario.getUserName());

			if (usuarioExistente.isEmpty()) {
				return Optional.ofNullable(repositoryU.save(novoUsuario));
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
	 */
	public Optional<User> atualizarUsuario(Long id_usuario, User atualizacaoUsuario) {
		Boolean atualizar = false;
		Optional<User> usuarioExistente = repositoryU.findById(id_usuario);

		if (usuarioExistente.isPresent()) {
			Optional<User> emailExistente = repositoryU.findByEmail(atualizacaoUsuario.getEmail());

			if (emailExistente.isEmpty()) {

				Optional<User> usuarioRepetido = repositoryU.findByUserName(atualizacaoUsuario.getUserName());
				if (usuarioRepetido.isEmpty()) {

					atualizar = true;

				} else {

					if (atualizacaoUsuario.getUserName().equals(usuarioExistente.get().getUserName())) {
						atualizar = true;
					}

				}

			} else {
				if (atualizacaoUsuario.getEmail().equals(usuarioExistente.get().getEmail())) {

					Optional<User> usuarioRepetido = repositoryU.findByUserName(atualizacaoUsuario.getUserName());
					if (usuarioRepetido.isEmpty()) {

						atualizar = true;

					} else {

						if (atualizacaoUsuario.getUserName().equals(usuarioExistente.get().getUserName())) {
							atualizar = true;
						}

					}

				}
			}
			if (atualizar) {
				usuarioExistente.get().setName(atualizacaoUsuario.getName());
				usuarioExistente.get().setTelephone(atualizacaoUsuario.getTelephone());
				usuarioExistente.get().setPassword(atualizacaoUsuario.getPassword());
				usuarioExistente.get().setGender(atualizacaoUsuario.getGender());
				usuarioExistente.get().setBirth(atualizacaoUsuario.getBirth());
				usuarioExistente.get().setEmail(atualizacaoUsuario.getEmail());
				usuarioExistente.get().setUserName(atualizacaoUsuario.getUserName());

				return Optional.ofNullable(repositoryU.save(usuarioExistente.get()));
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
	 */
	public Optional<Object> cadastrarPostagem(Long idUsuario, String nomeTema, Post novaPostagem) {

		Optional<Post> postagemExistente = repositoryP.findByTitle(novaPostagem.getTitle());

		if (postagemExistente.isEmpty()) {
			Optional<User> usuarioExistente = repositoryU.findById(idUsuario);

			if (usuarioExistente.isPresent()) {
				Optional<Tag> temaExistente = repositoryT.findByTagName(nomeTema);

				if (temaExistente.isEmpty()) {
					Tag novoTema = new Tag();
					novoTema.setTagName(nomeTema);
					repositoryT.save(novoTema);
					novaPostagem.getTagRelation().add(novoTema);
				} else {
					novaPostagem.getTagRelation().add(temaExistente.get());
				}

				novaPostagem.setUserPost(usuarioExistente.get());

				return Optional.ofNullable(repositoryP.save(novaPostagem));

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
	 */
	public Optional<Object> atualizarPostagem(Long idPostagem, Post novaPostagem) {
		Optional<Post> postagemExistente = repositoryP.findById(idPostagem);

		if (postagemExistente.isPresent()) {
			Optional<Post> tituloExistente = repositoryP.findByTitle(novaPostagem.getTitle());

			if (tituloExistente.isEmpty()) {
				postagemExistente.get().setTitle(novaPostagem.getTitle());
				postagemExistente.get().setDescription(novaPostagem.getDescription());
				postagemExistente.get().setUrlImage(novaPostagem.getUrlImage());

				return Optional.ofNullable(repositoryP.save(postagemExistente.get()));
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
	 */
	public Optional<Object> deletarTemaDaPostagem(Long idPostagem, Long idTema) {
		Optional<Tag> temaExistente = repositoryT.findById(idTema);

		if (temaExistente.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPostagem);

			if (postagemExistente.isPresent()) {
				if (postagemExistente.get().getTagRelation().contains(temaExistente.get())) {
					postagemExistente.get().getTagRelation().remove(temaExistente.get());

					return Optional.ofNullable(repositoryP.save(postagemExistente.get()));
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
	 */
	public Optional<Object> cadastrarComentario(Long idUsuario, Long idPostagem, Comment novoComentario) {

		Optional<User> usuarioExistente = repositoryU.findById(idUsuario);

		if (usuarioExistente.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPostagem);

			if (postagemExistente.isPresent()) {
				novoComentario.setPost(postagemExistente.get());
				novoComentario.setUserComment(usuarioExistente.get());
				return Optional.ofNullable(repositoryC.save(novoComentario));
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
	 */
	public Optional<Object> atualizarComentario(Long idComentario, Comment comentarioAtualizado) {
		Optional<Comment> comentarioExistente = repositoryC.findById(idComentario);

		if (comentarioExistente.isPresent()) {
			comentarioExistente.get().setText(comentarioAtualizado.getText());

			return Optional.ofNullable(repositoryC.save(comentarioExistente.get()));
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
	 */
	public Optional<Object> likePost(Long idUser, Long idPost) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Post> existingPost = repositoryP.findById(idPost);
			if (existingPost.isPresent()) {

				Optional<Like> existingLike = repositoryL.findByPostUpvote(existingPost.get());

				if (existingLike.isPresent()) {

					if (existingLike.get().getUserLike().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingLike.get().getUserLike().add(existingUser.get());

						return Optional.ofNullable(repositoryL.save(existingLike.get()));
					}
				}
				Like newLike = new Like();

				newLike.getUserLike().add(existingUser.get());
				newLike.setPostUpvote(existingPost.get());

				repositoryL.save(newLike);
				existingPost.get().setLiked(newLike);

				repositoryP.save(existingPost.get());
				return Optional.ofNullable(newLike);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param idUser
	 * @param idComment
	 * @return
	 */
	public Optional<Object> likeComment(Long idUser, Long idComment) {
		Optional<User> existingUser = repositoryU.findById(idUser);

		if (existingUser.isPresent()) {
			Optional<Comment> existingComment = repositoryC.findById(idComment);
			if (existingComment.isPresent()) {

				Optional<Like> existingLike = repositoryL.findByCommentUpvote(existingComment.get());

				if (existingLike.isPresent()) {

					if (existingLike.get().getUserLike().contains(existingUser.get())) {
						return Optional.empty();
					} else {
						existingLike.get().getUserLike().add(existingUser.get());

						return Optional.ofNullable(repositoryL.save(existingLike.get()));
					}
				}
				Like newLike = new Like();

				newLike.getUserLike().add(existingUser.get());
				newLike.setCommentUpvote(existingComment.get());

				repositoryL.save(newLike);
				existingComment.get().setLiked(newLike);

				repositoryC.save(existingComment.get());
				return Optional.ofNullable(newLike);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param idLike
	 * @param idUser
	 * @return
	 */
	public Optional<Object> unlike(Long idLike, Long idUser) {
		Optional<Like> existingLike = repositoryL.findById(idLike);

		if (existingLike.isPresent()) {
			Optional<User> existingUser = repositoryU.findById(idUser);

			if (existingUser.isPresent()) {
				if (existingLike.get().getUserLike().contains(existingUser.get())) {
					existingLike.get().getUserLike().remove(existingUser.get());

					if (existingLike.get().getUserLike().isEmpty()) {
						if (existingLike.get().getPostUpvote() != null) {
							Optional<Post> existingPost = repositoryP
									.findById(existingLike.get().getPostUpvote().getIdPost());

							existingPost.get().setLiked(null);
							existingLike.get().setPostUpvote(null);

							repositoryP.save(existingPost.get());
						} else if (existingLike.get().getCommentUpvote() != null) {
							Optional<Comment> existingComment = repositoryC
									.findById(existingLike.get().getCommentUpvote().getIdComment());

							existingComment.get().setLiked(null);
							existingLike.get().setCommentUpvote(null);
							
							repositoryC.save(existingComment.get());
						}
						repositoryL.deleteById(idLike);
					} else {
						repositoryL.save(existingLike.get());
					}
					return Optional.ofNullable(existingLike.get());
				}
			}
		}
		return Optional.empty();
	}
}
