package com.AskMarinho.app.RedeSocial.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	public Set<Tag> findAllByTagNameContainingIgnoreCase(String tagName);

	public Optional<Tag> findByTagName(String tagName);
}
