package br.com.grandcharles.sgw.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.repository.GrupoUsuarioRepository;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class GrupoUsuarioService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoUsuarioRepository repository;
	
	@Transactional
	public GrupoUsuarioTO salvar(GrupoUsuarioTO grupoUsuarioTO){
		GrupoUsuarioTO existe = repository.existeNome(grupoUsuarioTO.getNome());
		
		if (existe != null && !existe.equals(grupoUsuarioTO)){
			throw new NegocioException("Nome do Grupo de Usuário já existente!");
		}
		return repository.salvar(grupoUsuarioTO);
	}
	
}
