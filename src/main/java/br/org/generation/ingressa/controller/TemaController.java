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

import br.org.generation.ingressa.model.Tema;
import br.org.generation.ingressa.repository.TemaRepository;
import br.org.generation.ingressa.service.TemaService;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/tema")
public class TemaController {

	@Autowired
	private TemaRepository repository;
	
	@Autowired
	private TemaService temaService;
	

	@GetMapping
	public ResponseEntity<List<Tema>> PesquisaTodosOsTemas() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/vagas")
	public ResponseEntity<List<Tema>> PesquisaTodosTemasVagas() {
		return ResponseEntity.ok(repository.pesquisaDeTemasDeVagas());
	}
	
	@GetMapping("/comuns")
	public ResponseEntity<List<Tema>> PesquisaTodosTemasComuns() {
		return ResponseEntity.ok(repository.pesquisaDeTemasDeComuns());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	

/*	@GetMapping("/areavagas/{area}")
	public ResponseEntity<List<Tema>> PesquisarPorAreaVagas(@PathVariable String area) {
		return ResponseEntity.ok(repository.pesquisaDeAreasDeVagasEPalavraChave(area));
	}
	
	@GetMapping("/areacomuns/{area}")
	public ResponseEntity<List<Tema>> PesquisarPorAreaComuns(@PathVariable String area) {
		return ResponseEntity.ok(repository.pesquisaDeAreasDeComunsEPalavraChave(area));
	}
	*/
	

/*	@GetMapping("/palavrachave/{palavrachave}")
	public ResponseEntity<List<Tema>> GetByPalavrachave(@PathVariable String palavrachave) {
		return ResponseEntity.ok(repository.findAllByPalavraChaveContainingIgnoreCase(palavrachave));
	}*/
	
/*	@GetMapping("/areaoupalavrachave/{pesquisa}")
	public ResponseEntity<List<Tema>> GetByPalavrachaveOuArea(@PathVariable String pesquisa) {
		return ResponseEntity.ok(repository.findAllByAreaContainingIgnoreCaseOrPalavraChaveContainingIgnoreCase(pesquisa, pesquisa));
	}*/

	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}

	@PutMapping
	public ResponseEntity<Tema> atualizacaoDoTema(@RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.OK).body(temaService.atualizarTema(tema));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		temaService.deletarTema(id);
	}

}
