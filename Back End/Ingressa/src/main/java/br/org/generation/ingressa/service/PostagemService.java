package br.org.generation.ingressa.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.UsuarioRepository;

@Service
public class PostagemService {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Postagem verificacaoPostagem(Postagem postagem) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(postagem.getUsuario().getId());
		
		postagem.setQtCurtidas(0);
			
		
		if(usuario.get().isUsuarioEmpregador() == true) {
			return postagemRepository.save(postagem);
		}
		else {
			if(postagem.getCargo() != null) {
				return null;
			}
			else {
				return postagemRepository.save(postagem);
			}
		}

	}
	
	public Postagem atualizarPostagem(Postagem postagem) {
		
	Optional<Postagem> postagemBase = postagemRepository.findById(postagem.getId());
	
	Optional<Usuario> usuario = usuarioRepository.findById(postagem.getUsuario().getId());
	
	
	if(postagemBase.isPresent()) {
		
		if(postagemBase.get().getUsuario().getId() == postagem.getUsuario().getId()) {
			
			postagem.setDataDePostagem(postagemBase.get().getDataDePostagem());	
			
			if(postagem.getQtCurtidas() == null) {
				postagem.setQtCurtidas(postagemBase.get().getQtCurtidas());
			}
			else {
				return null;
			}
			
	
			if(postagem.getRegiao() == null) {
				postagem.setRegiao(postagemBase.get().getRegiao());
			}
			else {
				if(usuario.get().isUsuarioEmpregador() == true) {
					postagem.setRegiao(postagem.getRegiao());
				}
				else {
					return null;
				}
			}
			if(postagem.getCargo() == null) {
				postagem.setCargo(postagemBase.get().getCargo());
			}
			else {
				if(usuario.get().isUsuarioEmpregador() == true) {
					postagem.setCargo(postagem.getCargo());
				}
				else {
					return null;
				}
			}
			if(postagem.getTitulo() == null) {
				postagem.setTitulo(postagemBase.get().getTitulo());
			}
			if(postagem.getTexto() == null) {
				postagem.setTexto(postagemBase.get().getTexto());
			}
			if(postagem.getMidia() == null) {
				postagem.setMidia(postagemBase.get().getMidia());
			}
							
			if(postagem.getTema() == null) {							
				postagem.setTema(postagemBase.get().getTema());
			}
			
			return postagemRepository.save(postagem);
						
			
		}
		
		else {
			
			 return null;
		}
		
	}
	
	return null;
		
	}
	

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

		return (postagemRepository.save(postagem));
	}
	
	public List<Postagem> postagensEmAlta() {
		
		List<Postagem> postagens = postagemRepository.pesquisaPostagensEmAlta();
		
		Collections.sort(postagens, Collections.reverseOrder(Comparator.comparing(Postagem::getQtCurtidas)));
		
		return postagens;
		
	}

	private Postagem buscarPostagemPeloId(Long id) {


		try {
			return postagemRepository.findById(id).orElseThrow();	
		}
		catch(NoSuchElementException e) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "", e);
		}

		
	}
	
}
