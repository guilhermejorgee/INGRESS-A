package br.org.generation.ingressa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.repository.TemaRepository;

@Service
public class TemaService {
	
	@Autowired
	TemaRepository temaRepository;
	
	public Tema atualizarTema(Tema tema) {
		
		Optional<Tema> temaBase = temaRepository.findById(tema.getId());
		
		if(temaBase.isPresent()) {
			
			if(tema.getArea() == null) {
				tema.setArea(temaBase.get().getArea());
			}
			
			if(tema.getPalavraChave() == null) {
				tema.setPalavraChave(temaBase.get().getPalavraChave());
			}
			
			if(tema.getTipoTema() == null) {
				tema.setTipoTema(temaBase.get().getTipoTema());
			}
			
			temaRepository.save(tema);
		}
		
		
		return null;
		
	}
	

		
}
