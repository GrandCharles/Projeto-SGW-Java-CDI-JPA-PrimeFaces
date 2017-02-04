package br.com.grandcharles.sgw.model.cliente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.grandcharles.sgw.model.pedido.PedidoTO;

@Entity
@Table(name="tbEndereco")
public class EnderecoTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotBlank
	@Size(max=40)
	@Column(name="strLogradouro", length=40, nullable=false )
	private String logradouro;

	@NotBlank
	@Size(max=10)
	@Column(name="strNumero", length=10)
	private String numero;

	@Size(max=40)
	@Column(name="strComplemento", length=40)
	private String complemento;

	@NotBlank
	@Size(max=30)
	@Column(name="strCidade", length=30, nullable=false)
	private String cidade;

	@NotBlank
	@Size(max=10)
	@Column(name="strUf", length=10, nullable=false)
	private String uf;

	@NotBlank
	@Size(max=8)
	@Column(name="strCep", length=8, nullable=false)
	private String cep;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="idCliente", nullable=false)
	private ClienteTO clienteTO;


	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
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

	public ClienteTO getClienteTO() {
		return clienteTO;
	}
	public void setClienteTO(ClienteTO clienteTO) {
		this.clienteTO = clienteTO;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoTO other = (EnderecoTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
