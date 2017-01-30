package br.com.grandcharles.sgw.controller.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import br.com.grandcharles.sgw.model.produto.CategoriaTO;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.repository.produto.CategoriasRepository;
import br.com.grandcharles.sgw.service.produto.ProdutoService;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriasRepository repository;
	
	@Inject
	private ProdutoService service; 
	
	private ProdutoTO produtoTO;
	
	@NotNull
	private CategoriaTO categoriaPai;

	private List<CategoriaTO> lstCategoriasPai;
	private List<CategoriaTO> lstSubCategorias;

		
	public CadastroProdutoBean(){
		limpar();
	}
	
	
	private void limpar(){
		produtoTO = new ProdutoTO();
		categoriaPai = null;
		lstSubCategorias = new ArrayList<>();
	}

	
	public void inicializar(){
		if (FacesUtil.isNotPostback()){
			lstCategoriasPai = repository.buscarCategoria();
		}
		
		if (this.categoriaPai != null){
			carregarSubCategoria();
		}
	}
	
	public void carregarSubCategoria(){
		lstSubCategorias = repository.buscarSubCategorias(categoriaPai);
	}
	
	
	public void salvar() {
		this.produtoTO = service.salvar(produtoTO);
		
		//limpar();

		FacesUtil.addInfoMessage("Produto salvo com sucesso");
	}

	
	public boolean isEditando() {
		return this.produtoTO.getId() != null;
	}
	
	
	public ProdutoTO getProdutoTO() {
		return produtoTO;
	}
	public void setProdutoTO(ProdutoTO produtoTO) {
		this.produtoTO = produtoTO;
		
		if (this.produtoTO != null){
			this.categoriaPai = this.produtoTO.getCategoriaTO().getCategoriaPai();
		}
	}


	public CategoriaTO getCategoriaPai() {
		return categoriaPai;
	}
	public void setCategoriaPai(CategoriaTO categoriaPai) {
		this.categoriaPai = categoriaPai;
	}


	public List<CategoriaTO> getLstCategoriasPai() {
		return lstCategoriasPai;
	}
	public void setLstCategoriasPai(List<CategoriaTO> lstCategoriasPai) {
		this.lstCategoriasPai = lstCategoriasPai;
	}
	
	
	public List<CategoriaTO> getLstSubCategorias() {
		return lstSubCategorias;
	}
	public void setLstSubCategorias(List<CategoriaTO> lstSubCategorias) {
		this.lstSubCategorias = lstSubCategorias;
	}

}
