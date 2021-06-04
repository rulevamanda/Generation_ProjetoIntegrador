package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Postagem;
import com.AskMarinho.app.RedeSocial.models.Tema;
import com.AskMarinho.app.RedeSocial.models.Usuario;
import com.AskMarinho.app.RedeSocial.repositories.PostagemRepository;
import com.AskMarinho.app.RedeSocial.repositories.TemaRepository;
import com.AskMarinho.app.RedeSocial.repositories.UsuarioRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository repository;

	@Autowired
	private UsuarioRepository repositoryU;

	@Autowired
	private TemaRepository repositoryT;

	/**
	 * Método para cadastrar postagens caso não haja alguma com o mesmo título, caso
	 * haja não é cadastrado
	 * 
	 * @param idUsuario
	 * @param idTema
	 * @param novaPostagem
	 * @author Antonio
	 * @author Matheus
	 * @returnum Optional com os dados cadastrados da nova postagem ou retorna um
	 *           Optional vazio
	 */
	public Optional<Object> cadastrarPostagem(Long idUsuario, Long idTema, Postagem novaPostagem) {
		Optional<Postagem> postagemExistente = repository.findByTitulo(novaPostagem.getTitulo());

		if (postagemExistente.isEmpty()) {
			Optional<Usuario> usuarioExistente = repositoryU.findById(idUsuario);

			if (usuarioExistente.isPresent()) {
				Optional<Tema> temaExistente = repositoryT.findById(idTema);

				if (temaExistente.isPresent()) {
					novaPostagem.setUsuarioPostagem(usuarioExistente.get());
					novaPostagem.getTemasRelacionados().add(temaExistente.get());
					return Optional.ofNullable(repository.save(novaPostagem));
				}

			}

		}

		return Optional.empty();
	}

	/**
	 * Método para atualizar postagens
	 * 
	 * @param idPostagem   - id da postagem passado pelo controller
	 * @param novaPostagem - dados da postagem para serem atualizados que foram
	 *                     passados pelo controller
	 * @return retorna um optional com a postagem atualizada ou retorna um empty
	 *         vazio caso a postagem não exista
	 * @author Antonio
	 */
	public Optional<Object> atualizarPostagem(Long idPostagem, Postagem novaPostagem) {
		Optional<Postagem> postagemExistente = repository.findById(idPostagem);

		if (postagemExistente.isPresent()) {
			Optional<Postagem> tituloExistente = repository.findByTitulo(novaPostagem.getTitulo());

			if (tituloExistente.isEmpty()) {
				postagemExistente.get().setTitulo(novaPostagem.getTitulo());
				postagemExistente.get().setDescricao(novaPostagem.getDescricao());
				postagemExistente.get().setUrl_imagem(novaPostagem.getUrl_imagem());

				return Optional.ofNullable(repository.save(postagemExistente.get()));
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

	public Optional<Postagem> adicionarTema(Long idPostagem, Long idTema) {
		Optional<Tema> temaExistente = repositoryT.findById(idTema);
		if (temaExistente.isPresent()) {
			Optional<Postagem> postagemExistente = repository.findById(idPostagem);
			if (postagemExistente.isPresent()) {
				postagemExistente.get().getTemasRelacionados().add(temaExistente.get());
				return Optional.ofNullable(repository.save(postagemExistente.get()));
			}

		} 
		return Optional.empty();
	}
}