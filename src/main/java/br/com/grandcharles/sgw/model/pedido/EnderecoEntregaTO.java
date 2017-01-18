package br.com.grandcharles.sgw.model.pedido;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class EnderecoEntregaTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max=40)
	@Column(name="strLogradouro", length=40, nullable=false)
	private String logradouro;

	@NotBlank
	@Size(max=10)
	@Column(name="strNumero", length=10, nullable=false)	
	private String numero;

	@Size(max=40)
	@Column(name="strComplemento", length=40)
	private String complemento;

	@NotBlank
	@Size(max=20)
	@Column(name="strCidade", length=20, nullable=false)
	private String cidade;

	@NotBlank
	@Size(max=10)
	@Column(name="strUf", length=10, nullable=false)
	private String uf;

	@NotBlank
	@Size(max=8)
	@Column(name="strCep", length=8, nullable=false)
	private String cep;

	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

}
