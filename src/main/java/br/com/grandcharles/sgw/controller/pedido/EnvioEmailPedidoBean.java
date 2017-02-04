package br.com.grandcharles.sgw.controller.pedido;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;
import br.com.grandcharles.sgw.util.jsf.FacesUtil;
import br.com.grandcharles.sgw.util.mail.Mailer;


@Named
@RequestScoped
public class EnvioEmailPedidoBean  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	@PedidoEdicao
	private PedidoTO pedidoTO;
	
	@Inject
	private Mailer mailer;
	
	public void enviarEmail(){
		MailMessage mailMessage = mailer.novaMensagemEmail();
		
		mailMessage.to(this.pedidoTO.getClienteTO().getEmail())
		           .subject("Pedido de venda nº " + this.pedidoTO.getId())
		           .bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
		           .put("pedidoTO",this.pedidoTO)
		           .put("numberTool", new NumberTool())
		           .put("locale",new Locale("pt","BR"))
		           .send();
		
		
		
		FacesUtil.addInfoMessage("E-mail do Pedido enviado com sucesso");
	}
	
}
