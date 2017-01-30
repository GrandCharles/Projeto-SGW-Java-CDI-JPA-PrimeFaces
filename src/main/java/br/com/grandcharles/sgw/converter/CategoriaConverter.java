package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.produto.CategoriaTO;
import br.com.grandcharles.sgw.repository.produto.CategoriasRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = CategoriaTO.class)
public class CategoriaConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private CategoriasRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do CategoriaRepository.class no contexto CDI.
	public CategoriaConverter(){
		repository = (CategoriasRepository) CDIServiceLocator.getBean(CategoriasRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CategoriaTO retorno = null;
		if (value != null){
			Long id = new Long(value);
			retorno= repository.porId(id); 
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			CategoriaTO categoriaTO = (CategoriaTO) value;
			return categoriaTO.getId() == null ? null : categoriaTO.getId().toString();
		}
		return "";
	}

}
