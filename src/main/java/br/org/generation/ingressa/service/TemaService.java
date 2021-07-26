package br.org.generation.ingressa.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.TemaRepository;
import br.org.generation.ingressa.repository.UsuarioRepository;

@Service
public class TemaService {
	
	@Autowired
	TemaRepository temaRepository;
	
	@Autowired
	PostagemRepository postagemRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Tema atualizarTema(Tema tema) {
		
		Optional<Tema> temaBase = temaRepository.findById(tema.getId());
		
		tema.setPostagem(temaBase.get().getPostagem());
					
			
			return temaRepository.save(tema);

		
	}
	
	public void deletarTema(long id) {
		
		
		List<Postagem> postagens = postagemRepository.postagemPorIdTema(id);
			

		for (Postagem postagem : postagens) {
				
			
			for(Usuario usuario: postagem.getCurtidoresPostagem()) {
				
				for(Postagem postagemUsuario: usuario.getPostagemCurtidas()) {
					
					if(postagemUsuario.getId() == postagem.getId()) {
						usuario.getPostagemCurtidas().remove(postagem);
						postagem.getCurtidoresPostagem().remove(usuario);
						usuarioRepository.save(usuario);
						postagemRepository.save(postagem);
					}
					
				}
				
				//if(postagem.getId() == usuario)
				//usuario.getPostagemCurtidas().remove(postagem);
				//usuarioRepository.save(usuario);
				
				//postagem.getCurtidoresPostagem().remove(usuario);
				
			}
			
			//Usuario usuario = usuarioRepository.getById(postagem.getUsuario().getId());
			
			//usuario.getPostagem().remove(postagem);
			
			//usuarioRepository.save(usuario);
			
			//postagemRepository.save(postagem);
			
			//postagemRepository.deleteById(postagem.getId());				
		
		}
		
				
		temaRepository.deleteById(id);
		
	}
	

		
}
