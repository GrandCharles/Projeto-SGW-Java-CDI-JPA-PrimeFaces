package br.com.grandcharles.sgw.service.pedido;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository repository;
	
	@Transactional
	public PedidoTO salvar(PedidoTO pedidoTO){
		if (pedidoTO.isNovo()){
			pedidoTO.setDtCriacao(new Date());
			pedidoTO.setStatusPedido(StatusPedido.ORCAMENTO);
		}
		pedidoTO.calculoTotalPedido();
	
		if (!pedidoTO.isPedidoAlteravel()){
			throw new NegocioException("Pedido não pode ser alterado no status " 
					                   + pedidoTO.getStatusPedido().getDescricao() + ".");
		}
		
		if (pedidoTO.getLstItemPedido().isEmpty()){
			throw new NegocioException("Pedido deve conter pelo menos um item lançado!");
		}

		if (pedidoTO.isPedidoNegativo()){
			throw new NegocioException("Pedido não pode ter valor total negativo!");
		}

		pedidoTO = repository.salvar(pedidoTO);
		
		return pedidoTO;
		//return repository.salvar(pedidoTO);
	}
	


}
