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

import br.com.grandcharles.sgw.filter.GrupoUsuarioFilter;
import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class GrupoUsuarioRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	
	public GrupoUsuarioTO salvar(GrupoUsuarioTO grupoTO){
		return manager.merge(grupoTO);
	} 

	@Transactional
	public void remover(GrupoUsuarioTO grupoUsuarioTO){
		try {
			grupoUsuarioTO = porId(grupoUsuarioTO.getId());
			manager.remove(grupoUsuarioTO);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Grupo de Usuário não pode ser excluído!");
		} 
	}

	public List<GrupoUsuarioTO> buscarGrupoUsuario(){
		/* JPQL - Java Procedure Query Language. Usada pela JPA*/
		return manager.createQuery("from GrupoUsuarioTO",
				GrupoUsuarioTO.class).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GrupoUsuarioTO> pesquisa(GrupoUsuarioFilter filtro){
		/* Pilha hibernate*/
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(GrupoUsuarioTO.class);
		
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome",filtro.getNome(),MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(filtro.getDescricao())){
			criteria.add(Restrictions.ilike("descricao",filtro.getDescricao(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	public GrupoUsuarioTO porId(Long id){
		return manager.find(GrupoUsuarioTO.class, id);
	}

	public GrupoUsuarioTO existeNome(String nome){
		try {
		return manager.createQuery("from GrupoUsuarioTO where upper(nome)=:valor",GrupoUsuarioTO.class)
				.setParameter("valor", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
