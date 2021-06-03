package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

	public List<Usuario> findAllByNomeContainingIgnoreCase (String nome);
	
	public Optional<Object> findByEmail (String email);
	
	public Optional <Object> findByNomeUsuario (String nomeUsuario);

}
