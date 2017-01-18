package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.pedido.EnderecoEntregaTO;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	/*
	private PedidoTO pedidoTO;
	private List<Integer> itensPedidoTO;
	
	public CadastroPedidoBean() {
		pedidoTO = new PedidoTO();
		
		//pedidoTO.setEnderecoEntregaTO(new EnderecoEntregaTO());
		
		itensPedidoTO = new ArrayList<>();
		itensPedidoTO.add(1);
	}


	
	public PedidoTO getPedidoTO() {
		return pedidoTO;
	}
	public void setPedidoTO(PedidoTO pedidoTO) {
		this.pedidoTO = pedidoTO;
	}


	public List<Integer> getItensPedidoTO() {
		return itensPedidoTO;
	}
	public void setItensPedidoTO(List<Integer> itensPedidoTO) {
		this.itensPedidoTO = itensPedidoTO;
	}



	public void salvar(){
		
		
	}
*/
}
