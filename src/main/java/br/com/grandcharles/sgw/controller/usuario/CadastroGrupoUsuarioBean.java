package br.com.grandcharles.sgw.controller.usuario;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.service.usuario.GrupoUsuarioService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroGrupoUsuarioBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoUsuarioService service; 
	
	private GrupoUsuarioTO grupoUsuarioTO;
	

	public CadastroGrupoUsuarioBean(){
		limpar();
	}
	
	public void inicializar(){
		if (this.grupoUsuarioTO == null) {
			limpar();
		}	
	}
	
	private void limpar(){
		grupoUsuarioTO = new GrupoUsuarioTO();
	}


	
	public void salvar() {
		this.grupoUsuarioTO = service.salvar(grupoUsuarioTO);
		//limpar();
		FacesUtil.addInfoMessage("Grupo de usuário salvo com sucesso");
	}


	public boolean isNovo() {
		return this.grupoUsuarioTO.getId() == null;
	}
	
	
	public GrupoUsuarioTO getGrupoUsuarioTO() {
		return grupoUsuarioTO;
	}
	public void setGrupoUsuarioTO(GrupoUsuarioTO grupoUsuarioTO) {
		this.grupoUsuarioTO = grupoUsuarioTO;
	}

}
