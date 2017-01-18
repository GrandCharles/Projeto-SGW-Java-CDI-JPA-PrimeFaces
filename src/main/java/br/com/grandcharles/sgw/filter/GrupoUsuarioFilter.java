package br.com.grandcharles.sgw.filter;

import java.io.Serializable;

import br.com.grandcharles.sgw.validation.SKU;

public class GrupoUsuarioFilter implements Serializable {
	private static final long serialVersionUID = 1L;

	private String sku;
	private String nome;
	private String descricao;

	@SKU
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku==null ? null : sku.toUpperCase();
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
