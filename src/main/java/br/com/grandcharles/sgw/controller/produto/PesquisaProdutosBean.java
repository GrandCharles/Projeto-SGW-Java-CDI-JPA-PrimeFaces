package br.com.grandcharles.sgw.controller.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.grandcharles.sgw.filter.ProdutoFilter;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.repository.produto.ProdutoRepository;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoRepository repository;
	
	private ProdutoTO produtoSelecionado;
	
	private ProdutoFilter filter;
	
	private List<ProdutoTO> lstProdutosFiltrados;


	public PesquisaProdutosBean(){
		filter = new ProdutoFilter();
		lstProdutosFiltrados = new ArrayList<>();
	}
	
	
	public void pesquisarFiltro(){
		lstProdutosFiltrados = repository.pesquisaFiltro(filter);
	}
	
	public void excluir(){
		repository.remover(produtoSelecionado);
		lstProdutosFiltrados.remove(produtoSelecionado);
		FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluído com sucesso!");
	}


	public ProdutoFilter getFilter() {
		return filter;
	}
	public void setFilter(ProdutoFilter filter) {
		this.filter = filter;
	}


	public List<ProdutoTO> getLstProdutosFiltrados() {
		return lstProdutosFiltrados;
	}
	public void setLstProdutosFiltrados(List<ProdutoTO> lstProdutosFiltrados) {
		this.lstProdutosFiltrados = lstProdutosFiltrados;
	}


	public ProdutoTO getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public void setProdutoSelecionado(ProdutoTO produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	
}
