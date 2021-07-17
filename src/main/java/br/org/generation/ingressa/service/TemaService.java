package br.org.generation.ingressa.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.TemaRepository;

@Service
public class TemaService {
	
	@Autowired
	TemaRepository temaRepository;
	
	@Autowired
	PostagemRepository postagemRepository;
	
	public Tema atualizarTema(Tema tema) {
		
		Optional<Tema> temaBase = temaRepository.findById(tema.getId());
		
		tema.setPostagem(temaBase.get().getPostagem());
					
			
			return temaRepository.save(tema);

		
	}
	
	public void deletarTema(long id) {
		
		
		List<Postagem> postagens = postagemRepository.postagemPorIdTema(id);
		

		for (Postagem postagem : postagens) {

			
			postagemRepository.deleteById(postagem.getId());
		}
		
				
		temaRepository.deleteById(id);
		
	}
	

		
}
