package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.usuario.GrupoUsuarioTO;
import br.com.grandcharles.sgw.repository.usuario.GrupoUsuarioRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = GrupoUsuarioTO.class)
public class GrupoUsuarioConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private GrupoUsuarioRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do GrupoUsuarioRepository.class no contexto CDI.
	public GrupoUsuarioConverter() {
		repository = (GrupoUsuarioRepository) CDIServiceLocator.getBean(GrupoUsuarioRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		GrupoUsuarioTO retorno = null;
		if (value != null){
			Long id = new Long(value);
			retorno= repository.porId(id); 
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			GrupoUsuarioTO grupoUsuarioTO = (GrupoUsuarioTO) value;
			return grupoUsuarioTO.getId() == null ? null : grupoUsuarioTO.getId().toString();
		}
		return "";
	}

}
