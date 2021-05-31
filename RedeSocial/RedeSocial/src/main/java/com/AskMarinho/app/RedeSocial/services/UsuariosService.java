package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.AskMarinho.app.RedeSocial.models.UsuariosModel;
import com.AskMarinho.app.RedeSocial.repositories.UsuariosRepository;

public class UsuariosService {
	
	@Autowired
	private UsuariosRepository repository;
	
	public Optional<Object> cadastrarUsuario(UsuariosModel novoUsuario, UsuariosModel novoEmail) {
		Optional<Object> EmailExistente = repository.findByEmail(novoEmail.getEmail());
		Optional<Object> UsernameExistente = repository.findByUsername(novoUsuario.getNome_usuario());
		
		if (EmailExistente.isPresent() && UsernameExistente.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(repository.save(novoUsuario));
		}
	}
	
	public Optional <UsuariosModel> atualizarUsuario(UsuariosModel id_usuario, UsuariosModel updateUsuario){
		Optional <UsuariosModel> usuarioExistente = repository.findById(id_usuario);
		if (usuarioExistente.isPresent()) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(repository.save(updateUsuario));
		}
	}

}
