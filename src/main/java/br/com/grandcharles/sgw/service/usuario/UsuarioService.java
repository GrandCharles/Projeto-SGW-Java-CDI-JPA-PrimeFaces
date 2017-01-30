package br.com.grandcharles.sgw.service.usuario;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.usuario.UsuarioRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class UsuarioService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository repository;
	
	@Transactional
	public UsuarioTO salvar(UsuarioTO usuarioTO){
		UsuarioTO existe = repository.existeLogin(usuarioTO.getLogin());
		
		if (existe != null && !existe.equals(usuarioTO)){
			throw new NegocioException("Login de usuário já existente!");
		}
		return repository.salvar(usuarioTO);
	}
	
}
