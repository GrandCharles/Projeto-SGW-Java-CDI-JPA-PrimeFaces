package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.repository.produto.ProdutoRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ProdutoTO.class)
public class ProdutoConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private ProdutoRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do CategoriaRepository.class no contexto CDI.
	public ProdutoConverter(){
		this.repository = (ProdutoRepository) CDIServiceLocator.getBean(ProdutoRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ProdutoTO retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			retorno= this.repository.porId(new Long(value)); 
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null){
			ProdutoTO produtoTO = (ProdutoTO) value;
			return produtoTO.getId() == null ? null : produtoTO.getId().toString();
		}
		return "";
	}

}
