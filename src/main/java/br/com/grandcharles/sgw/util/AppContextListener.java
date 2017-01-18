package br.com.grandcharles.sgw.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// Não converter para zero variaveis(Bind) de expressão - Tomcat
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}

}