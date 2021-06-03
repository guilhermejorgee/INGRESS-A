package br.org.generation.ingressa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.ingressa.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository <Tema, Long> {
	public List<Tema> findAllByAreaContainingIgnoreCase(String area);
	public List<Tema> findAllByPalavraChaveContainingIgnoreCase(String palavrachave);
}

