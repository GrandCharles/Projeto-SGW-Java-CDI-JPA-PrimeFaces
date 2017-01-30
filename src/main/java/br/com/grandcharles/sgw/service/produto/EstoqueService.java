package br.com.grandcharles.sgw.service.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.pedido.ItemPedidoTO;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class EstoqueService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository repository;
	
	@Transactional
	public void baixarItensEstoque(PedidoTO pedidoTO){
		pedidoTO = this.repository.porId(pedidoTO.getId());
		
		for (ItemPedidoTO item : pedidoTO.getLstItemPedido()){
			item.getProdutoTO().baixarItemEstoque(item.getQuantidade());
		}
	}

	@Transactional
	public void retornarItensEstoque(PedidoTO pedidoTO) {
		pedidoTO = this.repository.porId(pedidoTO.getId());
		
		for (ItemPedidoTO item : pedidoTO.getLstItemPedido()){
			item.getProdutoTO().adicionarItemEstoque(item.getQuantidade());
		}
	}
	
}
