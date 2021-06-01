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
	 * Método para cadastrar um novo usuário onde verifica se o email informado já foi cadastrado ou não.
	 * @param novoEmail - String representando a entidade Usuario
	 * @return Retorna um Optional vazio caso o email já esteja registrado no database, senão
	 * retorna um Optional que salva o novo usuário.
	 * @author Chelle
	 * @author Amanda
	 * @since 1.0
	 */
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {
		Optional<Object> emailExistente = repository.findByEmail(novoUsuario.getEmail());

		if (emailExistente.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(repository.save(novoUsuario));
		}
	}
	/**
	 * Método para atualizar um usuário existente com base em verificação de Id.
	 * @param id_usuario - Long
	 * @param updateUsuario - String
	 * @since 1.0
	 * @author Chelle
	 * @author Amanda
	 * @return Retorna um Optional para salvar as novas alterações caso o usuário seja encontrado, 
	 * senão retorna um Optional vazio.
	 */
	public Optional <Usuario> atualizarUsuario(Long id_usuario, Usuario updateUsuario){
		Optional <Usuario> usuarioExistente = repository.findById(id_usuario);
		
		if (usuarioExistente.isPresent()) {
			usuarioExistente.get().setNome(updateUsuario.getNome());
			usuarioExistente.get().setTelefone(updateUsuario.getTelefone());
			usuarioExistente.get().setSenha(updateUsuario.getSenha());
			usuarioExistente.get().setGenero(updateUsuario.getGenero());
			usuarioExistente.get().setNascimento(updateUsuario.getNascimento());
			return Optional.ofNullable(repository.save(usuarioExistente.get()));
			
		} else {
			return Optional.empty();
		}
	}
}
