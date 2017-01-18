package br.com.grandcharles.sgw.controller.sistema;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class SistemaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public void alterarLingua_us(){
		Locale local = new Locale ("en","US");
	    FacesContext.getCurrentInstance().getApplication().setDefaultLocale(local);
	    FacesContext.getCurrentInstance().getViewRoot().setLocale(local);
    }
    
    public void alterarLingua_pt(){
        Locale local = new Locale ("pt","BR");
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(local);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(local);

    }

}
