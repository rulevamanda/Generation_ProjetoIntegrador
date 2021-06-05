package com.AskMarinho.app.RedeSocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Tag;

@Repository
public interface TemaRepository extends JpaRepository<Tag, Long> {

	public List<Tag> findAllByTagNameContainingIgnoreCase(String tagName);

	public Optional<Tag> findByTagName(String tagName);
}
