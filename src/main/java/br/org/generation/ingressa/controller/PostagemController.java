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

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.service.PostagemService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	@Autowired
	private PostagemService postagemService;

	@GetMapping
	public ResponseEntity<List<Postagem>> buscarPorTudo() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/comuns")
	public ResponseEntity<List<Postagem>> exibirTodasPostagensComuns() {
		return ResponseEntity.ok(repository.postagensComuns());
	}
	
	@GetMapping("/vagas")
	public ResponseEntity<List<Postagem>> exibirTodasPostagensVagas() {
		return ResponseEntity.ok(repository.postagensVagas());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> buscarPorId(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> buscarPorTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@GetMapping("/regiao/{regiao}")
	public ResponseEntity<List<Postagem>> buscarPorRegiao(@PathVariable String regiao) {
		return ResponseEntity.ok(repository.findAllByRegiaoContainingIgnoreCase(regiao));
	}

	@GetMapping("/cargo/{cargo}")
	public ResponseEntity<List<Postagem>> BuscarPorCargo(@PathVariable String cargo) {
		return ResponseEntity.ok(repository.findAllByCargoContainingIgnoreCase(cargo));
	}

	@PostMapping
	public ResponseEntity<Postagem> fazerPostagem(@RequestBody Postagem postagem) {
		if(postagemService.verificacaoPostagem(postagem) == null) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemService.verificacaoPostagem(postagem));
	}

	@PutMapping
	public ResponseEntity<Postagem> atualizarPostagem(@RequestBody Postagem postagem) {
		if(postagemService.atualizarPostagem(postagem) == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.OK).body(postagemService.atualizarPostagem(postagem));
	}

	@PutMapping("/curtir/{id}")
	public ResponseEntity<Postagem> atualizarCurtirPostagemId(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(postagemService.curtir(id));
		
	}

	@PutMapping("/descurtir/{id}")
	public ResponseEntity<Postagem> atualizarDescurtirPostagemId(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(postagemService.descurtir(id));

	}
	
	@GetMapping("/emalta")
	public ResponseEntity<List<Postagem>> postagemAlta() {

	return ResponseEntity.status(HttpStatus.OK).body(postagemService.postagensEmAlta());

	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		
		repository.deleteById(id);

	}
}
