package br.com.grandcharles.sgw.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class DataValorTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date data;
	private BigDecimal valor;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
