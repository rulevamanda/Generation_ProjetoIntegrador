package com.AskMarinho.app.RedeSocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedeSocialApplication {
		
		//Executando o metodo get, para exibir a mensagem no localhost e retornar um status
		@GetMapping
		public ResponseEntity<String> metodoTeste() {
			return ResponseEntity.status(201).body("Teste!!!");
		}
		
		
	public static void main(String[] args) {
		SpringApplication.run(RedeSocialApplication.class, args);
	}

}
