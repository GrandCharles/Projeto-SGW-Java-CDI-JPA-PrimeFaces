package br.com.grandcharles.sgw.util.mail;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.outjected.email.api.MailMessage;
import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.MailMessageImpl;

@RequestScoped
public class Mailer implements Serializable{
	private static final long serialVersionUID = 1L;

	// Configuração da sessão de envio de email
	@Inject
	private SessionConfig sessionConfig;
	
	public MailMessage novaMensagemEmail(){
		return new MailMessageImpl(this.sessionConfig);
		
	}
	
} 
