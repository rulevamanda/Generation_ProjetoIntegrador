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
	 * MÃ©todo para cadastrar postagens caso nÃ£o haja alguma com o mesmo tÃ­tulo,
	 * caso haja nÃ£o Ã© cadastrado
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
	 * MÃ©todo para atualizar postagens
	 * 
	 * @param idPostagem   - id da postagem passado pelo controller
	 * @param novaPostagem - dados da postagem para serem atualizados que foram
	 *                     passados pelo controller
	 * @return retorna um optional com a postagem atualizada ou retorna um empty
	 *         vazio caso a postagem nÃ£o exista
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

	/**
	 * Método para adicionar um tema dentro da postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @author Matheus
	 * @return Optional com o tema adicionado ou Optional vazio
	 */
	public Optional<Object> adicionarTema(Long idPostagem, Long idTema) {
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

	/**
	 * Método para deletar um tema de dentro de uma postagem
	 * 
	 * @param idPostagem
	 * @param idTema
	 * @return Optional com a postagem com o tema deletado ou um Optional vazio
	 * @author Antonio
	 */
	public Optional<Object> deletarTemaDaPostagem(Long idPostagem, Long idTema) {
		Optional<Tema> temaExistente = repositoryT.findById(idTema);

		if (temaExistente.isPresent()) {
			Optional<Postagem> postagemExistente = repository.findById(idPostagem);

			if (postagemExistente.isPresent()) {
				if (postagemExistente.get().getTemasRelacionados().contains(temaExistente.get())) {
					postagemExistente.get().getTemasRelacionados().remove(temaExistente.get());

					return Optional.ofNullable(repository.save(postagemExistente.get()));
				}

			}
		}
		return Optional.empty();
	}
}