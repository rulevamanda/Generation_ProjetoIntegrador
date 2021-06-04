package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Comentario;
import com.AskMarinho.app.RedeSocial.models.Postagem;
import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.repositories.ComentarioRepository;
import com.AskMarinho.app.RedeSocial.repositories.PostagemRepository;
import com.AskMarinho.app.RedeSocial.repositories.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository repositoryC;

	@Autowired
	private UsuarioRepository repositoryU;

	@Autowired
	private PostagemRepository repositoryP;

	/**
	 * Método para cadastrar novos usuários
	 * 
	 * @param idUsuario      - usuário que está comentando
	 * @param idPostagem     - postagem que está sendo comentada
	 * @param novoComentario
	 * @author Antonio
	 * @return Optional com o comentário cadastrada ou Optional vazio
	 */
	public Optional<Object> cadastrarComentario(Long idUsuario, Long idPostagem, Comentario novoComentario) {
		// verificar se o usuario e postagem existem
		Optional<Usuario> usuarioExistente = repositoryU.findById(idUsuario);

		if (usuarioExistente.isPresent()) {
			Optional<Postagem> postagemExistente = repositoryP.findById(idPostagem);
			
			if (postagemExistente.isPresent()) {
				novoComentario.setPostagem(postagemExistente.get());
				novoComentario.setUsuario(usuarioExistente.get());
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
	public Optional<Object> atualizarComentario(Long idComentario, Comentario comentarioAtualizado) {
		Optional<Comentario> comentarioExistente = repositoryC.findById(idComentario);

		if (comentarioExistente.isPresent()) {
			comentarioExistente.get().setTexto(comentarioAtualizado.getTexto());

			return Optional.ofNullable(repositoryC.save(comentarioExistente.get()));
		}
		return Optional.empty();
	}
}
