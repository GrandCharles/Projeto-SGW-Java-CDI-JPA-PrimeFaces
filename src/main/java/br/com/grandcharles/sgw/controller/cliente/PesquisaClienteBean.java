package br.com.grandcharles.sgw.controller.cliente;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.filter.ClienteFilter;
import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.repository.ClienteRepository;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClienteBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository repository;
	
	private ClienteTO clienteSelecionado;
	
	private ClienteFilter filter;
	
	private List<ClienteTO> lstClienteFiltrado;


	public PesquisaClienteBean(){
		filter = new ClienteFilter();
	}
	
	
	public void pesquisar(){
		lstClienteFiltrado = repository.pesquisa(filter);
	}
	
	public void excluir(){
		repository.remover(clienteSelecionado);
		lstClienteFiltrado.remove(clienteSelecionado);
		FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluído com sucesso!");
	}


	public ClienteFilter getFilter() {
		return filter;
	}
	public void setFilter(ClienteFilter filter) {
		this.filter = filter;
	}


	public List<ClienteTO> getLstClienteFiltrado() {
		return lstClienteFiltrado;
	}
	public void setLstClienteFiltrado(List<ClienteTO> lstClienteFiltrado) {
		this.lstClienteFiltrado = lstClienteFiltrado;
	}


	public ClienteTO getClienteSelecionado() {
		return clienteSelecionado;
	}
	public void setClienteSelecionado(ClienteTO clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	
}
