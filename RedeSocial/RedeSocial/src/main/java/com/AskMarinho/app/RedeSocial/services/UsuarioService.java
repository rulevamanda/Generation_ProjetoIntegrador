package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

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
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {
		Optional<Usuario> emailExistente = repository.findByEmail(novoUsuario.getEmail());

		if (emailExistente.isEmpty()) {
			Optional<Usuario> usuarioExistente = repository.findByNomeUsuario(novoUsuario.getNomeUsuario());

			if (usuarioExistente.isEmpty()) {
				return Optional.ofNullable(repository.save(novoUsuario));
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
	 * @return Retorna um Optional para salvar as novas alterações caso o usuário
	 *         seja encontrado, senão retorna um Optional vazio.
	 */
	public Optional<Usuario> atualizarUsuario(Long id_usuario, Usuario atualizacaoUsuario) {
		Boolean atualizar = false;
		Optional<Usuario> usuarioExistente = repository.findById(id_usuario);

		if (usuarioExistente.isPresent()) {
			Optional<Usuario> emailExistente = repository.findByEmail(atualizacaoUsuario.getEmail());

			if (emailExistente.isEmpty()) {

				Optional<Usuario> usuarioRepetido = repository.findByNomeUsuario(atualizacaoUsuario.getNomeUsuario());
				if (usuarioRepetido.isEmpty()) {

					atualizar = true;

				} else {

					if (atualizacaoUsuario.getNomeUsuario().equals(usuarioExistente.get().getNomeUsuario())) {
						atualizar = true;
					}

				}

			} else {
				if (atualizacaoUsuario.getEmail().equals(usuarioExistente.get().getEmail())) {

					Optional<Usuario> usuarioRepetido = repository
							.findByNomeUsuario(atualizacaoUsuario.getNomeUsuario());
					if (usuarioRepetido.isEmpty()) {

						atualizar = true;

					} else {

						if (atualizacaoUsuario.getNomeUsuario().equals(usuarioExistente.get().getNomeUsuario())) {
							atualizar = true;
						}

					}

				}
			}
			if (atualizar) {
				usuarioExistente.get().setNome(atualizacaoUsuario.getNome());
				usuarioExistente.get().setTelefone(atualizacaoUsuario.getTelefone());
				usuarioExistente.get().setSenha(atualizacaoUsuario.getSenha());
				usuarioExistente.get().setGenero(atualizacaoUsuario.getGenero());
				usuarioExistente.get().setNascimento(atualizacaoUsuario.getNascimento());
				usuarioExistente.get().setEmail(atualizacaoUsuario.getEmail());
				usuarioExistente.get().setNomeUsuario(atualizacaoUsuario.getNomeUsuario());

				return Optional.ofNullable(repository.save(usuarioExistente.get()));
			} else {
				return Optional.empty();
			}

		}
		return Optional.empty();

	}
}
