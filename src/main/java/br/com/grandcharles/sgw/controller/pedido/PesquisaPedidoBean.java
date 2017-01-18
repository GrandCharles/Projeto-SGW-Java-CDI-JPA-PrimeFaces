package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.repository.PedidoRepository;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository repository;

	private PedidoFilter filter;
	
	private List<PedidoTO> lstPedidoFiltrado;
	
	
	public PesquisaPedidoBean() {
		filter = new PedidoFilter();
		lstPedidoFiltrado = new ArrayList<>();
	}

	public void pesquisar(){
		lstPedidoFiltrado = repository.pesquisa(filter);
	}
	
	public StatusPedido[] getStatusPedido(){
		return StatusPedido.values();
	}
	
		
	public List<PedidoTO> getLstPedidoFiltrado() {
		return lstPedidoFiltrado;
	}
	public void setLstPedidoFiltrado(List<PedidoTO> lstPedidoFiltrado) {
		this.lstPedidoFiltrado = lstPedidoFiltrado;
	}


	public PedidoFilter getFilter() {
		return filter;
	}
	public void setFilter(PedidoFilter filter) {
		this.filter = filter;
	}

}
