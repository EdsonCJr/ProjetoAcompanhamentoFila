package br.com.acompanhamentofila.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.acompanhamentofila.util.HibernateUtil;

public class GenericDAO<Entidade> {

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(entidade);
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

	public List<Entidade> listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria query = session.createCriteria(classe);
			List<Entidade> result = query.list();
			return result;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	public Entidade buscar(Long numChamado) {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria query = session.createCriteria(classe);
			query.add(Restrictions.idEq(numChamado));
			Entidade result = (Entidade) query.uniqueResult();
			return result;
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			session.close();
		}
	}

	public void merge(Entidade entidade) {

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(entidade);
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

}
