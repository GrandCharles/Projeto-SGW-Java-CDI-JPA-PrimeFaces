package br.com.grandcharles.sgw.service.pedido;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.service.produto.EstoqueService;
import br.com.grandcharles.sgw.util.jpa.Transactional;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

public class PedidoService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EstoqueService service;

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
	
	@Transactional
	public void excluir(PedidoTO pedidoTO){
		if (pedidoTO.isEmitido()){
			throw new NegocioException("Pedido " 
                    + pedidoTO.getId() 
                    + " não pode ser excluído!"
                    + " para o status:"
                    + pedidoTO.getStatusPedido().getDescricao());
		}

		repository.excluir(pedidoTO);
	}
	
	
	
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

	
	
	@Transactional
	public PedidoTO emitir(PedidoTO pedidoTO){
		pedidoTO = this.salvar(pedidoTO);

		if (!pedidoTO.isPedidoEmissivel()){
			throw new NegocioException("Pedido não pode ser emitido com Status: " 
		                                + pedidoTO.getStatusPedido().getDescricao() + ".");
		}
				
		this.service.baixarItensEstoque(pedidoTO);
		
		pedidoTO.setStatusPedido(StatusPedido.EMITIDO);
		
		pedidoTO = this.repository.salvar(pedidoTO);
		
		return pedidoTO;
	}

	
}
