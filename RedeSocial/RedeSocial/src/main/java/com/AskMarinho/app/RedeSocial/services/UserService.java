package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Comment;
import com.AskMarinho.app.RedeSocial.models.Post;
import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.models.User;
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
				}
				else {
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
	public Optional<Object> adicionarTema(Long idPostagem, Long idTema) {
		Optional<Tag> temaExistente = repositoryT.findById(idTema);
		if (temaExistente.isPresent()) {
			Optional<Post> postagemExistente = repositoryP.findById(idPostagem);
			if (postagemExistente.isPresent()) {
				postagemExistente.get().getTagRelation().add(temaExistente.get());
				return Optional.ofNullable(repositoryP.save(postagemExistente.get()));
			}

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
}
