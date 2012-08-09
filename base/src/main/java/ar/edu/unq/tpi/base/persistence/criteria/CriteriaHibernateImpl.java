package ar.edu.unq.tpi.base.persistence.criteria;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class CriteriaHibernateImpl implements Criteria<Criterion>{

	private Criterion criterion;
	
	public CriteriaHibernateImpl() {
	}

	public CriteriaHibernateImpl(Criterion criterion) {
		this.criterion = criterion;
	}

	public CriteriaHibernateImpl like(String property, String value) {
		return new CriteriaHibernateImpl(Restrictions.like(property, value,
				MatchMode.ANYWHERE));
	}

	public  CriteriaHibernateImpl equals(String property, Object value) {
		return new CriteriaHibernateImpl(Restrictions.eq(property, value));
	}
	public  CriteriaHibernateImpl equalsProperty(String propertyName, String otherPropertyName) {
		return new CriteriaHibernateImpl(Restrictions.eqProperty(propertyName, otherPropertyName));
	}

	public  CriteriaHibernateImpl distint(String property, Object value) {
		return new CriteriaHibernateImpl(Restrictions.ne(property, value));
	}

	public  CriteriaHibernateImpl greater(String property, Object value) {
		return new CriteriaHibernateImpl(Restrictions.gt(property, value));
	}
	public  CriteriaHibernateImpl greaterProperty(String property, String otherPropertyName) {
		return new CriteriaHibernateImpl(Restrictions.gtProperty(property, otherPropertyName));
	}

	public  CriteriaHibernateImpl lessProperty(String property, String otherPropertyName) {
		return new CriteriaHibernateImpl(Restrictions.ltProperty(property, otherPropertyName));
	}
	public  CriteriaHibernateImpl less(String property, String value) {
		return new CriteriaHibernateImpl(Restrictions.lt(property, value));
	}

	public  CriteriaHibernateImpl lessOrEqual(String property, String value) {
		return new CriteriaHibernateImpl(Restrictions.le(property, value));
	}
	
	public  CriteriaHibernateImpl lessOrEqualProperty(String property, String otherPropertyName) {
		return new CriteriaHibernateImpl(Restrictions.leProperty(property, otherPropertyName));
	}

	public  CriteriaHibernateImpl greaterOrEqual(String property, Object value) {
		return new CriteriaHibernateImpl(Restrictions.ge(property, value));
	}

	public  CriteriaHibernateImpl greaterOrEqualProperty(String property, String otherPropertyName) {
		return new CriteriaHibernateImpl(Restrictions.geProperty(property, otherPropertyName));
	}

	public  CriteriaHibernateImpl and(Criteria<Criterion> cri1, Criteria<Criterion> cri2) {
		return new CriteriaHibernateImpl(Restrictions.and(cri1.build(), cri2.build()));
	}

	public  CriteriaHibernateImpl or(Criteria<Criterion> cri1, Criteria<Criterion> cri2) {
		return new CriteriaHibernateImpl(Restrictions.or(cri1.build(), cri2.build()));
	}
	
	public  CriteriaHibernateImpl between(String propertyName, Object desde, Object hasta) {
		return new CriteriaHibernateImpl(Restrictions.between(propertyName, desde, hasta));
	}
	
	public  CriteriaHibernateImpl sqlRestricions(String sql) {
		return new CriteriaHibernateImpl(Restrictions.sqlRestriction(sql));
	}


	public Criterion build() {
		return this.criterion;
	}

}
