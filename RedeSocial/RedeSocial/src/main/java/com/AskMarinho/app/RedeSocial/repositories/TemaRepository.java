package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	public List<Tema> findAllByNomeContainingIgnoreCase(String nome);
	
	public Optional <Tema> findByNome(String nome);
}