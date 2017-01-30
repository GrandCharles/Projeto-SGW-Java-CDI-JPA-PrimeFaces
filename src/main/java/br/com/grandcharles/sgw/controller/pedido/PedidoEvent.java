package br.com.grandcharles.sgw.controller.pedido;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;

public class PedidoEvent {
	
	PedidoTO pedidoTO;

	public PedidoEvent(PedidoTO pedidoTO) {
		this.pedidoTO = pedidoTO;
	}

	
	public PedidoTO getPedidoTO() {
		return pedidoTO;
	}
	public void setPedidoTO(PedidoTO pedidoTO) {
		this.pedidoTO = pedidoTO;
	}
	
}
