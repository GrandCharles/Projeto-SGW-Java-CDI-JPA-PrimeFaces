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
public class EmissaoPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService service;
	
	@Inject
	@PedidoEdicao
	private PedidoTO pedidoTO;

	@Inject
	private Event<PedidoEvent> event;
	
	
	public void emitirPedido(){
		this.pedidoTO.removerItemVazio();
		
		try {
			this.pedidoTO = this.service.emitir(this.pedidoTO);
			
			//Lançando um evento CDI para atualizar o objeto peditoTO no cadastroPedidoBean
			this.event.fire(new PedidoEvent(this.pedidoTO));
			
			FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
			
		} finally {
			this.pedidoTO.adicionarItemVazio();
		}
		
	}
	
	
}
