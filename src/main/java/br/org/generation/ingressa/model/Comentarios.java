package br.org.generation.ingressa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_comentarios")
public class Comentarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDeComentario = new java.sql.Date(System.currentTimeMillis());

	@NotNull
	private String comentario;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"comentarios", "usuario", "curtidoresPostagem"})
	private Postagem postagem;
	
	@ManyToOne
	@JsonIgnoreProperties(value = {"comentarios", "postagem", "email", "dataNascimento", "senha", "descSobre", "telefone", "qtdPostagem", "postagemCurtidas"})
	private Usuario usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public Date getDataDeComentario() {
		return dataDeComentario;
	}

	public void setDataDeComentario(Date dataDeComentario) {
		this.dataDeComentario = dataDeComentario;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
