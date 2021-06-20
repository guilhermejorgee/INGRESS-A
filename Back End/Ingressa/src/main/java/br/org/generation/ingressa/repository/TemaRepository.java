package br.org.generation.ingressa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.ingressa.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	public List<Tema> findAllByAreaContainingIgnoreCase(String area);

	public List<Tema> findAllByPalavraChaveContainingIgnoreCase(String palavrachave);
	
	public List<Tema> findAllByAreaContainingIgnoreCaseOrPalavraChaveContainingIgnoreCase(String valor1, String valor2);
	
	//
	
	/*@Query(value = "select * from tb_tema where area like %:pesquisa% or palavra_chave like %:pesquisa%", nativeQuery = true)
	public List<Tema> pesquisaPorAreaOuPalavraChave(@Param("pesquisa") String pesquisa);*/
}

