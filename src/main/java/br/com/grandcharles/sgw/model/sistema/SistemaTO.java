package br.com.grandcharles.sgw.model.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tbSistema")
public class SistemaTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@NotBlank
	@Size(max=50)
	@Column(name="strMailHost", length=50, nullable=false)
	private String MailHost;
	
	@NotBlank
	@Size(max=30)
	@Column(name="strMailPort", length=30, nullable=false)
	private String mailPort;

	@NotBlank
	@Column(name="blnMailSsl", nullable=false)
	private Boolean mailSsl;
	
	@NotBlank
	@Column(name="blnMailTls", nullable=false)
	private Boolean mailTls;
	
	@NotBlank
	@Column(name="blnMailAuth", nullable=false)
	private Boolean mailAuth;
	
	@NotBlank
	@Size(max=50)
	@Column(name="strMailUserName", length=50, nullable=false)
	private String mailUserName;
	
	@NotBlank
	@Size(max=30)
	@Column(name="strMailUserSenha", length=30, nullable=false)
	private String mailUserSenha;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMailHost() {
		return MailHost;
	}
	public void setMailHost(String mailHost) {
		MailHost = mailHost;
	}

	public String getMailPort() {
		return mailPort;
	}
	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}
	
	public Boolean getMailSsl() {
		return mailSsl;
	}
	public void setMailSsl(Boolean mailSsl) {
		this.mailSsl = mailSsl;
	}
	
	public Boolean getMailTls() {
		return mailTls;
	}
	public void setMailTls(Boolean mailTls) {
		this.mailTls = mailTls;
	}
	
	public Boolean getMailAuth() {
		return mailAuth;
	}
	public void setMailAuth(Boolean mailAuth) {
		this.mailAuth = mailAuth;
	}
	
	public String getMailUserName() {
		return mailUserName;
	}
	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}
	
	public String getMailUserSenha() {
		return mailUserSenha;
	}
	public void setMailUserSenha(String mailUserSenha) {
		this.mailUserSenha = mailUserSenha;
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
		SistemaTO other = (SistemaTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		

	
}
