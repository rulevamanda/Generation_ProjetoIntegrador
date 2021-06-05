package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Tag;
import com.AskMarinho.app.RedeSocial.repositories.TemaRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {

	@Autowired
<<<<<<< HEAD
	private TemaRepository repositoryT;

=======
	private TemaService serviceT;
	
	
	/**
	 * Método para buscar todos os temas
	 * 
	 * @return retorna todos os temas cadastrados
	 * @author Matheus
	 */
>>>>>>> a979fa9b8205ad79f62180e0aed913301086688f
	@GetMapping("/todos")
	public ResponseEntity<List<Tag>> getAll() {
		return ResponseEntity.status(200).body(repositoryT.findAll());
	}
<<<<<<< HEAD

=======
	
	
	/**
	 * Método para buscar tema através do id
	 * 
	 * @param id - id do tema.
	 * @return retorna o tema referenciado pelo id com status 200 ou status 404 com build vazia.
	 * @author Matheus
	 */
>>>>>>> a979fa9b8205ad79f62180e0aed913301086688f
	@GetMapping("/id/{id}")
	public ResponseEntity<Tag> getById(@PathVariable long id) {
		return repositoryT.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
<<<<<<< HEAD

=======
	
	
	
>>>>>>> a979fa9b8205ad79f62180e0aed913301086688f
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tag>> getByName(@PathVariable String nome) {
		return ResponseEntity.ok(repositoryT.findAllByTagNameContainingIgnoreCase(nome));
	}

}


