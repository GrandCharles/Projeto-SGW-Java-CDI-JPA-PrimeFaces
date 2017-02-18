package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.service.pedido.PedidoService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;
import br.com.grandcharles.sgw.model.pedido.StatusPedido;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoService service;

	@Inject
	private PedidoRepository repository;

	private PedidoFilter filter;
	
	private PedidoTO pedidoTO;
	
	private List<PedidoTO> lstPedidoFiltrado;
	
	
	public PesquisaPedidoBean() {
		filter = new PedidoFilter();
		lstPedidoFiltrado = new ArrayList<>();
	}

	public void pesquisarFiltro(){
		lstPedidoFiltrado = repository.pesquisaFiltro(filter);
	}
	

	public void excluir(){
		this.service.excluir(pedidoTO);
		
		lstPedidoFiltrado.remove(pedidoTO);
		
		FacesUtil.addInfoMessage("Pedido excluído com sucesso!");
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


	public PedidoTO getPedidoTO() {
		return pedidoTO;
	}
	public void setPedidoTO(PedidoTO pedidoTO) {
		this.pedidoTO = pedidoTO;
	}
	
}
