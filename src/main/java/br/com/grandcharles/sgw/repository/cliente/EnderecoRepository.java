package br.com.grandcharles.sgw.repository.cliente;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.cliente.EnderecoTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class EnderecoRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public EnderecoTO salvar(EnderecoTO enderecoTO){
		return this.manager.merge(enderecoTO);
	} 

	public EnderecoTO porId(Long id){
		return manager.find(EnderecoTO.class, id);
	}

	@Transactional
	public void remover(EnderecoTO enderecoTO){
		try {
			enderecoTO = porId(enderecoTO.getId());
			this.manager.remove(enderecoTO);
			this.manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Endereço não pode ser excluído!");
		} 
	}
	
}
