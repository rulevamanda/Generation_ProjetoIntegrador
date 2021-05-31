package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.UsuariosModel;
import com.AskMarinho.app.RedeSocial.repositories.UsuariosRepository;

@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository repository;
	
	
	/**
	 * Método para cadastrar um novo usuário com verificação de email e username.
	 * @param novoUsuario - Long representa entidade UsuarioModel como novo usuário.
	 * @param novoEmail - String representa entidade UsuarioModel como novo email.
	 * @since 1.0
	 * @return Optional vazio caso contrário salva o novo usuário.
	 */
	public Optional<Object> cadastrarUsuario(UsuariosModel novoUsuario, UsuariosModel novoEmail) {
		Optional<Object> EmailExistente = repository.findByEmail(novoEmail.getEmail());
		Optional<Object> UsernameExistente = repository.findByUsername(novoUsuario.getNome_usuario());
		
		if (EmailExistente.isPresent() && UsernameExistente.isPresent()) {
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
	 * @return Retorna um Optional para salvar as novas alterações caso o usuário seja encontrado, 
	 * senão retorna um Optional vazio.
	 */
	public Optional <UsuariosModel> atualizarUsuario(UsuariosModel id_usuario, UsuariosModel updateUsuario){
		Optional <UsuariosModel> usuarioExistente = repository.findById(updateUsuario.getId_usuario());
		if (usuarioExistente.isPresent()) {
			usuarioExistente.get().setNome(updateUsuario.getNome());
			usuarioExistente.get().setEmail(updateUsuario.getEmail());
			usuarioExistente.get().setTelefone(updateUsuario.getTelefone());
			usuarioExistente.get().setSenha(updateUsuario.getSenha());
			usuarioExistente.get().setBiografia(updateUsuario.getBiografia());
			usuarioExistente.get().setGenero(updateUsuario.getGenero());
			usuarioExistente.get().setNome_usuario(updateUsuario.getNome_usuario());
			usuarioExistente.get().setNascimento(updateUsuario.getNascimento());
			
			return Optional.ofNullable(repository.save(updateUsuario));
					
		} else {
			return Optional.empty();
		}
	}
	
	/**
	 * Método para atualizar somente o Nome de Usuário com verificação para impedir duplicatas.
	 * @param novoUsername - 
	 * @return
	 */
	public Optional <Object> atualizarUsername (UsuariosModel novoUsername){
		Optional <Object> usernameExistente = repository.findByUsername(novoUsername.getNome_usuario());
		
		if (usernameExistente.isEmpty()) {
			((UsuariosModel) usernameExistente.get()).setNome_usuario(novoUsername.getNome_usuario());
			return Optional.ofNullable(repository.save(novoUsername));
		} else {
			return Optional.empty();
					
		}
	}

}
