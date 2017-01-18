package br.com.grandcharles.sgw.controller.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.repository.ClienteRepository;
import br.com.grandcharles.sgw.service.ClienteService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository repository;
	
	@Inject
	private ClienteService service; 

	private ClienteTO clienteTO;

	
	public CadastroClienteBean(){
		limpar();
	}
	

	public void inicializar(){
	}
	
	
	public void salvar(){
		this.clienteTO = service.salvar(clienteTO);
		
		//limpar();

		FacesUtil.addInfoMessage("Cliente salvo com sucesso");
	}
	
	private void limpar(){
		clienteTO = new ClienteTO();
	}	
	
	
	public boolean isEditando() {
		return this.clienteTO.getId() != null;
	}	
	
	
	public ClienteTO getClienteTO() {
		return clienteTO;
	}
	public void setClienteTO(ClienteTO clienteTO) {
		this.clienteTO = clienteTO;
	}

	
	
}
