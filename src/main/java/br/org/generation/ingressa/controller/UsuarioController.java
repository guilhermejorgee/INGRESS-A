package br.org.generation.ingressa.controller;

import java.util.List;
import java.util.Optional;


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

import br.org.generation.ingressa.model.UserLogin;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.UsuarioRepository;
import br.org.generation.ingressa.service.UsuarioService;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository repository;
	
	
	@GetMapping
	public ResponseEntity<List<Usuario>> todosUsuarios(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> GetById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<Usuario> pesquisaPorUsuario(@PathVariable String usuario){
		return repository.findByEmailAndNomeContainingIgnoreCase(usuario, usuario).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	


	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
		if(usuarioService.CadastrarUsuario(usuario) == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuario));

	}
	
	@GetMapping("/tops")
	public ResponseEntity<List<Usuario>> topEmpregadores() {
		
		return ResponseEntity.ok(usuarioService.maiorQtdDePostagens());
	
	}
	
	@GetMapping("/topsmes")
	public ResponseEntity<List<Usuario>> topEmpregadoresMes() {
		
		return ResponseEntity.ok(usuarioService.maiorQtdDePostagensMes());
	
	}
	
	@PutMapping
	public ResponseEntity<Usuario> Put(@RequestBody Usuario usuario){
		Optional<Usuario> user = usuarioService.atualizarUsuario(usuario);
		try {
			return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public void deletarUsuario(@PathVariable long id){
		usuarioService.deletarUsuario(id);
	}
	
	@PutMapping("/adicionarcurtida/{idUsuario}/{idPostagem}")
	public ResponseEntity<Usuario> AdiocionarCurtidaPostagem(@PathVariable Long idUsuario, @PathVariable Long idPostagem){
		Optional<Usuario> user = usuarioService.adicionarCurtidasDoUsuario(idUsuario, idPostagem);
		try {
			return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/removercurtida/{idUsuario}/{idPostagem}")
	public ResponseEntity<Usuario> removerCurtidaPostagem(@PathVariable Long idUsuario, @PathVariable Long idPostagem){
		Optional<Usuario> user = usuarioService.removerCurtidasDoUsuario(idUsuario, idPostagem);
		try {
			return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
