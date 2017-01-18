package br.com.grandcharles.sgw.controller.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.filter.UsuarioFilter;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.UsuarioRepository;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository repository;
	
	private UsuarioTO usuarioSelecionado;
	
	private UsuarioFilter filter;
	
	private List<UsuarioTO> lstUsuarioFiltrado;


	public PesquisaUsuarioBean(){
		filter = new UsuarioFilter();
		lstUsuarioFiltrado = new ArrayList<>();
	}
	
	
	public void pesquisar(){
		lstUsuarioFiltrado = repository.pesquisa(filter);
	}
	
	public void excluir(){
		repository.remover(usuarioSelecionado);
		lstUsuarioFiltrado.remove(usuarioSelecionado);
		FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso!");
	}


	public UsuarioFilter getFilter() {
		return filter;
	}
	public void setFilter(UsuarioFilter filter) {
		this.filter = filter;
	}


	public List<UsuarioTO> getLstUsuarioFiltrado() {
		return lstUsuarioFiltrado;
	}
	public void setLstUsuarioFiltrado(List<UsuarioTO> lstUsuarioFiltrado) {
		this.lstUsuarioFiltrado = lstUsuarioFiltrado;
	}


	public UsuarioTO getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	public void setUsuarioSelecionado(UsuarioTO usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	
}
