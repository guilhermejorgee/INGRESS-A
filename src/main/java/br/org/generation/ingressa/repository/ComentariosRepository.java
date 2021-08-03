package br.org.generation.ingressa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.ingressa.model.Comentarios;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {

}
