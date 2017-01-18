package br.com.grandcharles.sgw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.grandcharles.sgw.model.produto.ProdutoTO;
import br.com.grandcharles.sgw.repository.ProdutosRepository;
import br.com.grandcharles.sgw.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = ProdutoTO.class)
public class ProdutoConverter implements Converter{

	//@Inject - Não funciona em classe converter no jsf 2.2
	private ProdutosRepository repository;
	
	// solução e retornar uma instancia (Bean CDI) do CategoriaRepository.class no contexto CDI.
	public ProdutoConverter(){
		repository = CDIServiceLocator.getBean(ProdutosRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ProdutoTO retorno = null;
		if (value != null){
			Long id = new Long(value);
			retorno= repository.porId(id); 
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
