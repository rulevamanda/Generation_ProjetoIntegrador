package com.AskMarinho.app.RedeSocial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AskMarinho.app.RedeSocial.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

}
