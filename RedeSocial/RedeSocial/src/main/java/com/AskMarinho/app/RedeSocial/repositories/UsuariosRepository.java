package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.AskMarinho.app.RedeSocial.models.UsuariosModel;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Long>{
	public List<UsuariosModel> findAllByNome_UsuarioContainingIgnoreCase (String nome);

	public Optional <Object> findByNameContainingIgnoreCase (String nome);
	
	public Optional <Object> findByUsername (String nome_usuario);
	
	public Optional <Object> findByEmailContainingIgnoreCase (String email);
	
	public Optional <Object> findByTelephoneContaining (Long telefone);
	
	public Optional <Object> findByEmail (String email); //email inteiro

	public Optional<UsuariosModel> findById(UsuariosModel id_usuario);

}