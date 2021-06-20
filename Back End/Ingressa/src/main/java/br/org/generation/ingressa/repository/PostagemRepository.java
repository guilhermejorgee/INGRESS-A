package br.org.generation.ingressa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.generation.ingressa.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long> {
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	public List<Postagem> findAllByCargoContainingIgnoreCase(String cargo);
	public List<Postagem> findAllByRegiaoContainingIgnoreCase(String regiao);
	
	// 
	
	@Query(value = "select * from tb_postagem where cargo is null", nativeQuery = true)
	public List<Postagem> postagensComuns(); //Exibir todas as postagens comuns
	
	@Query(value = "select * from tb_postagem where cargo is not null", nativeQuery = true)
	public List<Postagem> postagensVagas(); //Exibir todas as postagens de vagas
	
	@Query(value = "select count(usuario_id) from tb_postagem where cargo is not null and usuario_id = :id", nativeQuery = true)
	public int countPosts(@Param("id") long id);
	/*Essa pesquisa serve para separar as postagens de emprego e também verificar
	qual o usuário fez a postagem, para contabilizar no ranking*/
	
	@Query(value = "select * from tb_postagem where tema_id = 1", nativeQuery = true)
	public List<Postagem> pesquisaPostagensEmAlta(); //Exibir todas as postagens de vagas
}