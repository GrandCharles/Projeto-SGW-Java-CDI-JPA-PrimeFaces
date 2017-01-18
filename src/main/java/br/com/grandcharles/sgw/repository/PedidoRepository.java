package br.com.grandcharles.sgw.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

import br.com.grandcharles.sgw.filter.PedidoFilter;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;

public class PedidoRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	/*
	public PedidoTO salvar(PedidoTO pedidoTO){
		return manager.merge(pedidoTO);
	} 
	
	
	@Transactional
	public void remover(PedidoTO pedidoTO){
		try {
			pedidoTO = porId(pedidoTO.getId());
			manager.remove(pedidoTO);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Pedido não pode ser excluído!");
		} 
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<PedidoTO> pesquisa(PedidoFilter filtro){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(PedidoTO.class)
				// fazemos uma associação (join) com cliente e nomeamos como "c"
				.createAlias("clienteTO", "c")
				// fazemos uma associação (join) com usuario(vendedor) e nomeamos como "u"
				.createAlias("usuarioTO", "u");
				
		if (filtro.getNroPedInicial() != null){
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNroPedInicial()));			
		}
		if (filtro.getNroPedFinal() != null){
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNroPedFinal()));			
		}
		
		if (filtro.getDtPedInicial() != null) {
			criteria.add(Restrictions.ge("dtPedInicial", filtro.getDtPedInicial()));
		}
		
		if (filtro.getDtPedFinal() != null) {
			criteria.add(Restrictions.le("dtPedFinal", filtro.getDtPedFinal()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c", criado anteriormente
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(filtro.getNomeUsuario())) {
			// acessamos o nome do usuario associado ao pedido pelo alias "u", criado anteriormente
			criteria.add(Restrictions.ilike("u.nome", filtro.getNomeUsuario(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatusPedido() != null && filtro.getStatusPedido().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusPedido
			criteria.add(Restrictions.in("statusPedido", filtro.getStatusPedido()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
	}
	
	
	/*
	public UsuarioTO existeNome(String nome){
		try {
		return manager.createQuery("from UsuarioTO where upper(nome)=:valor",UsuarioTO.class)
				.setParameter("valor", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public PedidoTO porId(Long id){
		return manager.find(PedidoTO.class, id);
	}
	*/

	
}
