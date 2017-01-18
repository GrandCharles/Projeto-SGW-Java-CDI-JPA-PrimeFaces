package br.com.grandcharles.sgw.model.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="tbcliente")
public class ClienteTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotBlank
	@Size(max=30)
	@Column(name="strNome", length=30, nullable=false)
	private String nome;

	@NotBlank
	@Size(max=50)
	@Column(name="strEmail", length=50, nullable=false, unique=true)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name="strTipoPessoa", length=10, nullable=false)
	private TipoPessoa tipoPessoa;

	@Column(name="strDocCpfCnpj", length=14, nullable=false)
	private String docCpfCnpj;

	@OneToMany(mappedBy = "clienteTO", cascade=CascadeType.ALL)
	private List<EnderecoTO> lstEnderecos = new ArrayList<>();

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public String getDocCpfCnpj() {
		return docCpfCnpj;
	}
	public void setDocCpfCnpj(String docCpfCnpj) {
		this.docCpfCnpj = docCpfCnpj;
	}
	
	public List<EnderecoTO> getLstEnderecos() {
		return lstEnderecos;
	}
	public void setLstEnderecos(List<EnderecoTO> lstEnderecos) {
		this.lstEnderecos = lstEnderecos;
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
		ClienteTO other = (ClienteTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
