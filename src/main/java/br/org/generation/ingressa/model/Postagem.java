package br.org.generation.ingressa.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_postagem")
public class Postagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDePostagem = new java.sql.Date(System.currentTimeMillis());

	private String regiao;

	
	private String cargo;

	@NotNull(message = "Título Obrigatório")
	@Size(min = 4)
	private String titulo;

	@Column(columnDefinition = "text")
	@NotNull(message = "Texto Obrigatório")
	@Size(min = 10)
	private String texto;
	
	
	private String midia;

	@PositiveOrZero
	private Integer qtCurtidas;
	

	@ManyToOne
	@JsonIgnoreProperties(value = "postagem")
	private Tema tema;

	@ManyToOne
	@JsonIgnoreProperties(value = {"postagem", "postagemCurtidas", "comentarios"})
	private Usuario usuario;
	
	@ManyToMany(mappedBy = "postagemCurtidas", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"postagemCurtidas", "postagem", "email", "dataNascimento", "senha", "usuarioEmpregador", "descSobre", "telefone", "empresaAtual", "qtdPostagem", "comentarios"})
	private Set<Usuario> curtidoresPostagem;
	
	@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = {"postagem"})
	private List<Comentarios> comentarios;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataDePostagem() {
		return dataDePostagem;
	}

	public void setDataDePostagem(Date dataDePostagem) {
		this.dataDePostagem = dataDePostagem;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;

	}
	
	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	public Integer getQtCurtidas() {
		return qtCurtidas;
	}

	public void setQtCurtidas(Integer qtCurtidas) {
		this.qtCurtidas = qtCurtidas;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Usuario> getCurtidoresPostagem() {
		return curtidoresPostagem;
	}

	public void setCurtidoresPostagem(Set<Usuario> curtidoresPostagem) {
		this.curtidoresPostagem = curtidoresPostagem;
	}

	public List<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	
	
	
	

}
