package br.org.generation.ingressa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.ingressa.model.Comentarios;
import br.org.generation.ingressa.repository.ComentariosRepository;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/comentario")
public class ComentariosController {
	
	@Autowired
	ComentariosRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Comentarios>> todosComentarios(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Comentarios> adicionarComentario(@RequestBody Comentarios comentario){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(comentario));
	}
	
	@PutMapping
	public ResponseEntity<Comentarios> editarComentario(@RequestBody Comentarios comentario){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(comentario));
	}
	
	@DeleteMapping("/{id}")
	void deletarComentario(@PathVariable long id) {
		
		repository.deleteById(id);
		
	}
	
	

}
