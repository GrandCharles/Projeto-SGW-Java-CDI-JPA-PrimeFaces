package br.com.grandcharles.sgw.repository.usuario;

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
import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class UsuarioRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public UsuarioTO salvar(UsuarioTO usuarioTO){
		return this.manager.merge(usuarioTO);
	} 
	
	
	@Transactional
	public void remover(UsuarioTO usuarioTO){
		try {
			usuarioTO = porId(usuarioTO.getId());
			this.manager.remove(usuarioTO);
			this.manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Usuário não pode ser excluído!");
		} 
	}

	
	@SuppressWarnings("unchecked")
	public List<UsuarioTO> pesquisaFiltro(UsuarioFilter filtro){
		/* Pilha hibernate*/
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UsuarioTO.class);
		
		// where descricao like '%grandcharles%'
		if (StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome",filtro.getNome(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	/*
	public List<UsuarioTO> buscaVendedores(){
		return this.manager.createQuery("from UsuarioTO", UsuarioTO.class).getResultList();
	}
	*/
	
	public List<UsuarioTO> pesquisaPorNome(String nome){
		return this.manager.createQuery("from UsuarioTO where upper(nome) like :nome",UsuarioTO.class)
				           .setParameter("nome",nome.toUpperCase() + "%")
				           .getResultList();
	}
	
	


	public UsuarioTO porId(Long id){
		return this.manager.find(UsuarioTO.class, id);
	}
	
	public UsuarioTO porEmail(String email){
		UsuarioTO usuarioTO = null;
		try {
			usuarioTO = this.manager.createQuery("from UsuarioTO where lower(email) = :valor",UsuarioTO.class)
			                        .setParameter("valor", email.toLowerCase())
			                        .getSingleResult();
		} catch (NoResultException e){
			// Nenhum usuário encontrado com o E-Mail informado
		}
		return usuarioTO;
	}
	
	public UsuarioTO porLogin(String login){
		UsuarioTO usuarioTO = null;
		try {
			usuarioTO = this.manager.createQuery("from UsuarioTO where upper(login) = :valor",UsuarioTO.class)
					                .setParameter("valor", login.toUpperCase())
				                    .getSingleResult();
		} catch (NoResultException e) {
		}
		return usuarioTO;
	}

	public UsuarioTO existeLogin(String login){
		try {
			return manager.createQuery("from UsuarioTO where upper(login)=:valor",UsuarioTO.class)
					.setParameter("valor", login.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	
	
}
