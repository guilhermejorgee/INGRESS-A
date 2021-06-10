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
	
<<<<<<< HEAD
	// 
	
	@Query(value = "select count(usuario_id) from tb_postagem where cargo is not null && usuario_id = :id", nativeQuery = true)
=======
	
	@Query(value = "select count(usuario_id) from tb_postagem where usuario_id = :id", nativeQuery = true)
>>>>>>> 8fa961b1d8accc64f46bc9dd50e63fa68aafe011
	public int countPosts(@Param("id") long id);
}