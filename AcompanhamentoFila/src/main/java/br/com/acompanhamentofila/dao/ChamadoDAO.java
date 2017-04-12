package br.com.acompanhamentofila.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.criteria.Expression;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;

import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.domain.Operador;
import br.com.acompanhamentofila.domain.StatusChamado;
import br.com.acompanhamentofila.util.HibernateUtil;

public class ChamadoDAO extends GenericDAO<Chamado> {

	public void fecharChamado(List<Chamado> listaDeChamados) {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;

		StatusChamadoDAO statusChamadoDAO = new StatusChamadoDAO();
		StatusChamado statusChamado = new StatusChamado();
		try {
			statusChamado = statusChamadoDAO.buscarStatus("Resolvido");
			transaction = session.beginTransaction();
			for (Chamado ch : listaDeChamados) {

				ch.setStatusChamado(statusChamado);
				session.merge(ch);

			}
			transaction.commit();
		} catch (RuntimeException exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw exception;
		} finally {
			session.close();
		}
	}

	public List<Chamado> listarChamadosAbertos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria query = session.createCriteria(Chamado.class);
			query.createAlias("statusChamado", "sc");
			query.add(Restrictions.ne("sc.status", "Resolvido"));

			/*
			 * query.add(Restrictions.ne("statusChamado", "Resolvido"))
			 * adicionando a restrição .ne (not equals) a query para recuperar
			 * somente os chamados com o status Aguardando
			 * intervenção", "Aguardando Retorno do Usuário", "Atribuido",
			 * "Designado", "Em processamento" e "Reatribuido"
			 * 
			 * query.add(Restrictions.ne("statusChamado",
			 * StatusChamado.RESOLVIDO));
			 * 
			 * query.add(Restrictions.in("statusChamado",
			 * "Aguardando intervenção", "Aguardando Retorno do Usuário",
			 * "Atribuido", "Designado", "Em processamento", "Reatribuido"));
			 * adicionando a restrição .in (IN = lista) a query para recuperar
			 * somente os chamados com o status Aguardando
			 * intervenção", "Aguardando Retorno do Usuário", "Atribuido",
			 * "Designado", "Em processamento" e "Reatribuido"
			 */

			List<Chamado> listaCh = query.list();

			List<Chamado> result = new ArrayList<>();

			DateTime hoje = new DateTime();

			for (Chamado c : listaCh) {

				Chamado ch = new Chamado();

				ch.setNumeroChamado(c.getNumeroChamado());
				ch.setDataAbertura(c.getDataAbertura());
				ch.setStatusChamado(c.getStatusChamado());
				ch.setCodigoCliente(c.getCodigoCliente());
				ch.setNomeCliente(c.getNomeCliente());
				ch.setSetorAbertura(c.getSetorAbertura());
				ch.setProblemaAbertura(c.getProblemaAbertura());
				ch.setCriticidade(c.getCriticidade());
				ch.setDescricaoProblema(c.getDescricaoProblema());
				ch.setDataUltimaResp(c.getDataUltimaResp());
				ch.setUltimaIntervecao(c.getUltimaIntervecao());
				ch.setUltimaSolicitacao(c.getUltimaSolicitacao());
				ch.setOperadores(c.getOperadores());
				ch.setVencimentoSla(c.getVencimentoSla());

				DateTime dtCh = new DateTime(c.getDataAbertura());
				int idade = Days.daysBetween(dtCh, hoje).getDays();
				
				ch.setIdadeChamado(idade);

				result.add(ch);
			}

			return result;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	public List<Chamado> listaDeChamadosPorOperador(Operador op) {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			/*
			 * O parâmetro "operadores" em query.createCriteria se refere ao
			 * atributo operadores da classe Chamado, que no banco de dados está
			 * definido como "operador_codigo" definido pela anotação
			 * 
			 * @JoinColumn(name ="") também da classe Chamado, para o criteria
			 * considetar o nome do atributo da clase, ou seja, "operadores"
			 */
			Criteria query = session.createCriteria(Chamado.class);
			query.createCriteria("operadores").add(Restrictions.idEq(op.getCodigo()));

			List<Chamado> listaDeChamados = query.list();

			return listaDeChamados;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}

	}

	public List<String> listaDeCriticidade() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria query = session.createCriteria(Chamado.class);
			query.setProjection(
					Projections.distinct(Projections.projectionList().add(Projections.property("criticidade"))))
					.addOrder(Order.asc("criticidade"));

			List<String> listaCrit = query.list();

			return listaCrit;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	public List<String> listaDeSistemas() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {

			Criteria query = session.createCriteria(Chamado.class);
			query.setProjection(
					Projections.distinct(Projections.projectionList().add(Projections.property("setorAbertura"))))
					.addOrder(Order.asc("setorAbertura"));

			List<String> listaSist = query.list();

			return listaSist;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	/*
	 * public List<String> listaDeOperadores() { Session session =
	 * HibernateUtil.getFabricaDeSessoes().openSession(); try { Criteria query =
	 * session.createCriteria(Chamado.class); query.setProjection(
	 * Projections.distinct(Projections.projectionList().add(Projections.
	 * property("operadores"))));
	 * 
	 * List<String> opList = query.list();
	 * 
	 * 
	 * return opList;
	 * 
	 * } catch (RuntimeException exception) { throw exception; } finally {
	 * session.close(); } }
	 */

}
