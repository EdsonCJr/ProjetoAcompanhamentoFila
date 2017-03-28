package br.com.acompanhamentofila.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.acompanhamentofila.domain.Operador;

public class OperadorDAOTeste {
	
	@Test
	//@Ignore
	public void salvar(){
		
		
		Operador operador1 = new Operador();
		/*
		Operador operador2 = new Operador();
		Operador operador3 = new Operador();
		Operador operador4 = new Operador();
		Operador operador5 = new Operador();
		Operador operador6 = new Operador();
		Operador operador7 = new Operador();
		Operador operador8 = new Operador();
		Operador operador9 = new Operador();
		Operador operador10 = new Operador();
		Operador operador11 = new Operador();
		Operador operador12 = new Operador();
		Operador operador13 = new Operador();
		Operador operador14 = new Operador();
		*/
		OperadorDAO operadorDAO = new OperadorDAO();
		
		
		operador1.setNome("Leal dos Santos Lauriete");
		/*
		operador2.setNome("Santana Helder");
		operador3.setNome("de Jesus Bon Fim Alex");
		operador4.setNome("Soares Da Costa Igor");
		operador5.setNome("Marrocos de Sousa Ivan");
		operador6.setNome("Matos da Silva Rodrigo");
		operador7.setNome("Jensen Nilton");
		operador8.setNome("Cristina dos Santos Martins Larissa");
		operador9.setNome("Teixeira da Fonseca Ramon");
		operador10.setNome("Diniz Lima Filipe");
		operador11.setNome("Araujo Ferreira Fabiano");
		operador12.setNome("Cavicchio Junior Edson");
		operador13.setNome("Alexandre do Nascimento Ricardo");
		operador14.setNome("Sem operador");
		*/
		
		operadorDAO.merge(operador1);
		/*
		operadorDAO.merge(operador2);
		operadorDAO.merge(operador3);
		operadorDAO.merge(operador4);
		operadorDAO.merge(operador5);
		operadorDAO.merge(operador6);
		operadorDAO.merge(operador7);
		operadorDAO.merge(operador8);
		operadorDAO.merge(operador9);
		operadorDAO.merge(operador10);
		operadorDAO.merge(operador11);
		operadorDAO.merge(operador12);
		operadorDAO.merge(operador13);
		operadorDAO.merge(operador14);
		*/
		
	}
	
	@Test
	@Ignore
	public void listar(){
		OperadorDAO operadorDAO = new OperadorDAO();
		List<Operador> listaDeOperadores = new ArrayList<>();
		
		listaDeOperadores = operadorDAO.listar();
		
		for(Operador op : listaDeOperadores){
			System.out.println("Código: "+op.getCodigo()+" nome: "+op.getNome());
		}
		System.out.println("Qtd operadores: "+listaDeOperadores.size());
		
	}

}
