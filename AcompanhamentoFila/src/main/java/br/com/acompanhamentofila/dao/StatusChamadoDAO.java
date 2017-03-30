package br.com.acompanhamentofila.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.acompanhamentofila.domain.StatusChamado;
import br.com.acompanhamentofila.util.HibernateUtil;

public class StatusChamadoDAO extends GenericDAO<StatusChamado> {
	
	public StatusChamado buscarStatus(String status){
		StatusChamado statusChamado = new StatusChamado();
		
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try{
			Criteria query = session.createCriteria(StatusChamado.class);
			query.add(Restrictions.eq("status", status));
			
			statusChamado = (StatusChamado) query.uniqueResult();
			
			return statusChamado;
			
		} catch(RuntimeException exception){
			throw exception;
		} finally {
			session.close();
		}
	}
	
	public List<StatusChamado> listarStatusOrdenado(){
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try{
			Criteria query = session.createCriteria(StatusChamado.class);
			query.addOrder(Order.asc("status"));
			
			List<StatusChamado> listaDeStatus = query.list();
			
			return listaDeStatus;
			
		} catch(RuntimeException exception){
			throw exception;
		} finally {
			session.close();
		}
		
	}

}
