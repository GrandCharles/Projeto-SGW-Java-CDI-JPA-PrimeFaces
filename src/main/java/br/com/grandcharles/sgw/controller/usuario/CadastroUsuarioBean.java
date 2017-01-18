package br.com.grandcharles.sgw.controller.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.GrupoUsuarioRepository;
import br.com.grandcharles.sgw.service.UsuarioService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private GrupoUsuarioRepository repository;
	
	@Inject
	private UsuarioService service; 
	
	private UsuarioTO usuarioTO;
	
	private GrupoUsuarioTO usuarioGrupoUsuarioTO;

	private List<GrupoUsuarioTO> lstGrupoUsuario;

		
	public CadastroUsuarioBean(){
		limpar();
	}
	
	
	public void inicializar(){
		if (FacesUtil.isNotPostback()){
			lstGrupoUsuario = repository.buscarGrupoUsuario();
		}
	}
	

	public void salvar() {
		this.usuarioTO = service.salvar(usuarioTO);
		
		//limpar();

		FacesUtil.addInfoMessage("Usuário salvo com sucesso");
	}


	private void limpar(){
		usuarioTO = new UsuarioTO();
		usuarioGrupoUsuarioTO = null;
		lstGrupoUsuario = new ArrayList<>();
	}

	
	public boolean isEditando() {
		return this.usuarioTO.getId() != null;
	}
	
	
	public UsuarioTO getUsuarioTO() {
		return usuarioTO;
	}
	public void setUsuarioTO(UsuarioTO usuarioTO) {
		this.usuarioTO = usuarioTO;
		
		/*
		if (this.usuarioTO !=null){
			this.grupoUsuarioTO = this.usuarioTO.getGrupoUsuarioTO().getGrupoUsuarioTO();
		}
		*/
	}


	//@NotNull
	public GrupoUsuarioTO getUsuarioGrupoUsuarioTO() {
		return usuarioGrupoUsuarioTO;
	}
	public void setUsuarioGrupoUsuarioTO(GrupoUsuarioTO usuarioGrupoUsuarioTO) {
		this.usuarioGrupoUsuarioTO = usuarioGrupoUsuarioTO;
	}


	public List<GrupoUsuarioTO> getLstGrupoUsuario() {
		return lstGrupoUsuario;
	}
	public void setLstGrupoUsuario(List<GrupoUsuarioTO> lstGrupoUsuario) {
		this.lstGrupoUsuario = lstGrupoUsuario;
	}
	
}