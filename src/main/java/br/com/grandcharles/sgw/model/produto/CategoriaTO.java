package br.com.grandcharles.sgw.model.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tbcategoria")
public class CategoriaTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotBlank 
	@Size(max=30)
	@Column(name="strDescricao", length=30, nullable=false)
	private String descricao;

	@ManyToOne
	@JoinColumn(name="idCategoriaPai")
	private CategoriaTO categoriaPai;

	@OneToMany(mappedBy="categoriaPai", cascade=CascadeType.ALL)
	private List<CategoriaTO> lstSubCategorias = new ArrayList<>();

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaTO getCategoriaPai() {
		return categoriaPai;
	}
	public void setCategoriaPai(CategoriaTO categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	
	public List<CategoriaTO> getLstSubCategorias() {
		return lstSubCategorias;
	}
	public void setLstSubCategorias(List<CategoriaTO> lstSubCategorias) {
		this.lstSubCategorias = lstSubCategorias;
	}	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CategoriaTO other = (CategoriaTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
