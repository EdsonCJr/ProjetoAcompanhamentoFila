package br.com.acompanhamentofila.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.acompanhamentofila.dao.ChamadoDAO;
import br.com.acompanhamentofila.dao.OperadorDAO;
import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;

@SuppressWarnings("serial")
@ManagedBean(name = "chamadoMB2")
@ViewScoped
public class ChamadoBean2 implements Serializable {

	Operador operador = null;
	Chamado chamadoSelecionado = null;

	List<Operador> listaDeOperadores = new ArrayList<>();
	List<Chamado> listaDeChamados = new ArrayList<>();

	private Boolean exibeDataTable;

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setChamadoSelecionado(Chamado chamadoSelecionado) {
		this.chamadoSelecionado = chamadoSelecionado;
	}

	public Chamado getChamadoSelecionado() {
		return chamadoSelecionado;
	}

	public void setListaDeOperadores(List<Operador> listaDeOperadores) {
		this.listaDeOperadores = listaDeOperadores;
	}

	public List<Operador> getListaDeOperadores() {
		return listaDeOperadores;
	}

	public void setListaDeChamados(List<Chamado> listaDeChamados) {
		this.listaDeChamados = listaDeChamados;
	}

	public List<Chamado> getListaDeChamados() {
		return listaDeChamados;
	}

	public void setExibeDataTable(Boolean exibeDataTable) {
		this.exibeDataTable = exibeDataTable;
	}

	public Boolean getExibeDataTable() {
		return exibeDataTable;
	}

	@PostConstruct
	public void novo() {
		operador = new Operador();
		chamadoSelecionado = new Chamado();
		carregarOperadores();
		setExibeDataTable(false);
	}

	public void carregarOperadores() {
		OperadorDAO operadorDAO = new OperadorDAO();
		try {
			listaDeOperadores = operadorDAO.listar();

		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreou um erro ao carregar a lista de operadores");
			exception.printStackTrace();
		}
	}

	public void listaDeChamadosPorOperador(ValueChangeEvent event) {

		if (event.getNewValue() == null) {
			setExibeDataTable(false);
		} else {
			long codOperador = (long) event.getNewValue();
			operador.setCodigo(codOperador);
		}
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		try {

			listaDeChamados = chamadoDAO.listaDeChamadosPorOperador(operador);

			if (listaDeChamados.isEmpty()) {
				setExibeDataTable(false);
				Messages.addGlobalWarn("O operador selecionado não possui chamados em backlog");
			} else {
				setExibeDataTable(true);
			}

		} catch (RuntimeException exception) {
			Messages.addGlobalError("Ocorreu um erro listar os chamados");
			exception.printStackTrace();
		}

	}

	public void onRowSelect(SelectEvent event) {
		chamadoSelecionado = (Chamado) event.getObject();
	}

	/*
	 * Método inativo, pois não esta sendo utilizado, mas está presente na
	 * documentação do primefaces (showcase), caso necessário será ativo.
	 * 
	 * public void onRowUnselect(UnselectEvent event) { chamadoSelecionado =
	 * (Chamado) event.getObject(); }
	 */

}
