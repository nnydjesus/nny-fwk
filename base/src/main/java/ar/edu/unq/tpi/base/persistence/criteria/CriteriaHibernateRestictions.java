package ar.edu.unq.tpi.base.persistence.criteria;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;

public class CriteriaHibernateRestictions<T> implements Restictions<T, CriteriaHibernateImpl>{
	
	private Session session;
	private org.hibernate.Criteria hibernateCriteria;
	private ProjectionList projectionList;

	public CriteriaHibernateRestictions(Session session, Class<T> clazz) {
		this.session = session;
		hibernateCriteria = this.session.createCriteria(clazz);
	}
	
	public CriteriaHibernateRestictions<T> createCriteria(String associationPath, String alias){
		this.hibernateCriteria = this.hibernateCriteria.createCriteria(associationPath, alias, JoinType.INNER.ordinal());
		return this;
	}
	
	public CriteriaHibernateRestictions<T> createCriteria(String associationPath, int jointType){
		this.hibernateCriteria =this.hibernateCriteria.createCriteria(associationPath, jointType);
		return this;
	}
	
	
	public CriteriaHibernateRestictions<T> add(CriteriaHibernateImpl criterion){
		hibernateCriteria.add(criterion.build());		
		return this;
	}
	
	public CriteriaHibernateRestictions<T> addOrder(Order<?> order){
		hibernateCriteria.addOrder((org.hibernate.criterion.Order) order.getSpecificOrder());		
		return this;
	}
	public CriteriaHibernateRestictions<T> setMaxresults(int maxResults){
		hibernateCriteria.setMaxResults(maxResults);		
		return this;
	}
	public CriteriaHibernateRestictions<T> setFirstResult(int firstResult){
		hibernateCriteria.setFirstResult(firstResult);		
		return this;
	}
	
	public CriteriaHibernateRestictions<T> setCacheable(boolean cacheable){
		hibernateCriteria.setCacheable(cacheable);		
		return this;
	}
	
	public CriteriaHibernateRestictions<T> setResultTransformer(ResultTransformer resultProcessor) {
		hibernateCriteria.setResultTransformer(resultProcessor);
		return this;
	}

	public CriteriaHibernateRestictions<T> addProjection(Projection projection) {
		if(projectionList == null){
			projectionList = Projections.projectionList();
		}
		projectionList.add(projection);
		return this;
	}
	
	public CriteriaHibernateRestictions<T> buildProjections(){
		hibernateCriteria.setProjection(projectionList);
		return this;
	}
	
	public CriteriaHibernateRestictions<T> setFechMode(String associationPath, FetchMode mode){
		hibernateCriteria.setFetchMode(associationPath, mode);
		return this;
	}
	
	
	@SuppressWarnings("unchecked")
	public T uniqueResult(){
		return (T) hibernateCriteria.uniqueResult();		
	}
	
	@SuppressWarnings("unchecked")
	public <M> List<M> list(){
		return hibernateCriteria.list();		
	}

	
}
