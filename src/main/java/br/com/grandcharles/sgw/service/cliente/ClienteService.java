package br.com.grandcharles.sgw.service.cliente;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.repository.cliente.ClienteRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class ClienteService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository repository;
	
	@Transactional
	public ClienteTO salvar(ClienteTO clienteTO){
		ClienteTO existe = repository.existeNome(clienteTO.getNome());
		
		if (existe != null && !existe.equals(clienteTO)){
			throw new NegocioException("Cliente já existente!");
		}
		return repository.salvar(clienteTO);
	}
	
}
