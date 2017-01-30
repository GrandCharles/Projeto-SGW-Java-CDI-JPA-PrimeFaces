package br.com.grandcharles.sgw.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;

@Entity
@Table(name="tbpedido")
public class PedidoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dtDataCriacao", nullable=false)
	private Date dtCriacao = new Date();

	@Column(name="strObservacao", columnDefinition="text")
	private String observacao;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="dtDataEntrega", nullable=false)
	private Date dtEntrega;

	@NotNull
	@Column(name="intVlrFrete", precision=12, scale=2)
	private BigDecimal vlrFrete = BigDecimal.ZERO;

	@NotNull
	@Column(name="intVlrDesconto", precision=12, scale=2)
	private BigDecimal vlrDesconto = BigDecimal.ZERO;

	@NotNull
	@Column(name="intVlrTotal", nullable=false, precision=12, scale=2)
	private BigDecimal vlrTotal = BigDecimal.ZERO;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="strStatus", length=10, nullable=false)
	private StatusPedido statusPedido = StatusPedido.ORCAMENTO;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="strFormaPagamento", length=15, nullable=false)
	private FormaPagamento formaPagamento;
	
	//@NotNull
	@ManyToOne
	@JoinColumn(name="idUsuario", nullable=false)
	private UsuarioTO usuarioTO;

	//@NotNull
	@ManyToOne
	@JoinColumn(name="idCliente", nullable=false)
	private ClienteTO clienteTO;

	@Embedded
	private EnderecoEntregaTO enderecoEntregaTO;
	
	@OneToMany(mappedBy = "peditoTO", 
			   cascade = CascadeType.ALL, 
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY, 
			   targetEntity = ItemPedidoTO.class)
	private List<ItemPedidoTO> lstItemPedido = new ArrayList<>();
	
	
	/* ===========================================================================================*/
	@Transient
	public boolean isNovo(){
		return getId() == null;
	}
	@Transient
	public boolean isOrcamento(){
		return StatusPedido.ORCAMENTO.equals(this.getStatusPedido());
	}
	@Transient
	public boolean isEmitido(){
		return StatusPedido.EMITIDO.equals(this.getStatusPedido());
	}
	@Transient
	public boolean isCancelado(){
		return StatusPedido.CANCELADO.equals(this.getStatusPedido());
	}
	
	
	@Transient
	public boolean isPedidoNegativo(){
		return this.getVlrTotal().compareTo(BigDecimal.ZERO) < 0;
	}
	@Transient
	public boolean isPedidoEmissivel(){
		return !this.isNovo() && this.isOrcamento();
	}
	@Transient
	public boolean isPedidoCancelavel(){
		return !this.isNovo() && this.isEmitido();
	}
	@Transient
	public boolean isPedidoAlteravel(){
		return this.isOrcamento();
	}

	
	
	@Transient
	public BigDecimal getCalcularSubTotal(){
		return this.getVlrTotal().subtract(this.getVlrFrete()).add(this.getVlrDesconto());
	}
	
	public void adicionarItemVazio() {
		Integer ordem = 1;

		if (this.isOrcamento()){
			for (ItemPedidoTO item : this.getLstItemPedido()){
				if (item.getProdutoTO() != null && item.getProdutoTO().getId() != null) {
					ordem++;
				}
			}
			
			ProdutoTO produtoTO = new ProdutoTO();
			ItemPedidoTO item = new ItemPedidoTO();
			item.setOrdem(ordem);
			item.setQuantidade(1);
			item.setProdutoTO(produtoTO);
			item.setPeditoTO(this);
			this.getLstItemPedido().add(0,item);
		}
	}

	public void atualizaOrdem(){
		Integer ordem = 0;
		for (ItemPedidoTO item : this.getLstItemPedido()){
			if (item.getProdutoTO() != null && item.getProdutoTO().getId() != null) {
				ordem++;
				item.setOrdem(ordem);
			}
		}
	}

	public void removerItemVazio(){
		ItemPedidoTO item = this.getLstItemPedido().get(0);
		if (item != null && item.getProdutoTO().getId() == null){
			this.getLstItemPedido().remove(0);
		}	
	}
	
	
	public void calculoTotalPedido(){
		BigDecimal total = BigDecimal.ZERO;

		total = total.add(this.getVlrFrete()).subtract(this.getVlrDesconto());
		
		for (ItemPedidoTO item : this.getLstItemPedido()){
			if (item.getProdutoTO() != null && item.getProdutoTO().getId() != null) {
				total = total.add(item.getCalculaTotalItem());
			}
		}
		this.setVlrTotal(total);
	}
	/* ========================================================================================== */	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Date getDtEntrega() {
		return dtEntrega;
	}
	public void setDtEntrega(Date dtEntrega) {
		this.dtEntrega = dtEntrega;
	}
	
	public BigDecimal getVlrFrete() {
		return vlrFrete;
	}
	public void setVlrFrete(BigDecimal vlrFrete) {
		this.vlrFrete = vlrFrete;
	}
	
	public BigDecimal getVlrDesconto() {
		return vlrDesconto;
	}
	public void setVlrDesconto(BigDecimal vlrDesconto) {
		this.vlrDesconto = vlrDesconto;
	}
	
	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}
	public void setVlrTotal(BigDecimal vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}
	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public UsuarioTO getUsuarioTO() {
		return usuarioTO;
	}
	public void setUsuarioTO(UsuarioTO usuarioTO) {
		this.usuarioTO = usuarioTO;
	}

	public ClienteTO getClienteTO() {
		return clienteTO;
	}
	public void setClienteTO(ClienteTO clienteTO) {
		this.clienteTO = clienteTO;
	}	
	
	public EnderecoEntregaTO getEnderecoEntregaTO() {
		return enderecoEntregaTO;
	}
	public void setEnderecoEntregaTO(EnderecoEntregaTO enderecoEntregaTO) {
		this.enderecoEntregaTO = enderecoEntregaTO;
	}
	
	public List<ItemPedidoTO> getLstItemPedido() {
		return lstItemPedido;
	}
	public void setLstItemPedido(List<ItemPedidoTO> lstItemPedido) {
		this.lstItemPedido = lstItemPedido;
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
		PedidoTO other = (PedidoTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
	
}
