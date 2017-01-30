package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.repository.pedido.PedidoRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = PedidoTO.class)
public class PedidoConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private PedidoRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do PedidoRepository.class no contexto CDI.
	public PedidoConverter(){
		this.repository = CDIServiceLocator.getBean(PedidoRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PedidoTO retorno = null;
		if (value != null){
			Long id = new Long(value);
			retorno= this.repository.porId(id); 
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			PedidoTO pedidoTO = (PedidoTO) value;
			return pedidoTO.getId() == null ? null : pedidoTO.getId().toString();
		}
		return "";
	}

}
