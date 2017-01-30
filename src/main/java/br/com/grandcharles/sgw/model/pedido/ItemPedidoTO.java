package br.com.grandcharles.sgw.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.grandcharles.sgw.model.produto.ProdutoTO;

@Entity
@Table(name="tbitempedido")
public class ItemPedidoTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@NotNull
	@Column(name="intOrdem", nullable=false, length=6)
	private Integer ordem;

	@NotNull
	@Column(name="intQtde", nullable=false, length=6)
	private Integer quantidade = 1;

	@Column(name="intVlrUnitario", nullable=false, precision=12, scale=2)
	private BigDecimal vlrUnitario = BigDecimal.ZERO;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idProduto", nullable=false)
	private ProdutoTO produtoTO;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idPedido", nullable=false)
	private PedidoTO peditoTO;
	

	/* ===========================================================================================*/
	@Transient
	public BigDecimal getCalculaTotalItem(){	
		if (this.getVlrUnitario() != null && this.getQuantidade() != null){
			return this.getVlrUnitario().multiply(new BigDecimal(this.getQuantidade()));
		}
		return BigDecimal.ZERO;
	}
	
	@Transient
	public boolean isProdutoLancado(){
		return this.getProdutoTO() != null && this.getProdutoTO().getId() != null;
	}
	
	@Transient
	public boolean isEstoqueSuficiente(){
		if (this.getProdutoTO().getId() != null){
			return this.produtoTO.getQtdeEstoque() >= this.getQuantidade(); 
		}
		return true;
	}
	/* ===========================================================================================*/

	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getVlrUnitario() {
		return vlrUnitario;
	}
	public void setVlrUnitario(BigDecimal vlrUnitario) {
		this.vlrUnitario = vlrUnitario;
	}
		
	public ProdutoTO getProdutoTO() {
		return produtoTO;
	}
	public void setProdutoTO(ProdutoTO produtoTO) {
		this.produtoTO = produtoTO;
	}
	
	public PedidoTO getPeditoTO() {
		return peditoTO;
	}
	public void setPeditoTO(PedidoTO peditoTO) {
		this.peditoTO = peditoTO;
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
		ItemPedidoTO other = (ItemPedidoTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
