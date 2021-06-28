package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findAllByNameContainingIgnoreCase(String name);

	public Optional<Usuario> findByEmail(String email);

	public Optional<Usuario> findByUserName(String userName);

}
