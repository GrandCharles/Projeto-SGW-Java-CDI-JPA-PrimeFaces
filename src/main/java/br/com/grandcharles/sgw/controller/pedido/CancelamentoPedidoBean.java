package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.service.pedido.PedidoService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService service;
	
	@Inject
	private Event<PedidoEvent> event;
	
	@Inject
	@PedidoEdicao
	private PedidoTO pedidoTO;
	
	public void cancelarPedido(){
		this.pedidoTO = this.service.cancelar(this.pedidoTO);
		this.event.fire(new PedidoEvent(this.pedidoTO));
		
		FacesUtil.addInfoMessage("Pedido cancelado com sucesso");
	}
}
