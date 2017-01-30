package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.usuario.UsuarioTO;
import br.com.grandcharles.sgw.repository.usuario.UsuarioRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = UsuarioTO.class)
public class UsuarioConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private UsuarioRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do UsuarioRepository.class no contexto CDI.
	public UsuarioConverter(){
		this.repository = (UsuarioRepository) CDIServiceLocator.getBean(UsuarioRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		UsuarioTO retorno = null;
	
		if (value != null){
			retorno = this.repository.porId(new Long(value)); 
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			UsuarioTO usuarioTO = (UsuarioTO) value;
			return usuarioTO.getId() == null ? null : usuarioTO.getId().toString();
		}
		return "";
	}

}
