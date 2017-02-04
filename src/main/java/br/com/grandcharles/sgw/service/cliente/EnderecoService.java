package br.com.grandcharles.sgw.service.cliente;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.cliente.EnderecoTO;
import br.com.grandcharles.sgw.repository.cliente.EnderecoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class EnderecoService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EnderecoRepository repository;

	
	@Transactional
	public EnderecoTO salvar(EnderecoTO enderecoTO){

		return repository.salvar(enderecoTO);
	}	
}
