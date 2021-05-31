package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.AskMarinho.app.RedeSocial.models.Tema;


public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	public List<Tema> findAllByNomeContainingIgnoreCase(String nome);
}
