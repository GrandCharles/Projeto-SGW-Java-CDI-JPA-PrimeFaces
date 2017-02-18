package br.com.grandcharles.sgw.repository.sistema;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.grandcharles.sgw.model.sistema.SistemaTO;

public class SistemaRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	
	public SistemaTO porId(Long id){
		return manager.find(SistemaTO.class, id);
	}
	
	
}
