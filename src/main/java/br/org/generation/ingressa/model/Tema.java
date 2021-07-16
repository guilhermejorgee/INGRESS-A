package br.org.generation.ingressa.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

@Entity
@Table(name = "tb_tema")
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Size(max = 100)
	private String area;

	@Size(max = 100)
	private String palavraChave;
	
	@NotNull
	private Boolean tipoTema;
	
	@OneToMany(mappedBy = "tema", cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
	@JsonIgnoreProperties(value = "tema", allowSetters = true)
	private List<Postagem> postagem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}


	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

	public Boolean getTipoTema() {
		return tipoTema;
	}

	public void setTipoTema(Boolean tipoTema) {
		this.tipoTema = tipoTema;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
}