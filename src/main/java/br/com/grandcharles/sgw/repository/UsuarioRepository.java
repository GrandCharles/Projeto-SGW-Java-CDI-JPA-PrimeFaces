package br.com.grandcharles.sgw.repository;

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

import br.com.grandcharles.sgw.filter.UsuarioFilter;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class UsuarioRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public UsuarioTO salvar(UsuarioTO usuarioTO){
		return manager.merge(usuarioTO);
	} 
	
	@Transactional
	public void remover(UsuarioTO usuarioTO){
		try {
			usuarioTO = porId(usuarioTO.getId());
			manager.remove(usuarioTO);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Usuário não pode ser excluído!");
		} 
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioTO> pesquisa(UsuarioFilter filtro){
		/* Pilha hibernate*/
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UsuarioTO.class);
		
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome",filtro.getNome(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	
	public UsuarioTO existeNome(String nome){
		try {
		return manager.createQuery("from UsuarioTO where upper(nome)=:valor",UsuarioTO.class)
				.setParameter("valor", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public UsuarioTO porId(Long id){
		return manager.find(UsuarioTO.class, id);
	}

	
}
