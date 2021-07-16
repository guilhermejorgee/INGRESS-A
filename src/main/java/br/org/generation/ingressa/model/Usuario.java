package br.org.generation.ingressa.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(min = 3)
	private String nome;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private LocalDate dataNascimento;
	
	@NotNull
	@Size(min = 8)
	private String senha;
	
	@NotNull
	private Boolean usuarioEmpregador;

	
	@Size(max = 255)
	private String descSobre;
	
	@Size(max = 11)
	private String telefone;
	
	private String fotoPerfil;
	
	private Boolean usuarioAdmin;
	
	@Size(max = 20)
	private String empresaAtual;
	
	@Transient
	private int qtdPostagem;
	
	
	@OneToMany(mappedBy = "usuario", cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
	@JsonIgnoreProperties(value = {"usuario", "curtidoresPostagem"}, allowSetters = true)
	private List<Postagem> postagem;
	

	@ManyToMany
	@JsonIgnoreProperties(value = {"curtidoresPostagem", "usuario", "regiao", "cargo", "texto", "midia", "qtCurtidas", "tema", "dataDePostagem"})
	@JoinTable(name="curtidas_postagens",
	joinColumns = @JoinColumn(name="tb_usuario_id"),
	inverseJoinColumns = @JoinColumn(name="tb_postagem_id"))
	private Set<Postagem> postagemCurtidas;
		
	
	
	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}	

	public int getQtdPostagem() {
		return qtdPostagem;
	}

	public void setQtdPostagem(int qtdPostagem) {
		this.qtdPostagem = qtdPostagem;
	}

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


	public Boolean getUsuarioEmpregador() {
		return usuarioEmpregador;
	}

	public void setUsuarioEmpregador(Boolean usuarioEmpregador) {
		this.usuarioEmpregador = usuarioEmpregador;
	}

	public String getDescSobre() {
		return descSobre;
	}

	public void setDescSobre(String descSobre) {
		this.descSobre = descSobre;
	}
	

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getUsuarioAdmin() {
		return usuarioAdmin;
	}

	public void setUsuarioAdmin(Boolean usuarioAdmin) {
		this.usuarioAdmin = usuarioAdmin;
	}

	public String getEmpresaAtual() {
		return empresaAtual;
	}

	public void setEmpresaAtual(String empresaAtual) {
		this.empresaAtual = empresaAtual;
	}

	public Set<Postagem> getPostagemCurtidas() {
		return postagemCurtidas;
	}

	public void setPostagemCurtidas(Set<Postagem> postagemCurtidas) {
		this.postagemCurtidas = postagemCurtidas;
	}

	
	
	
	


	
	

}
