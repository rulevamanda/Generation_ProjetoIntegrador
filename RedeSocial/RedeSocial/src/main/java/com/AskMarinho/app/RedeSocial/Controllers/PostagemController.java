package com.AskMarinho.app.RedeSocial.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.Models.Postagem;

@RestController 
@RequestMapping ("/postagens")
@CrossOrigin (origins = "*", allowedHeaders = "*")

public class PostagemController {

	@Autowired
	private PostagemController repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> todasPostagens () {
		List<Postagem> listaDePostagem = repository.f
		return ResponseEntity.status(200).body(listaDePostagem)
		
	}
	
}
