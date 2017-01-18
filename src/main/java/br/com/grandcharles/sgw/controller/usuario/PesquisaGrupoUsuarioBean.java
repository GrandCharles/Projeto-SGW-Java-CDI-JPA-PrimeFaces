package br.com.grandcharles.sgw.controller.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.filter.GrupoUsuarioFilter;
import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.repository.GrupoUsuarioRepository;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaGrupoUsuarioBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoUsuarioRepository repository;
	
	private GrupoUsuarioFilter filter;

	private GrupoUsuarioTO grupoUsuarioSelecionado;
	
	private List<GrupoUsuarioTO> lstGrupoUsuarioFiltrado;


	public PesquisaGrupoUsuarioBean(){
		filter = new GrupoUsuarioFilter();
		lstGrupoUsuarioFiltrado = new ArrayList<>();
	}
	
	
	public void pesquisar(){
		lstGrupoUsuarioFiltrado = repository.pesquisa(filter);
	}
	
	public void excluir(){
		repository.remover(grupoUsuarioSelecionado);
		lstGrupoUsuarioFiltrado.remove(grupoUsuarioSelecionado);
		FacesUtil.addInfoMessage("Grupode Usuário " + grupoUsuarioSelecionado.getNome() + " excluído com sucesso!");
	}


	public GrupoUsuarioFilter getFilter() {
		return filter;
	}
	public void setFilter(GrupoUsuarioFilter filter) {
		this.filter = filter;
	}


	public List<GrupoUsuarioTO> getLstGrupoUsuarioFiltrado() {
		return lstGrupoUsuarioFiltrado;
	}
	public void setLstGrupoUsuarioFiltrado(List<GrupoUsuarioTO> lstGrupoUsuarioFiltrado) {
		this.lstGrupoUsuarioFiltrado = lstGrupoUsuarioFiltrado;
	}


	public GrupoUsuarioTO getGrupoUsuarioSelecionado() {
		return grupoUsuarioSelecionado;
	}
	public void setGrupoUsuarioSelecionado(GrupoUsuarioTO grupoUsuarioSelecionado) {
		this.grupoUsuarioSelecionado = grupoUsuarioSelecionado;
	}

	
}
