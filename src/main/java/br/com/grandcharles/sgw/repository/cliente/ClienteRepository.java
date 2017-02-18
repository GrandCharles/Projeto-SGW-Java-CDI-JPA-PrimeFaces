package br.com.grandcharles.sgw.repository.cliente;

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

import br.com.grandcharles.sgw.filter.ClienteFilter;
import br.com.grandcharles.sgw.model.cliente.EnderecoTO;
import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class ClienteRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public ClienteTO salvar(ClienteTO clienteTO){
		return this.manager.merge(clienteTO);
	} 
	
	@Transactional
	public void remover(ClienteTO clienteTO){
		try {
			clienteTO = porId(clienteTO.getId());
			this.manager.remove(clienteTO);
			this.manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Cliente não pode ser excluído!");
		} 
	}

	
	/* Descontinuado apos hibernate 5.3
	@SuppressWarnings("unchecked")
	public List<ClienteTO> pesquisaFiltro(ClienteFilter filtro){
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ClienteTO.class);
		
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome",filtro.getNome(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	*/
	
	public List<ClienteTO> pesquisaFiltro(ClienteFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ClienteTO> criteriaQuery = builder.createQuery(ClienteTO.class);
		Root<ClienteTO> clienteRoot = criteriaQuery.from(ClienteTO.class);

		
		//Fetch<ClienteTO, EnderecoTO> enderecoJoin = clienteRoot.fetch("enderecoTO", JoinType.INNER);
		//enderecoJoin.fetch("clienteTO", JoinType.INNER);
		
		
		List<Predicate> predicates = new ArrayList<>();
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(clienteRoot.get("strNome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}

		
		criteriaQuery.select(clienteRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(clienteRoot.get("nome")));
		
		TypedQuery<ClienteTO> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	
	
	
	
	public List<ClienteTO> pesquisaPorNome(String nome){
		return this.manager.createQuery("from ClienteTO where upper(nome) like :nome",ClienteTO.class)
				.setParameter("nome",nome.toUpperCase() + "%")
				.getResultList();
	}
	
 	
	public ClienteTO existeNome(String nome){
		try {
		return this.manager.createQuery("from ClienteTO where upper(nome) = :valor",ClienteTO.class)
				.setParameter("valor", nome.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public ClienteTO porId(Long id){
		return manager.find(ClienteTO.class, id);
	}

	
}
