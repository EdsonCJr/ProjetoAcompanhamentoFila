package br.com.acompanhamentofila.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.enumeration.StatusChamado;
import br.com.acompanhamentofila.util.HibernateUtil;

public class ChamadoDAO extends GenericDAO<Chamado> {

	public void fecharChamado(List<Chamado> listaDeChamados) {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (Chamado ch : listaDeChamados) {

				ch.setStatusChamado(StatusChamado.RESOLVIDO);
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
			/*
			 * query.add(Restrictions.ne("statusChamado", "Resolvido"))
			 * adicionando a restrição .ne (not equals) a query para recuperar
			 * somente os chamados com o status Aguardando
			 * intervenção", "Aguardando Retorno do Usuário", "Atribuido",
			 * "Designado", "Em processamento" e "Reatribuido"
			 */
			query.add(Restrictions.ne("statusChamado", StatusChamado.RESOLVIDO));
			/*
			 * query.add(Restrictions.in("statusChamado",
			 * "Aguardando intervenção", "Aguardando Retorno do Usuário",
			 * "Atribuido", "Designado", "Em processamento", "Reatribuido"));
			 * adicionando a restrição .in (IN = lista) a query para recuperar
			 * somente os chamados com o status Aguardando
			 * intervenção", "Aguardando Retorno do Usuário", "Atribuido",
			 * "Designado", "Em processamento" e "Reatribuido"
			 */
			List<Chamado> result = query.list();
			return result;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	public List<String> listaDeOperadores() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria query = session.createCriteria(Chamado.class);
			query.setProjection(
					Projections.distinct(Projections.projectionList().add(Projections.property("operadores"))));

			List<String> opList = query.list();
			/*
			 * List<Chamado> chList = new ArrayList<>();
			 * 
			 * for (String op : opList) { Chamado ch = new Chamado();
			 * ch.setOperadores(op); chList.add(ch); }
			 */

			return opList;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

}
