package br.com.grandcharles.sgw.repository.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.grandcharles.sgw.filter.ProdutoFilter;
import br.com.grandcharles.sgw.model.produto.CategoriaTO;
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


	
	
	
	/* Descontinuado apos hibernate 5.3
	@SuppressWarnings("unchecked")
	public List<ProdutoTO> pesquisaFiltro(ProdutoFilter filtro){
		Session session = (Session) manager;
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
	*/
	public List<ProdutoTO> pesquisaFiltro(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ProdutoTO> criteriaQuery = builder.createQuery(ProdutoTO.class);
		Root<ProdutoTO> produtoRoot = criteriaQuery.from(ProdutoTO.class);

		
		Fetch<ProdutoTO, CategoriaTO> categoriaJoin = produtoRoot.fetch("categoriaTO", JoinType.INNER);
		categoriaJoin.fetch("categoriaPai", JoinType.INNER);

		
		List<Predicate> predicates = new ArrayList<>();
		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.equal(produtoRoot.get("sku"), filtro.getSku()));
		}
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.like(builder.lower(produtoRoot.get("strDescricao")), 
					"%" + filtro.getDescricao().toLowerCase() + "%"));
		}
		criteriaQuery.select(produtoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(produtoRoot.get("descricao")));
		

		
		//TypedQuery<ProdutoTO> query = manager.createQuery("select... ",resultClass);
		TypedQuery<ProdutoTO> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
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

	public List<ProdutoTO> pesquisaPorDescricao(String desc){
		try {
			return this.manager.createQuery("from ProdutoTO where upper(descricao) like :descricao",ProdutoTO.class)
					.setParameter("descricao",desc.toUpperCase() + "%")
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		
		
		
	}	
	
	
}
