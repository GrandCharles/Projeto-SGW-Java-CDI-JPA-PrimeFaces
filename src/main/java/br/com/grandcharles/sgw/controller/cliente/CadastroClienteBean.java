package br.com.grandcharles.sgw.controller.cliente;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.cliente.EnderecoTO;
import br.com.grandcharles.sgw.repository.cliente.EnderecoRepository;
import br.com.grandcharles.sgw.service.CepWebService;
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
		if (this.clienteTO == null) {
			limpar();
		}			
		
		if (FacesUtil.isNotPostback()){
			prepararNovoEndereco();
		}
	}

	private void limpar(){
		this.clienteTO = new ClienteTO();
		this.enderecoTO = new EnderecoTO();
	}	
	
	public void prepararNovoEndereco() {
		   this.enderecoTO = new EnderecoTO();
	}
	
	public void salvar(){
	    this.enderecoTO = new EnderecoTO();
		this.clienteTO = clienteService.salvar(clienteTO);
		
		//limpar();

		FacesUtil.addInfoMessage("Cliente salvo com sucesso");
	}
	
	
	
	public void salvarEndereco(){
		boolean novo = isNovoEndereco();
		
		this.enderecoTO.setClienteTO(clienteTO);
		this.enderecoTO = enderecoService.salvar(enderecoTO);
		if (novo) {
			this.clienteTO.getLstEndereco().add(enderecoTO);
			FacesUtil.addInfoMessage("Endereço incluído com sucesso");
		} else {
			FacesUtil.addInfoMessage("Endereço alterado com sucesso");
		}
	}

	public void excluirEndereco(){
		this.enderecoRepository.remover(enderecoTO);
		this.clienteTO.getLstEndereco().remove(enderecoTO);

		FacesUtil.addInfoMessage("Endereço " + enderecoTO.getLogradouro() + " excluído com sucesso!");
	}
	
	
	public void pesquisarCep(){
		try {
			CepWebService cws = new CepWebService(enderecoTO.getCep().replace("-", ""));
			
			 if (cws.getResultado() == 1) {
				 enderecoTO.setTipoLogradouro(cws.getTipoLogradouro());
				 enderecoTO.setLogradouro(cws.getLogradouro());
				 enderecoTO.setUf(cws.getEstado());
				 enderecoTO.setCidade(cws.getCidade());
				 enderecoTO.setBairro(cws.getBairro());
		        } else {
					FacesUtil.addErrorMessage("CEp não encontrado!");
		        }
			 
		} catch (Exception e) {

			FacesUtil.addErrorMessage("Servidor não está respondendo!");
		}		
		
	}
	
	
	public boolean isNovoCliente() {
		return this.clienteTO.getId() == null;
	}	
	public boolean isNovoEndereco() {
		return this.enderecoTO.getId() == null;
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
