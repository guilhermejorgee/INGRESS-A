package br.org.generation.ingressa.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.TemaRepository;
import br.org.generation.ingressa.repository.UsuarioRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TemaRepository temaRepository;

	public Postagem verificacaoPostagem(Postagem postagem) {

		Optional<Usuario> usuario = usuarioRepository.findById(postagem.getUsuario().getId());

		postagem.setQtCurtidas(0);

		String postagemConvertEspaco = postagem.getTexto().replace(" ", "&nbsp;");
		String postagemConvertLinha = postagemConvertEspaco.replace("\n", "<br>");
		String postagemComDestaqueUm = postagemConvertLinha.replace("<d>", "<strong>");
		String postagemComDestqueDois = postagemComDestaqueUm.replace("</d>", "</strong>");
		postagem.setTexto(postagemComDestqueDois);

		if (usuario.get().getUsuarioEmpregador() == true) {
			return postagemRepository.save(postagem);
		} else {
			if (postagem.getCargo() != null) {
				return null;
			} else {
				return postagemRepository.save(postagem);
			}
		}

	}

	public Postagem atualizarPostagem(Postagem postagem) {

		Optional<Postagem> postagemBase = postagemRepository.findById(postagem.getId());

		Optional<Usuario> usuario = usuarioRepository.findById(postagem.getUsuario().getId());
		
		Optional<Tema> temaNovo = temaRepository.findById(postagem.getTema().getId());

		if (postagemBase.isPresent()) {

			if ((postagemBase.get().getUsuario().getId() == postagem.getUsuario().getId())
					|| postagem.getUsuario().getUsuarioAdmin() == true) {

				postagem.setDataDePostagem(postagemBase.get().getDataDePostagem());

				postagem.setUsuario(postagemBase.get().getUsuario());
				
				postagem.setCurtidoresPostagem(postagemBase.get().getCurtidoresPostagem());
				
				postagem.setComentarios(postagemBase.get().getComentarios());
				
				if(postagem.getTema().getId() == postagemBase.get().getTema().getId()) {
					postagem.setTema(postagemBase.get().getTema());
				}
				else {
					postagem.setTema(temaNovo.get());
				}
					

				if (postagem.getQtCurtidas() == null) {
					postagem.setQtCurtidas(postagemBase.get().getQtCurtidas());
				} else {
					return null;
				}

				if (postagem.getRegiao() == null) {
					postagem.setRegiao(postagemBase.get().getRegiao());
				} else {
					if (usuario.get().getUsuarioEmpregador() == true || usuario.get().getUsuarioAdmin() == true) {
						postagem.setRegiao(postagem.getRegiao());
					} else {
						return null;
					}
				}
				if (postagem.getCargo() == null) {
					postagem.setCargo(postagemBase.get().getCargo());
				} else {
					if (usuario.get().getUsuarioEmpregador() == true || usuario.get().getUsuarioAdmin() == true) {
						postagem.setCargo(postagem.getCargo());
					} else {
						return null;
					}
				}
				if (postagem.getTitulo() == null) {
					postagem.setTitulo(postagemBase.get().getTitulo());
				}
				if (postagem.getTexto() == null) {
					postagem.setTexto(postagemBase.get().getTexto());
				} else {
					String postagemConvertEspaco = postagem.getTexto().replace(" ", "&nbsp;");
					String postagemConvertLinha = postagemConvertEspaco.replace("\n", "<br>");
					String postagemComDestaqueUm = postagemConvertLinha.replace("<d>", "<strong>");
					String postagemComDestqueDois = postagemComDestaqueUm.replace("</d>", "</strong>");
					postagem.setTexto(postagemComDestqueDois);
				}
				if (postagem.getMidia() == null) {
					postagem.setMidia(postagemBase.get().getMidia());
				}

				return postagemRepository.save(postagem);

			}

			else {

				return null;
			}

		}

		return null;

	}

	public void deletarPostagem(long id) {

		List<Usuario> usuarios = usuarioRepository.findAll();

		Postagem postagemBase = postagemRepository.findById(id).orElse(null);

		for (Usuario usuario : usuarios) {

			for (Postagem postagem : usuario.getPostagemCurtidas()) {
				
				if (postagem.getId() == id) {

					usuario.getPostagemCurtidas().remove(postagemBase);
					usuarioRepository.save(usuario);

				}

			}

		}

		postagemRepository.deleteById(id);

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

	public LocalDate convertToLocalDate(Date dateToConvert) {
		return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}

	public List<Postagem> postagensEmAltaSemana() {

		List<Postagem> postagens = postagemRepository.postagensComuns();

		List<Postagem> postagensSemana = new ArrayList<Postagem>();

		for (Postagem postagem : postagens) {

			Date dataPostagem = postagem.getDataDePostagem();

			int data = Period.between(convertToLocalDate(dataPostagem), LocalDate.now()).getDays();
			if (data < 7) {
				postagensSemana.add(postagem);
			}

		}

		Collections.sort(postagensSemana, Collections.reverseOrder(Comparator.comparing(Postagem::getQtCurtidas)));

		return postagensSemana;
	}

	/*
	 * public List<Postagem> postagensEmAlta() {
	 * 
	 * List<Postagem> postagens = postagemRepository.pesquisaPostagensEmAlta();
	 * 
	 * Collections.sort(postagens,
	 * Collections.reverseOrder(Comparator.comparing(Postagem::getQtCurtidas)));
	 * 
	 * return postagens;
	 * 
	 * }
	 */

	private Postagem buscarPostagemPeloId(Long id) {

		try {
			return postagemRepository.findById(id).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "", e);
		}

	}
	
	public List<Postagem> temaPostagemUsuario(long idUsuario, long idTema){
		
		Tema tema = temaRepository.findById(idTema).orElse(null);
		
		List<Postagem> postagemUsuario = new ArrayList<Postagem>();
		
		for(Postagem postagem: tema.getPostagem()) {
			if(postagem.getUsuario().getId() == idUsuario) {
				postagemUsuario.add(postagem);
			}
		}
		
		return postagemUsuario;
	}
	

}
