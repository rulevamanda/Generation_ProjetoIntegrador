package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
 
	public List<Postagem>findAllByTituloContainingIgnoreCase (String titulo);
	
	public Optional<Postagem> findByTitulo (String titulo);

}
