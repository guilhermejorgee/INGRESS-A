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
	
	@Query(value = "select * from tb_postagem where usuario_id = :id", nativeQuery = true)
	public List<Postagem> postagemPorIdUsuario(@Param("id") long id); //pesquisar postagens por id do usuario
	
	@Query(value = "select * from tb_postagem where cargo is null and usuario_id = :id", nativeQuery = true)
	public List<Postagem> postagemComunsPorIdUsuario(@Param("id") long id);
	
	@Query(value = "select * from tb_postagem where cargo is not null and usuario_id = :id", nativeQuery = true)
	public List<Postagem> postagemVagasPorIdUsuario(@Param("id") long id);
	
	@Query(value = "select * from tb_postagem inner join tb_tema on tb_tema.id = tb_postagem.tema_id where cargo is not null and (lower(cargo) like %:pesquisa% or lower(area) like %:pesquisa%)", nativeQuery = true)
	public List<Postagem> postagemAreaCargo(@Param("pesquisa") String pesquisa);
	
	@Query(value = "select * from tb_postagem inner join tb_tema on tb_tema.id = tb_postagem.tema_id where cargo is not null and (lower(regiao) like %:regiao% and (lower(cargo) like %:pesquisa% or lower(area) like %:pesquisa%))", nativeQuery = true)
	public List<Postagem> postagemAreaCargoRegiao(@Param("pesquisa") String pesquisa, @Param("regiao") String regiao);
	
	@Query(value = "select * from tb_postagem inner join tb_usuario on tb_usuario.id = tb_postagem.usuario_id where nome like %:pesquisa% or email like %:pesquisa%", nativeQuery = true)
	public List<Postagem> postagemPorNomeEmailUsuario(@Param("pesquisa") String pesquisa); //pesquisar postagens por nome e email
	
	@Query(value = "select * from tb_postagem where tema_id = :id", nativeQuery = true)
	public List<Postagem> postagemPorIdTema(@Param("id") long id);
	
	@Query(value = "select count(usuario_id) from tb_postagem where cargo is not null and usuario_id = :id", nativeQuery = true)
	public int countPosts(@Param("id") long id);
	/*Essa pesquisa serve para separar as postagens de emprego e também verificar
	qual o usuário fez a postagem, para contabilizar no ranking*/
	
	//@Query(value = "select * from tb_postagem where cargo is null", nativeQuery = true)
	//public List<Postagem> pesquisaPostagensEmAlta(); //pesquisa para ranking de postagens comuns
	
	@Query(value = "select * from tb_postagem inner join tb_tema on tb_tema.id = tb_postagem.tema_id where tipo_tema = true and area like %:pesquisa% or palavra_chave like %:pesquisa%;", nativeQuery = true)
	public List<Postagem> pesquisaPostagensVagasPorAreaEPalavraChave(@Param("pesquisa") String pesquisa); //pesquisar por area e palavra chave de postagens de vagas
	
	@Query(value = "select * from tb_postagem inner join tb_tema on tb_tema.id = tb_postagem.tema_id where tipo_tema = false and area like %:pesquisa% or palavra_chave like %:pesquisa%;", nativeQuery = true)
	public List<Postagem> pesquisaPostagensComunsPorAreaEPalavraChave(@Param("pesquisa") String pesquisa); //pesquisar por area e palavra chave de postagens comuns
	
}