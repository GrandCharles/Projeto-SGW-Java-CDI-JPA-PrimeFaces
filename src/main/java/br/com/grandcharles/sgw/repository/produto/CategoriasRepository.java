package br.com.grandcharles.sgw.repository.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.grandcharles.sgw.model.produto.CategoriaTO;

public class CategoriasRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public List<CategoriaTO> buscarCategoria(){
		/* JPQL - Java Procedure Query Language. Usada pela JPA*/
		return manager.createQuery("from CategoriaTO where categoriaPai is null",
				CategoriaTO.class).getResultList();
	}
	
	public List<CategoriaTO> buscarSubCategorias(CategoriaTO categoriaPai){
		return manager.createQuery("from CategoriaTO where categoriaPai=:id",CategoriaTO.class)
				.setParameter("id",categoriaPai)
				.getResultList();
	}

	public CategoriaTO porId(Long id){
		return manager.find(CategoriaTO.class, id);
	}
}
