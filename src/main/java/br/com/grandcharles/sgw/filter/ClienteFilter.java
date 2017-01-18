package br.com.grandcharles.sgw.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String docCpfCnpj;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDocCpfCnpj() {
		return docCpfCnpj;
	}
	public void setDocCpfCnpj(String docCpfCnpj) {
		this.docCpfCnpj = docCpfCnpj;
	}
	
	
}
