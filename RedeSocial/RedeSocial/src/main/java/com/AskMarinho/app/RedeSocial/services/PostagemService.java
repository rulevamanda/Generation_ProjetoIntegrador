package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Postagem;
import com.AskMarinho.app.RedeSocial.repositories.PostagemRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository repository;
	
	/**
	 * Método para cadastrar postagens caso não haja alguma com o mesmo título, caso haja não é cadastrado
	 * @param novaPostagem - objeto passado pelo controller
	 * @return um Optional com os dados cadastrados da nova postagem ou retorna um Optional vazio
	 * @author Antonio
	 */
	public Optional<Postagem> cadastrarPostagem (Postagem novaPostagem){
		Optional<Postagem> postagemExistente = repository.findByTitulo(novaPostagem.getTitulo());
		
		if(postagemExistente.isEmpty()) {
			return Optional.ofNullable(repository.save(novaPostagem));
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * Método para atualizar postagens
	 * @param id - id da postagem passado pelo controller
	 * @param novaPostagem - dados da postagem para serem atualizados que foram passados pelo controller
	 * @return retorna um optional com a postagem atualizada ou retorna um empty vazio caso a postagem não exista
	 * @author Antonio
	 */
	public Optional<Postagem> atualizarPostagem (Long id, Postagem novaPostagem){
		Optional<Postagem> postagemExistente = repository.findById(id);
		
		if(postagemExistente.isPresent()) {
			postagemExistente.get().setTitulo(novaPostagem.getTitulo());
			postagemExistente.get().setDescricao(novaPostagem.getDescricao());
			postagemExistente.get().setUrl_imagem(novaPostagem.getUrl_imagem());
			
			return Optional.ofNullable(repository.save(postagemExistente.get()));
		}
		else {
			return Optional.empty();
		}
	}
	
}
