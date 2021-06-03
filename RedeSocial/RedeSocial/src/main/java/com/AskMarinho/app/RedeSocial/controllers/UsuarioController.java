package com.AskMarinho.app.RedeSocial.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.repositories.UsuarioRepository;
import com.AskMarinho.app.RedeSocial.services.UsuarioService;

@RestController
@RequestMapping ("/usuarios")
@CrossOrigin (origins = "*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService services;
	
	@GetMapping ("/todes")
	public ResponseEntity<List<Usuario>> buscarTodes (){
		List <Usuario> listarTodes = repository.findAll();
		return ResponseEntity.status(200).body(listarTodes);
	}
	
	@GetMapping ("/nome/pesquisar")
	public ResponseEntity<Object> buscarPorNome (@RequestParam (defaultValue = "") String nome) {
		List<Usuario> listaDeNomes = repository.findAllByNomeContainingIgnoreCase(nome);
		
		if (!listaDeNomes.isEmpty()){
			return ResponseEntity.status(200).body(listaDeNomes);
		} else {
			return ResponseEntity.status(204).body("Ooops... Parece que esse usuário ainda não existe!");
		}
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> buscarPorId (@PathVariable Long id) {
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping ("/cadastrar")
	public ResponseEntity<String> cadastrarUsuario (@Valid @RequestBody Usuario novoUsuario){
		return services.cadastrarUsuario(novoUsuario)
				.map(emailCadastrado -> ResponseEntity.status(201).body("Usuario: "+novoUsuario.getNomeUsuario()+"\nEmail: "
						+ novoUsuario.getEmail()+"\nCADASTRADO"))
				.orElse(ResponseEntity.status(400).body("Erro ao cadastrar. Nome de Usuário ou Email já está sendo utilizado."));
	}
	
	@PutMapping ("/{id_usuario}/atualizar")
	public ResponseEntity <String> atualizarUsuario (@Valid @RequestBody Usuario atualizacaoUsuario, 
			@Valid @PathVariable  (value = "id_usuario") Long id) {
		return services.atualizarUsuario(id, atualizacaoUsuario)
				.map(atualizarUsuario -> ResponseEntity.status(201).body("Usuario: "+atualizacaoUsuario.getNomeUsuario()+"\nEmail: "
						+ atualizacaoUsuario.getEmail()+"\nCADASTRADO"))
				.orElse(ResponseEntity.status(400).body("Erro ao atualizar. Usuário não existe ou o nome de Usuário ou Email já está sendo utilizado."));
	}
	
	@PutMapping ("/{id_usuario}/email/atualizar")
	public ResponseEntity<Object> atualizarEmail (@Valid @RequestBody Usuario novoEmail){
		return services.cadastrarUsuario(novoEmail)
				.map(emailCadastrado -> ResponseEntity.status(201).body(emailCadastrado))
				.orElse(ResponseEntity.status(400).body("Ooops.. parece que esse email já foi cadastrado. Tente outro!"));
	}
	
	@DeleteMapping("/{id_usuario}/deletar")
	public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id_usuario) {
		Optional<Usuario> usuarioExistente = repository.findById(id_usuario);
		
		if(usuarioExistente.isPresent()) {
			repository.deleteById(id_usuario);
			return ResponseEntity.status(200).body(null);
		}
		else {
			return ResponseEntity.status(400).body(null);
		}
	}
	
}
