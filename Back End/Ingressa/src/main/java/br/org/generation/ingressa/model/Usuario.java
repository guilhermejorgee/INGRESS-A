package br.org.generation.ingressa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min = 8, max = 20)
	private String senha;
	
	@NotNull
	private boolean usuarioEmpregador;
	
	@Size(min = 5, max = 255)
	private String descSobre;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isUsuarioEmpregador() {
		return usuarioEmpregador;
	}

	public void setUsuarioEmpregador(boolean usuarioEmpregador) {
		this.usuarioEmpregador = usuarioEmpregador;
	}

	public String getDescSobre() {
		return descSobre;
	}

	public void setDescSobre(String descSobre) {
		this.descSobre = descSobre;
	}

}
