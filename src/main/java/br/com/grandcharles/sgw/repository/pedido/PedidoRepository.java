package br.com.grandcharles.sgw.repository.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;


import br.com.grandcharles.sgw.filter.PedidoFilter;
import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.model.pedido.DataValorTO;
import br.com.grandcharles.sgw.service.NegocioException;
import br.com.grandcharles.sgw.util.jpa.Transactional;

public class PedidoRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	
	public PedidoTO salvar(PedidoTO pedidoTO){
		return manager.merge(pedidoTO);
	}
	
	
	public void excluir(PedidoTO pedidoTO){
		try {
			pedidoTO = porId(pedidoTO.getId());
			manager.remove(pedidoTO);
			manager.flush();
		} catch(PersistenceException e){
			throw new NegocioException("Pedido não pode ser excluído!");
		} 
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PedidoTO> pesquisaFiltro(PedidoFilter filtro){
		//Session session = manager.unwrap(Session.class);
		Session session = (Session) manager;
		
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
	

	public PedidoTO porId(Long id){
		return manager.find(PedidoTO.class, id);
	}

	
	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias, UsuarioTO criadoPor) {
		//Session session = manager.unwrap(Session.class);
		Session session = (Session) manager;
		
		numeroDeDias -= 1;
		
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);
		
		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias, dataInicial);
		
		Criteria criteria = session.createCriteria(PedidoTO.class);
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.sqlGroupProjection("date(dtDataCriacao) as data", 
						                            "date(dtDataCriacao)", new String[] { "data" }, 
						new Type[] { StandardBasicTypes.DATE } ))
				.add(Projections.sum("vlrTotal").as("valor"))
			)
			.add(Restrictions.ge("dtCriacao", dataInicial.getTime()));
		
		if (criadoPor != null) {
			criteria.add(Restrictions.eq("usuarioTO", criadoPor));
		}
		
		List<DataValorTO> valoresPorData = criteria
				.setResultTransformer(Transformers.aliasToBean(DataValorTO.class)).list();
		
		for (DataValorTO dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}
		
		return resultado;
	}



	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias, Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return mapaInicial;
	}

}
