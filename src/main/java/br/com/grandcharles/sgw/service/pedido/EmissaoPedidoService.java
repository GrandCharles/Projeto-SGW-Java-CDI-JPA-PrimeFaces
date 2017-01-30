package br.com.grandcharles.sgw.service.pedido;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.service.produto.EstoqueService;
import br.com.grandcharles.sgw.util.jpa.Transactional;


public class EmissaoPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Inject
	private PedidoRepository repository;
	
	@Transactional
	public PedidoTO emitir(PedidoTO pedidoTO){
		pedidoTO = this.cadastroPedidoService.salvar(pedidoTO);

		if (!pedidoTO.isPedidoEmissivel()){
			throw new NegocioException("Pedido não pode ser emitido com Status: " 
		                                + pedidoTO.getStatusPedido().getDescricao() + ".");
		}
				
		this.estoqueService.baixarItensEstoque(pedidoTO);
		
		pedidoTO.setStatusPedido(StatusPedido.EMITIDO);
		
		pedidoTO = this.repository.salvar(pedidoTO);
		
		return pedidoTO;
	}
}
