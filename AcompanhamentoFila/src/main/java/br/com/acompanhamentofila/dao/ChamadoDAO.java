package br.com.acompanhamentofila.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.acompanhamentofila.domain.Chamado;
import br.com.acompanhamentofila.util.HibernateUtil;

public class ChamadoDAO extends GenericDAO<Chamado> {

	public void fecharChamado(List<Chamado> listaDeChamados) {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for (Chamado ch : listaDeChamados) {

				ch.setStatusChamado("Resolvido");
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
			query.add(Restrictions.in("statusChamado", "Aguardando intervenção", "Aguardando Retorno do Usuário",
					"Atribuido", "Designado", "Em processamento", "Reatribuido"));
			List<Chamado> result = query.list();
			return result;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

}
