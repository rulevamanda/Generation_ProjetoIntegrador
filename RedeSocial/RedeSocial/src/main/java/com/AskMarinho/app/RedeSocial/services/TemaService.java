package com.AskMarinho.app.RedeSocial.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AskMarinho.app.RedeSocial.models.Tema;
import com.AskMarinho.app.RedeSocial.repositories.TemaRepository;

@Service
public class TemaService {

	@Autowired
	private TemaRepository repositoryT;

	public Optional<Object> cadastrarTema(Tema novoTema) {

		Optional<Tema> temaExistente = repositoryT.findByNome(novoTema.getNome());

		if (temaExistente.isEmpty()) {
			return Optional.ofNullable(repositoryT.save(novoTema));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Object> atualizarTema(Long id, Tema atualizacaoT) {

		Optional<Tema> temaExistente = repositoryT.findById(id);

		if (temaExistente.isPresent()) {
			Optional<Tema> nomeExistente = repositoryT.findByNome(atualizacaoT.getNome());

			if (nomeExistente.isEmpty()) {
				temaExistente.get().setNome(atualizacaoT.getNome());

				return Optional.ofNullable(repositoryT.save(temaExistente.get()));
			} else {
				return Optional.empty();
			}

		} else {
			return Optional.empty();
		}
	}


}
