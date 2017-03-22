package br.com.acompanhamentofila.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.omnifaces.util.Messages;

import br.com.acompanhamentofila.dao.ChamadoDAO;
import br.com.acompanhamentofila.dao.OperadorDAO;
import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;

@SuppressWarnings("serial")
@ManagedBean(name = "chamadoMB2")
@ViewScoped
public class ChamadoBean2 implements Serializable {

	Operador operador = null;

	List<Operador> listaDeOperadores = new ArrayList<>();
	List<Chamado> listaDeChamados = new ArrayList<>();

	
	
	public void setOperador(Operador operador) {
		this.operador = operador;
	}
	
	public Operador getOperador() {
		return operador;
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
	

	@PostConstruct
	public void novo() {
		operador = new Operador();
		carregarOperadores();
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
	
	public void listaDeChamadosPorOperador(ValueChangeEvent event){
		
		long codOperador = (long) event.getNewValue();
		operador.setCodigo(codOperador);
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		try{
			
			listaDeChamados = chamadoDAO.listaDeChamadosPorOperador(operador);			
			
		} catch(RuntimeException exception){
			Messages.addGlobalError("Ocorreu um erro listar os chamados");
			exception.printStackTrace();
		}
		
	}
	
}