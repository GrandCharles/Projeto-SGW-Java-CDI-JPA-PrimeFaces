package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.cliente.ClienteTO;
import br.com.grandcharles.sgw.repository.cliente.ClienteRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ClienteTO.class)
public class ClienteConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private ClienteRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do UsuarioRepository.class no contexto CDI.
	public ClienteConverter(){
		this.repository = (ClienteRepository) CDIServiceLocator.getBean(ClienteRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ClienteTO retorno = null;
		if (value != null){
			retorno= this.repository.porId(new Long(value)); 
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			ClienteTO clienteTO = (ClienteTO) value;
			return clienteTO.getId() == null ? null : clienteTO.getId().toString();
		}
		return "";
	}

}
