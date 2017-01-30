package br.com.grandcharles.sgw.model.produto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.validation.SKU;

@Entity
@Table(name="tbProduto")
public class ProdutoTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	/* @SKU Validation SKU*/
	@SKU
	@NotBlank 
	@Size(max=8)
	@Column(name="strSku", length=10, nullable=false, unique=true)
	private String sku;

	@NotBlank
	@Size(max=40)
	@Column(name="strDescricao", length=40, nullable=false)
	private String descricao;

	@NotNull(message = "é obrigatório")
	@Column(name="intVlrUnitario", nullable=false, precision=12, scale=2)
	private BigDecimal vlrUnitario;

	@NotNull 
	@Min(0) 
	@Max(value = 9999, message = "tem um valor muito alto")
	@Column(name="intQtdeEstoque", nullable=false, length=4)
	private Integer qtdeEstoque;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idCategoria", nullable=false)
	private CategoriaTO categoriaTO;

	
	
	/* ===========================================================================================*/
	public void baixarItemEstoque(Integer quantidade) {
		int qtde = this.getQtdeEstoque() - quantidade;
		
		if (qtde < 0){
			throw new NegocioException("Não há disponibilidade no estoque de "
					                   + quantidade + " itens do produto " + this.getSku());
		}
		this.setQtdeEstoque(qtde);
	}

	public void adicionarItemEstoque(Integer quantidade) {
		int qtde = this.getQtdeEstoque() + quantidade;

		this.setQtdeEstoque(qtde);
	}
	
	/* ===========================================================================================*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getVlrUnitario() {
		return vlrUnitario;
	}
	public void setVlrUnitario(BigDecimal vlrUnitario) {
		this.vlrUnitario = vlrUnitario;
	}
	
	public Integer getQtdeEstoque() {
		return qtdeEstoque;
	}
	public void setQtdeEstoque(Integer qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}
	
	public CategoriaTO getCategoriaTO() {
		return categoriaTO;
	}
	public void setCategoriaTO(CategoriaTO categoriaTO) {
		this.categoriaTO = categoriaTO;
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
		ProdutoTO other = (ProdutoTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

 
}
