package br.com.grandcharles.sgw.filter;

import java.io.Serializable;
import java.util.Date;

import br.com.grandcharles.sgw.model.pedido.StatusPedido;

public class PedidoFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long nroPedInicial;
	private Long nroPedFinal;
	private Date dtPedInicial;
	private Date dtPedFinal;
	private String nomeUsuario;
	private String nomeCliente;
	
	private StatusPedido[] statusPedido;
			
	public Long getNroPedInicial() {
		return nroPedInicial;
	}
	public void setNroPedInicial(Long nroPedInicial) {
		this.nroPedInicial = nroPedInicial;
	}
	
	
	public Long getNroPedFinal() {
		return nroPedFinal;
	}
	public void setNroPedFinal(Long nroPedFinal) {
		this.nroPedFinal = nroPedFinal;
	}
	
	
	public Date getDtPedInicial() {
		return dtPedInicial;
	}
	public void setDtPedInicial(Date dtPedInicial) {
		this.dtPedInicial = dtPedInicial;
	}
	
	
	public Date getDtPedFinal() {
		return dtPedFinal;
	}
	public void setDtPedFinal(Date dtPedFinal) {
		this.dtPedFinal = dtPedFinal;
	}
	
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	
	public StatusPedido[] getStatusPedido() {
		return statusPedido;
	}
	public void setStatusPedido(StatusPedido[] statusPedido) {
		this.statusPedido = statusPedido;
	}
	
}
