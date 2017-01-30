package br.com.grandcharles.sgw.repository.produto;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.grandcharles.sgw.filter.ProdutoFilter;
import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class ProdutoRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public ProdutoTO salvar(ProdutoTO produtoTO){
		return manager.merge(produtoTO);
	} 
	
	@Transactional
	public void remover(ProdutoTO produtoTO){
		try {
			produtoTO = porId(produtoTO.getId());
			manager.remove(produtoTO);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Produto não pode ser excluído!");
		} 
	}

	@SuppressWarnings("unchecked")
	public List<ProdutoTO> pesquisaFiltro(ProdutoFilter filtro){
		/* Pilha hibernate*/
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ProdutoTO.class);
		
		if (StringUtils.isNotBlank(filtro.getSku())){
			criteria.add(Restrictions.eq("sku",filtro.getSku()));
		}
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getDescricao())){
			criteria.add(Restrictions.ilike("descricao",filtro.getDescricao(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("descricao")).list();
	}
	
	
	public ProdutoTO existeSKU(String sku){
		try {
		return manager.createQuery("from ProdutoTO where upper(sku)=:valor",ProdutoTO.class)
				.setParameter("valor", sku.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public ProdutoTO porId(Long id){
		return manager.find(ProdutoTO.class, id);
	}

	@SuppressWarnings("unchecked")	
	public List<ProdutoTO> pesquisaPorDescricao(String desc){
		/*
		return this.manager.createQuery("from ProdutoTO where upper(descricao) like :descricao",ProdutoTO.class)
				.setParameter("descricao",descricao.toUpperCase() + "%")
				.getResultList();
		*/
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ProdutoTO.class);
		criteria.add(Restrictions.ilike("descricao",desc,MatchMode.ANYWHERE));
		
		return criteria.addOrder(Order.asc("descricao")).list();
	}	
	
	
}
