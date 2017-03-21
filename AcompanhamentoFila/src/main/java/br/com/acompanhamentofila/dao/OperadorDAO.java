package br.com.acompanhamentofila.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import br.com.acompanhamentofila.domain.Operador;
import br.com.acompanhamentofila.util.HibernateUtil;

public class OperadorDAO extends GenericDAO<Operador> {

	public Operador buscarOperadorPorNome(String nome) {
		Operador operador = new Operador();

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria query = session.createCriteria(Operador.class);
			query.add(Restrictions.eq("nome", nome));

			operador = (Operador) query.uniqueResult();

			return operador;

		} catch (RuntimeException exception) {
			throw exception;

		} finally {
			session.close();
		}

	}

}
