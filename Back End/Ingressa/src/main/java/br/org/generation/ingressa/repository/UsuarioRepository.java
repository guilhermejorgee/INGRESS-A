package br.org.generation.ingressa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.generation.ingressa.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
	
	public Optional<Usuario> findByEmail(String email);	

	public List<Usuario> findByUsuarioEmpregador(boolean empregador);
		

}
