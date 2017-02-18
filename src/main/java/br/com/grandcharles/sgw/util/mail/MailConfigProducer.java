package br.com.grandcharles.sgw.util.mail;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.grandcharles.sgw.model.sistema.SistemaTO;
import br.com.grandcharles.sgw.repository.sistema.SistemaRepository;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer   {
	
	@Inject
	private SistemaRepository repository;

	private SistemaTO sistemaTO;


	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException{
		this.sistemaTO = new SistemaTO();

		Long id = new Long(1);
		this.sistemaTO = this.repository.porId(id);
	
		SimpleMailConfig simpleMailConfig = new SimpleMailConfig();
		simpleMailConfig.setServerHost(sistemaTO.getMailHost());
		simpleMailConfig.setServerPort(Integer.parseInt(sistemaTO.getMailPort()));
		simpleMailConfig.setEnableSsl(sistemaTO.getMailSsl());
		simpleMailConfig.setEnableTls(sistemaTO.getMailTls());
		simpleMailConfig.setAuth(sistemaTO.getMailAuth());
		simpleMailConfig.setUsername(sistemaTO.getMailUserName());
		simpleMailConfig.setPassword(sistemaTO.getMailUserSenha());

		/*
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/mail.properties"));
		
		SimpleMailConfig simpleMailConfig = new SimpleMailConfig();
		simpleMailConfig.setServerHost(properties.getProperty("mail.server.host"));
		simpleMailConfig.setServerPort(Integer.parseInt(properties.getProperty("mail.server.port")));
		simpleMailConfig.setEnableSsl(Boolean.parseBoolean(properties.getProperty("mail.enable.ssl")));
		simpleMailConfig.setEnableTls(Boolean.parseBoolean(properties.getProperty("mail.enable.tls")));
		simpleMailConfig.setAuth(Boolean.parseBoolean(properties.getProperty("mail.auth")));
		simpleMailConfig.setUsername(properties.getProperty("mail.username"));
		simpleMailConfig.setPassword(properties.getProperty("mail.password"));
		*/
		
		return simpleMailConfig;
	}
}
