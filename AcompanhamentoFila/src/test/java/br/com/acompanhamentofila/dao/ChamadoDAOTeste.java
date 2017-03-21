package br.com.acompanhamentofila.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;



import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;
import br.com.acompanhamentofila.enumeration.StatusChamado;

public class ChamadoDAOTeste {
	
	@Test
	@Ignore
	public void salvar(){
		Chamado ch = new Chamado();
		ChamadoDAO chDao = new ChamadoDAO();
		
		Operador operador = new Operador();
		OperadorDAO operadorDAO = new OperadorDAO();
		
		operador = operadorDAO.buscarOperadorPorNome("Cavicchio Junior Edson");
		
		
		
		Date dt = new Date("08/02/2017 11:00");
		Date dt1 = new Date("08/02/2017 13:31");
		Date dt2 = new Date("02/08/2017 11:31");
		
		ch.setNumeroChamado(988716L);
		ch.setDataAbertura(dt);
		ch.setStatusChamado(StatusChamado.ATRIBUIDO);
		ch.setCodigoCliente("MBZ_COORD01");
		ch.setNomeCliente("COORDENADOR LOJA BAIRRO MBZ");
		ch.setSetorAbertura("Suporte ao Sistema GOLD");
		ch.setProblemaAbertura("Problemas com Geração de PLU");
		ch.setCriticidade("P2 = 02 horas");
		ch.setDescricaoProblema("Nome:SILVANA GOMES DA SILVATelefone:(031)32977689ESTOU COM VARIAS CARGAS PLU  DE PREÇOS  PARADAS - FAVOR VALIDADR AS MESMAS .");
		ch.setDataUltimaResp(dt2);
		ch.setUltimaIntervecao(" ### Senhores favor enviar o arquivo de carga de PLU noturno");
		ch.setUltimaSolicitacao("");
		ch.setOperadores(operador);
		ch.setVencimentoSla(dt1);
		ch.setVisibilidade("NORMAL");
		
		chDao.salvar(ch);
	}
	
	@Test
	@Ignore
	public void listar(){
		ChamadoDAO chamadoDao = new ChamadoDAO();
		List<Chamado> listaDeChamados = chamadoDao.listar();
		for(Chamado ch : listaDeChamados){
			System.out.println(ch.getNumeroChamado()+" - "+ch.getCriticidade()+" - "+ch.getStatusChamado());
		}
		System.out.println("Qtd:"+listaDeChamados.size());
	}
	
	@Test
	public void listarChamadosAbertos(){
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<Chamado> OpenList = chamadoDAO.listarChamadosAbertos();
		for(Chamado ch : OpenList){
			System.out.println(ch.getNumeroChamado()+" - "+ch.getCriticidade()+" - "+ch.getCriticidade()+" - "+ch.getOperadores().getNome());
		}
		System.out.println("Qtd: "+OpenList.size());
	}
	
	@Test
	@Ignore
	public void listaOperadores(){
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<String> listaOperadores = chamadoDAO.listaDeOperadores();
		for(String ch: listaOperadores){
			System.out.println("Operador: "+ch);
		}
	}
	
	
	@Test
	@Ignore
	public void buscar(){
		Chamado ch = new Chamado();
		ChamadoDAO chamadoDao = new ChamadoDAO();
		
		ch = chamadoDao.buscar(988616L);
		
		if (ch == null){
			System.out.println("Chamado não encontrado");
		} else{
			System.out.println("Número Chamado: "+ch.getNumeroChamado()+" Prioridade: "+ch.getCriticidade());
		}
	}

}
