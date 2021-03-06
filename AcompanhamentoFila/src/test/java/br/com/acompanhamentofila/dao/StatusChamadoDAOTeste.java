package br.com.acompanhamentofila.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.acompanhamentofila.domain.StatusChamado;

public class StatusChamadoDAOTeste {
	
	@Test
	@Ignore
	public void salvar(){
		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();
		StatusChamado statusChamado1 = new StatusChamado();
		StatusChamado statusChamado2 = new StatusChamado();
		StatusChamado statusChamado3 = new StatusChamado();
		StatusChamado statusChamado4 = new StatusChamado();
		StatusChamado statusChamado5 = new StatusChamado();
		StatusChamado statusChamado6 = new StatusChamado();
		StatusChamado statusChamado7 = new StatusChamado();
		
		statusChamado1.setStatus("Aguardando intervenção");
		statusChamado2.setStatus("Aguardando Retorno do Usuário");
		statusChamado3.setStatus("Reatribuido");
		statusChamado4.setStatus("Designado");
		statusChamado5.setStatus("Atribuido");
		statusChamado6.setStatus("Em processamento");
		statusChamado7.setStatus("Resolvido");
		
		statusChamadoDAO.merge(statusChamado1);
		statusChamadoDAO.merge(statusChamado2);
		statusChamadoDAO.merge(statusChamado3);
		statusChamadoDAO.merge(statusChamado4);
		statusChamadoDAO.merge(statusChamado5);
		statusChamadoDAO.merge(statusChamado6);
		statusChamadoDAO.merge(statusChamado7);

	}
	
	@Test
	public void listaOrdenada(){
		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();
		List<StatusChamado> lista = new ArrayList<>();
		
		lista = statusChamadoDAO.listarStatusOrdenado();
		
		for(StatusChamado st : lista){
			System.out.println("Status: "+st.getStatus());
		}
	}

}
