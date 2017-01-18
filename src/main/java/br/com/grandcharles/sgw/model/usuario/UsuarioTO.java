package br.com.grandcharles.sgw.model.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tbusuario")
public class UsuarioTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotBlank
	@Size(max=30)
	@Column(name="strNome", length=30, nullable=false)
	private String nome;

	@NotBlank
	@Size(max=50)
	@Column(name="strEmail", length=50, unique=true)
	private String email;

	@NotBlank
	@Size(max=20)
	@Column(name="strSenha", length=20, nullable=false)
	private String senha;


	@NotNull
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="dpusuariogrupo", 
	           joinColumns={@JoinColumn(name="idUsuario")},
	           inverseJoinColumns={@JoinColumn(name="idGrupoUsuario")})
	private List<GrupoUsuarioTO> lstGrupoUsuario;
		
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
		
	public List<GrupoUsuarioTO> getLstGrupoUsuario() {
		return lstGrupoUsuario;
	}
	public void setLstGrupoUsuario(List<GrupoUsuarioTO> lstGrupoUsuario) {
		this.lstGrupoUsuario = lstGrupoUsuario;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioTO other = (UsuarioTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
