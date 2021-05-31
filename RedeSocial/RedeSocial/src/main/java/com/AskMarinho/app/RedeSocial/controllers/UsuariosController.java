package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.UsuariosModel;
import com.AskMarinho.app.RedeSocial.repositories.UsuariosRepository;
import com.AskMarinho.app.RedeSocial.services.UsuariosService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuariosController {
	
	@Autowired
	private UsuariosRepository repository;
	
	@Autowired
	private UsuariosService services;
	
	@GetMapping
	public ResponseEntity<List<UsuariosModel>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id_usuario}")
	public ResponseEntity<UsuariosModel> GetById(@PathVariable Long id_usuario){
		return repository.findById(id_usuario)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/username/{nome_usuario}")
	public ResponseEntity<Object> GetByName(@PathVariable String nome_usuario){
		return repository.findByUsername(nome_usuario)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Object> GetByEmail(@PathVariable String email){
		return repository.findByEmail(email)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/telefone/{telefone}")
	public ResponseEntity<Optional<Object>> GetByTelephone(@PathVariable Long telefone){
		return ResponseEntity.ok(repository.findByTelephoneContaining(telefone));
	}
	
	@PostMapping ("/novo")
	public ResponseEntity<Object> CadastrarUsuarios (@RequestBody UsuariosModel novoUsuario, @RequestBody UsuariosModel novoEmail){
		return services.cadastrarUsuario(novoUsuario, novoEmail)
				.map(newUser -> ResponseEntity.status(201).body(newUser))
				.orElse(ResponseEntity.status(400).body("Oops...parece que esse cadastro já existe!"));
	}
	
	@PutMapping ("/{id_usuario}/atualizar")
	public ResponseEntity<UsuariosModel> put (@RequestBody UsuariosModel id_usuario, @RequestBody UsuariosModel updateUsuario){
		return services.atualizarUsuario(id_usuario, updateUsuario)
				.map(updateUser -> ResponseEntity.status(201).body(updateUser))
				.orElse(ResponseEntity.status(400).build());
	}
	
	@PatchMapping ("/{id_usuario}/user/atualizar")
	public ResponseEntity<Object> atualizarUsername (@Valid @RequestBody UsuariosModel novoUsername){
		return services.atualizarUsername(novoUsername)
				.map(newUsername -> ResponseEntity.status(201).body(newUsername))
				.orElse(ResponseEntity.status(400).build());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id_usuarios) {
		repository.deleteById(id_usuarios);
	}
	
}
