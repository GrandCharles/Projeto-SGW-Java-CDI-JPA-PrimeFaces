package br.com.grandcharles.sgw.security;

/* Classe Bean Spring Security */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.repository.usuario.UsuarioRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
		UsuarioRepository repository = CDIServiceLocator.getBean(UsuarioRepository.class); 
		UsuarioTO usuarioTO = repository.porLogin(userLogin);
		UsuarioSistema user = null;
		
		if (usuarioTO != null){
			user = new UsuarioSistema(usuarioTO, getLstGrupoUsuario(usuarioTO));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getLstGrupoUsuario(UsuarioTO usuarioTO) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (GrupoUsuarioTO lstGrupoUsuario : usuarioTO.getLstGrupoUsuario()){
			authorities.add(new SimpleGrantedAuthority("ROLE_" + lstGrupoUsuario.getNome().toUpperCase()));
			
		}
		return authorities;
	}
	
	

}
