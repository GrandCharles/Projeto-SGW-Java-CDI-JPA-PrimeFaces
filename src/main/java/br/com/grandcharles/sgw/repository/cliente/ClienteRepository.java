package br.com.grandcharles.sgw.repository.cliente;

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

import br.com.grandcharles.sgw.filter.ClienteFilter;
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

	@SuppressWarnings("unchecked")
	public List<ClienteTO> pesquisaFiltro(ClienteFilter filtro){
		/* Pilha hibernate*/
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ClienteTO.class);
		
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome",filtro.getNome(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	/*
	public List<ClienteTO> buscaClientes(){
		return this.manager.createQuery("from ClienteTO", ClienteTO.class).getResultList();
	}
	*/

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
