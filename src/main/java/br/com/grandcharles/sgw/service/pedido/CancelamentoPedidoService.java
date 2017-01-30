package br.com.grandcharles.sgw.service.pedido;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.service.produto.EstoqueService;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository repository;
	
	@Inject
	private EstoqueService service;
	
	@Transactional
	public PedidoTO cancelar(PedidoTO pedidoTO) {
		pedidoTO = this.repository.porId(pedidoTO.getId());
		
		if (!pedidoTO.isPedidoCancelavel()){
			throw new NegocioException("Pedido não pode ser cancelado no status " 
					                   + pedidoTO.getStatusPedido().getDescricao() + ".");
		}
	
		if (pedidoTO.isEmitido()){
			this.service.retornarItensEstoque(pedidoTO);
		}
		
		pedidoTO.setStatusPedido(StatusPedido.CANCELADO);
		
		pedidoTO = this.repository.salvar(pedidoTO);
		
		return pedidoTO;
	}

	
}
