package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findAllByNameContainingIgnoreCase(String name);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByUserName(String userName);

}
