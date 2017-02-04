package br.com.grandcharles.sgw.controller.cliente;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.cliente.EnderecoTO;
import br.com.grandcharles.sgw.repository.cliente.EnderecoRepository;
import br.com.grandcharles.sgw.service.cliente.ClienteService;
import br.com.grandcharles.sgw.service.cliente.EnderecoService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService; 

	@Inject
	private EnderecoService enderecoService; 
	
	@Inject
	private EnderecoRepository enderecoRepository;

	private ClienteTO clienteTO;
	
	private EnderecoTO enderecoTO;

	
	public CadastroClienteBean(){
		limpar();
	}
	

	public void inicializar(){
		if (FacesUtil.isNotPostback()){
			//prepararNovoEndereco();
		}
	}
	
	
	public void salvar(){
	    this.enderecoTO = new EnderecoTO();
		this.clienteTO = clienteService.salvar(clienteTO);
		
		//limpar();

		FacesUtil.addInfoMessage("Cliente salvo com sucesso");
	}
	
	public void prepararNovoEndereco() {
		   this.enderecoTO = new EnderecoTO();
	}
	
	
	public void salvarEndereco(){
		this.enderecoTO.setClienteTO(clienteTO);
		this.enderecoTO = enderecoService.salvar(enderecoTO);
		this.clienteTO.getLstEndereco().add(enderecoTO);
		
		FacesUtil.addInfoMessage("Endereço salvo com sucesso");
	}

	public void excluirEndereco(){
		this.enderecoRepository.remover(enderecoTO);
		this.clienteTO.getLstEndereco().remove(enderecoTO);

		FacesUtil.addInfoMessage("Endereço " + enderecoTO.getLogradouro() + " excluído com sucesso!");
	}
	
	private void limpar(){
		this.clienteTO = new ClienteTO();
		this.enderecoTO = new EnderecoTO();
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


	public EnderecoTO getEnderecoTO() {
		return enderecoTO;
	}
	public void setEnderecoTO(EnderecoTO enderecoTO) {
		this.enderecoTO = enderecoTO;
	}

	
	
}
