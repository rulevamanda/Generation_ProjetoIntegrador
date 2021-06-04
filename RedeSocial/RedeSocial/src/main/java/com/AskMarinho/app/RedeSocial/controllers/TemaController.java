package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Tema;
import com.AskMarinho.app.RedeSocial.repositories.TemaRepository;
import com.AskMarinho.app.RedeSocial.services.TemaService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@Autowired
	private TemaService serviceT;
	
	
	/**
	 * Método para buscar todos os temas
	 * 
	 * @return retorna todos os temas cadastrados
	 * @author Matheus
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<Tema>> getAll()
	{
		return ResponseEntity.ok(repository.findAll());
	}
	
	
	/**
	 * Método para buscar tema através do id
	 * 
	 * @param id - id do tema.
	 * @return retorna o tema referenciado pelo id com status 200 ou status 404 com build vazia.
	 * @author Matheus
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id)
	{
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByName(@PathVariable String nome)
	{
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> post(@RequestBody Tema tema)
	{
		return serviceT.cadastrarTema(tema)
				.map(novoTema -> ResponseEntity.status(201).body("Tema: " + tema.getNome() + "\nCADASTRADO"))
				.orElse(ResponseEntity.status(400).body("Erro ao cadastrar. Já existe um tema com esse nome."));
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<String> put(@PathVariable (value = "id") long id, @RequestBody Tema tema)
	{
		return serviceT.atualizarTema(id, tema)
				.map(temaAtualizado -> ResponseEntity.status(201).body("Tema: " + tema.getNome() + "\nATUALIZADO"))
				.orElse(ResponseEntity.status(400).body("Erro ao atualizar. Tema não existe, ou nome em duplicata."));
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity <String> delete(@PathVariable (value = "id") long id)
	{		
		Optional <Tema> temaExistente = repository.findById(id);
		
		if (temaExistente.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).body("Tema deletado com sucesso.");
		} else {
			return ResponseEntity.status(200).body("Tema não pode ser deletado, pois não existe.");
		}
	}

}


