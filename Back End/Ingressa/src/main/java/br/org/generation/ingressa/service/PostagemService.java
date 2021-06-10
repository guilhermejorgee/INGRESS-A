package br.org.generation.ingressa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.repository.PostagemRepository;

@Service
public class PostagemService {
	@Autowired
	private PostagemRepository postagemRepository;

	public Postagem curtir(Long id) {

		Postagem postagem = buscarPostagemPeloId(id);

		postagem.setQtCurtidas(postagem.getQtCurtidas() + 1);

		return postagemRepository.save(postagem);

	}

	public Postagem descurtir(Long id) {

		Postagem postagem = buscarPostagemPeloId(id);

		if (postagem.getQtCurtidas() > 0) {

			postagem.setQtCurtidas(postagem.getQtCurtidas() - 1);

		} else {

			postagem.setQtCurtidas(0);

		}

		return postagemRepository.save(postagem);

	}

	private Postagem buscarPostagemPeloId(Long id) {

		Postagem postagemSalva = postagemRepository.findById(id).orElse(null);

		if (postagemSalva == null) {

			throw new EmptyResultDataAccessException(1);
		}

		return postagemSalva;
	}

}
