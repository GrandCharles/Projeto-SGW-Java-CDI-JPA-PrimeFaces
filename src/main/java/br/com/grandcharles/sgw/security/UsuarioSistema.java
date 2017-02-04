package br.com.grandcharles.sgw.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.grandcharles.sgw.model.usuario.UsuarioTO;

public class UsuarioSistema extends User {
	private static final long serialVersionUID = 1L;

	private UsuarioTO usuarioTO;
	
	public UsuarioSistema(UsuarioTO usuarioTO,
			              Collection<? extends GrantedAuthority> authorities) {
		
		super(usuarioTO.getLogin(), usuarioTO.getSenha(), authorities);
		this.usuarioTO = usuarioTO;
	}

	
	
	
	public UsuarioTO getUsuarioTO() {
		return usuarioTO;
	}
	public void setUsuarioTO(UsuarioTO usuarioTO) {
		this.usuarioTO = usuarioTO;
	}

}
