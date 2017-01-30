package br.com.grandcharles.sgw.service.produto;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.repository.produto.ProdutoRepository;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class ProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository repository;
	
	@Transactional
	public ProdutoTO salvar(ProdutoTO produtoTO){
		ProdutoTO existe = repository.existeSKU(produtoTO.getSku());
		
		if (existe != null && !existe.equals(produtoTO)){
			throw new NegocioException("SKU já existente!");
		}
		return repository.salvar(produtoTO);
	}
	
	
	
	
}
