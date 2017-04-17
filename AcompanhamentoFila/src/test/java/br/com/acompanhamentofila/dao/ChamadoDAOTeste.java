package br.com.acompanhamentofila.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.swing.text.DateFormatter;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;
import br.com.acompanhamentofila.domain.StatusChamado;

public class ChamadoDAOTeste {

	@Test
	@Ignore
	public void salvar() {
		Chamado ch = new Chamado();
		ChamadoDAO chDao = new ChamadoDAO();

		Operador operador = new Operador();
		OperadorDAO operadorDAO = new OperadorDAO();

		StatusChamado statusChamado = new StatusChamado();
		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();

		operador = operadorDAO.buscarOperadorPorNome("Cavicchio Junior Edson");
		statusChamado = statusChamadoDAO.buscarStatus("Atribuido");

		Date dt = new Date("08/02/2017 11:00");
		Date dt1 = new Date("08/02/2017 13:31");
		Date dt2 = new Date("02/08/2017 11:31");

		ch.setNumeroChamado(999999L);
		ch.setDataAbertura(dt);
		ch.setStatusChamado(statusChamado);
		ch.setCodigoCliente("MBZ_COORD01");
		ch.setNomeCliente("COORDENADOR LOJA BAIRRO MBZ");
		ch.setSetorAbertura("Suporte ao Sistema GOLD");
		ch.setProblemaAbertura("Problemas com Geração de PLU");
		ch.setCriticidade("P2 = 02 horas");
		ch.setDescricaoProblema(
				"Nome:SILVANA GOMES DA SILVATelefone:(031)32977689ESTOU COM VARIAS CARGAS PLU  DE PREÇOS  PARADAS - FAVOR VALIDADR AS MESMAS .");
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
	public void listar() {
		ChamadoDAO chamadoDao = new ChamadoDAO();
		List<Chamado> listaDeChamados = chamadoDao.listar();
		for (Chamado ch : listaDeChamados) {
			System.out.println(
					ch.getNumeroChamado() + " - " + ch.getCriticidade() + " - " + ch.getStatusChamado().getStatus());
		}
		System.out.println("Qtd:" + listaDeChamados.size());
	}

	@Test
	@Ignore
	public void listarChamadosAbertos() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<Chamado> OpenList = chamadoDAO.listarChamadosAbertos();
		for (Chamado ch : OpenList) {
			System.out.println(
					ch.getNumeroChamado() + " - " + ch.getCriticidade() + " - " + ch.getStatusChamado().getStatus());
		}
		System.out.println("Qtd: " + OpenList.size());
	}

	@Test
	@Ignore
	public void listaDeChamadosOperadores() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		Operador operador = new Operador();
		operador.setCodigo(13L);

		List<Chamado> lista = new ArrayList<>();
		lista = chamadoDAO.listaDeChamadosPorOperador(operador);

		if (lista.size() == 0 || lista.isEmpty()) {
			System.out.println("tamanho da lista: " + lista.size());
		} else {
			for (Chamado ch : lista) {
				System.out
						.println("Número Chamado: " + ch.getNumeroChamado() + " Status: " + ch.getStatusChamado() + "");
			}
			System.out.println("Qtd chamado: " + lista.size());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Chamado ch = new Chamado();
		ChamadoDAO chamadoDao = new ChamadoDAO();

		ch = chamadoDao.buscar(988616L);

		if (ch == null) {
			System.out.println("Chamado não encontrado");
		} else {
			System.out.println("Número Chamado: " + ch.getNumeroChamado() + " Prioridade: " + ch.getCriticidade());
		}
	}

	@Test
	@Ignore
	public void listaDePrioridade() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<String> lista = chamadoDAO.listaDeCriticidade();

		for (String cr : lista) {
			System.out.println("Criticidade: " + cr);
		}
	}

	@Test
	@Ignore
	public void listagemDeSistemas() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		List<String> lista = chamadoDAO.listaDeSistemas();

		for (String st : lista) {
			System.out.println(st);
		}
	}

	@Test
	@Ignore
	public void calculaIdadeChamado() {
		Date dtAtual = new Date();
		Date dtChamado = new Date("27/05/2016");

		DateTime dt = new DateTime();

		System.out.println("Obj DateTime: " + dt);

		DateTime dt1 = new DateTime(dtAtual);
		DateTime dt2 = new DateTime(dtChamado);

		int dias = Days.daysBetween(dt1, dt2).getDays();

		System.out.println("Idade do chamado: " + dias + " dias");

	}

	@Test
	public void chamadoPorVencimento() {
		ChamadoDAO chamadoDAO = new ChamadoDAO();

		List<Chamado> listaCh = new ArrayList<>();

		try {

			listaCh = chamadoDAO.listarChamadosAbertos();

		} catch (RuntimeException exception) {
			exception.printStackTrace();
		}
		int aVencer = 0;
		int amanha = 0;
		int hoje = 0;
		int vencido = 0;
		int qtd = 0;

		DateTime dtHj = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

		for (Chamado c : listaCh) {

			DateTime dtChamado = new DateTime(c.getVencimentoSla());

			if (!dtChamado.isEqual(null)) {
				String dtCh = dtChamado.toString(fmt);
				DateTime dc = fmt.parseDateTime(dtCh);
				String dthoje = dtHj.toString(fmt);
				DateTime dh = fmt.parseDateTime(dthoje);
				if (dc.isBefore(dh)) {
					System.out.println("Numero: " + c.getNumeroChamado() + " - setor: " + c.getSetorAbertura()
							+ " Status: " + c.getStatusChamado().getStatus() + " - SLA: " + c.getVencimentoSla()
							+ " vencido");
					vencido += 1;
				}

				if (dc.isEqual(dh)) {
					System.out.println(
							"Numero: " + c.getNumeroChamado() + " - setor: " + c.getSetorAbertura() + " Status: "
									+ c.getStatusChamado().getStatus() + " - SLA: " + c.getVencimentoSla() + " hoje");
					hoje += 1;
				}

				if (dc.isEqual(dh.plusDays(1))) {
					System.out.println(
							"Numero: " + c.getNumeroChamado() + " - setor: " + c.getSetorAbertura() + " Status: "
									+ c.getStatusChamado().getStatus() + " - SLA: " + c.getVencimentoSla() + " amanhã");
					amanha += 1;
				}

				if (dc.isAfter(dh.plusDays(1))) {
					System.out.println(
							"Numero: " + c.getNumeroChamado() + " - setor: " + c.getSetorAbertura() + " Status: "
									+ c.getStatusChamado().getStatus() + " - SLA: " + c.getVencimentoSla() + " a vencer");
					aVencer += 1;
				}
			}
		}
		System.out.println("Qtd vencido: " + vencido);
		System.out.println("Qtd hoje: " + hoje);
		System.out.println("Qtd amanhã: " + amanha);
		System.out.println("Qtd a vencer: " + aVencer);
		int total = vencido+hoje+amanha+aVencer;
		System.out.println("Qtd total chamado: " + total );

	}

}
