package br.com.acompanhamentofila.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.acompanhamentofila.enumeration.StatusChamado;

@Entity
@Table(name = "tbl_chamado")
public class Chamado {

	@Id
	private long numeroChamado;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura;

	@Column
	@Enumerated(EnumType.STRING)
	private StatusChamado statusChamado;

	@Column(length = 15)
	private String codigoCliente;

	@Column(length = 50)
	private String nomeCliente;

	@Column(length = 60)
	private String setorAbertura;

	@Column(length = 120)
	private String problemaAbertura;

	@Column(length = 25)
	private String criticidade;

	@Column(length = 2500)
	private String descricaoProblema;

	@Column
	private Date dataUltimaResp;

	@Column(length = 1000)
	private String ultimaIntervecao;

	@Column(length = 1000)
	private String ultimaSolicitacao;

	@OneToOne
	@JoinColumn(name = "operador_codigo")
	private Operador operadores;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date vencimentoSla;
	
	@Column(length = 6)
	private String visibilidade;

	@Transient
	private String caminho;

	public long getNumeroChamado() {
		return numeroChamado;
	}

	public void setNumeroChamado(long numeroChamado) {
		this.numeroChamado = numeroChamado;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public StatusChamado getStatusChamado() {
		return statusChamado;
	}

	public void setStatusChamado(StatusChamado statusChamado) {
		this.statusChamado = statusChamado;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getSetorAbertura() {
		return setorAbertura;
	}

	public void setSetorAbertura(String setorAbertura) {
		this.setorAbertura = setorAbertura;
	}

	public String getProblemaAbertura() {
		return problemaAbertura;
	}

	public void setProblemaAbertura(String problemaAbertura) {
		this.problemaAbertura = problemaAbertura;
	}

	public String getCriticidade() {
		return criticidade;
	}

	public void setCriticidade(String criticidade) {
		this.criticidade = criticidade;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public Date getDataUltimaResp() {
		return dataUltimaResp;
	}

	public void setDataUltimaResp(Date dataUltimaResp) {
		this.dataUltimaResp = dataUltimaResp;
	}

	public String getUltimaIntervecao() {
		return ultimaIntervecao;
	}

	public void setUltimaIntervecao(String ultimaIntervecao) {
		this.ultimaIntervecao = ultimaIntervecao;
	}

	public String getUltimaSolicitacao() {
		return ultimaSolicitacao;
	}

	public void setUltimaSolicitacao(String ultimaSolicitacao) {
		this.ultimaSolicitacao = ultimaSolicitacao;
	}

	public Operador getOperadores() {
		return operadores;
	}

	public void setOperadores(Operador operadores) {
		this.operadores = operadores;
	}

	public Date getVencimentoSla() {
		return vencimentoSla;
	}

	public void setVencimentoSla(Date vencimentoSla) {
		this.vencimentoSla = vencimentoSla;
	}

	public String getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (numeroChamado ^ (numeroChamado >>> 32));
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
		Chamado other = (Chamado) obj;
		if (numeroChamado != other.numeroChamado)
			return false;
		return true;
	}

}
