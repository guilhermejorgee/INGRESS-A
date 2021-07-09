package br.org.generation.ingressa.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.org.generation.ingressa.model.Postagem;
import br.org.generation.ingressa.model.UserLogin;
import br.org.generation.ingressa.model.Usuario;
import br.org.generation.ingressa.repository.PostagemRepository;
import br.org.generation.ingressa.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PostagemRepository postagemRepository;

	public Usuario CadastrarUsuario(Usuario usuario) {
		
		if(repository.findByEmail(usuario.getEmail()).isPresent()) {
            return null;
		}
		
		if(usuario.getUsuarioEmpregador() == false && !(usuario.getEmpresaAtual() == null)) {
			return null;
		}
		
		int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

		if(idade < 14)
			return null;
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaEncoder = encoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaEncoder);
		

		
		return repository.save(usuario);
		
		
	}

	public Optional<UserLogin> Logar(Optional<UserLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Optional<Usuario> usuario = repository.findByEmail(user.get().getEmail());
		
		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getEmail() + ":" + user.get().getSenha();
				
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				
				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);
				
				user.get().setId(usuario.get().getId());

				user.get().setNome(usuario.get().getNome());
				
				user.get().setFotoPerfil(usuario.get().getFotoPerfil());
						
				user.get().setUsuarioEmpregador(usuario.get().getUsuarioEmpregador());
				
				user.get().setEmpresaAtual(usuario.get().getEmpresaAtual());
				
				user.get().setTelefone(usuario.get().getTelefone());
				
				user.get().setSenha(null); // Não retorna a senha no console!
				
				return user;
			}
		}
		return null;
	}
	
	public Usuario atualizarUsuario(Usuario usuario){
		
		Optional<Usuario> usuarioBase = repository.findById(usuario.getId());
		
		if(usuario.getId() == usuarioBase.get().getId() || usuario.getUsuarioAdmin() == true) {
		
		usuario.setUsuarioAdmin(usuarioBase.get().getUsuarioAdmin());
					
		if(usuario.getEmail() == null) {
			
			usuario.setEmail(usuarioBase.get().getEmail());
		}
		else {		
			
			if(repository.findByEmail(usuario.getEmail()).isPresent()) {
				return null;
			}
		}
		
		if(usuario.getNome() == null) {
			usuario.setNome(usuarioBase.get().getNome());
		}
		
		if(usuario.getSenha() == null) {
			usuario.setSenha(usuarioBase.get().getSenha());
		}
		else {
		
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaEncoder = encoder.encode(usuario.getSenha());

			usuario.setSenha(senhaEncoder);
		}
			
		if(usuario.getDataNascimento() == null) {
			usuario.setDataNascimento(usuarioBase.get().getDataNascimento());
		}
		if(usuario.getUsuarioEmpregador() == null) {
			usuario.setUsuarioEmpregador(usuarioBase.get().getUsuarioEmpregador());
		}
		if(usuario.getDescSobre() == null) {
			usuario.setDescSobre(usuarioBase.get().getDescSobre());
		}
		if(usuario.getTelefone() == null) {
			usuario.setTelefone(usuarioBase.get().getTelefone());
		}		
		if(usuario.getFotoPerfil() == null) {
			usuario.setFotoPerfil(usuarioBase.get().getFotoPerfil());
		}
		if(usuario.getEmpresaAtual() == null) {
			usuario.setEmpresaAtual(usuarioBase.get().getEmpresaAtual());
		}
					
		return repository.save(usuario);
		
		}
		
		return null;
		

	}
	
	public List<Usuario> maiorQtdDePostagens(){
		
		List<Usuario> usuarios = repository.findByUsuarioEmpregador(true);
		
		
		for (Usuario usuario : usuarios) {
			
			usuario.setQtdPostagem(postagemRepository.countPosts(usuario.getId()));
		}
		
		Collections.sort(usuarios, Collections.reverseOrder(Comparator.comparing(Usuario::getQtdPostagem)));
		
		
		return usuarios;
	}
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return LocalDate.ofInstant(
	      dateToConvert.toInstant(), ZoneId.systemDefault());
	}
	
	
	public List<Usuario> maiorQtdDePostagensMes(){
		
		
		List<Usuario> usuarios = repository.findByUsuarioEmpregador(true);
		
		List<Postagem> postagens = postagemRepository.postagensVagas();
		
		List<Postagem> postagensMes = new ArrayList<Postagem>();
		
		List<Usuario> empregadoresMes = new ArrayList<Usuario>();
		
		
		for (Usuario usuario : usuarios) {
			
			usuario.setQtdPostagem(postagemRepository.countPosts(usuario.getId()));
		}
		
		for (Postagem postagem: postagens) {
			
			Date dataPostagem =  postagem.getDataDePostagem();
			
			
			int data = Period.between(convertToLocalDate(dataPostagem), LocalDate.now()).getDays();
			if(data < 30) {
				postagensMes.add(postagem);
		}
			
		}
		for (Postagem postagem: postagensMes) {
			
			
			Usuario user = repository.findById(postagem.getUsuario().getId()).orElse(null);
			
			empregadoresMes.add(user);
			
		}
		
		Collections.sort(empregadoresMes, Collections.reverseOrder(Comparator.comparing(Usuario::getQtdPostagem)));
			
			
		
		return empregadoresMes;
	
}
	
}