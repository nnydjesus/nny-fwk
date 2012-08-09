package ar.edu.unq.tpi.base.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.persistence.criteria.CriteriaHibernateImpl;
import ar.edu.unq.tpi.base.persistence.criteria.CriteriaHibernateRestictions;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

public class GenericRepository<T extends PersistentObject> extends FWKRepository<T> {

    private Class<T> entityClass;


    public GenericRepository() {
    }

    public GenericRepository(final Class<T> t) {
        this.entityClass = t;
    }

    @Override
    public void save(final T object) {
        super.save(object);
    }

    @Override
    public void update(final T object) {
        super.update(object);
    }

    @Override
    public List<T> getAll() {
        return super.getAll();
    }

    @Override
    public Class<T> getEntityType() {
        return entityClass;
    }

    
	public CriteriaHibernateRestictions<T> createCriteria() {
		return new CriteriaHibernateRestictions<T>(session(), getEntityType());
	}

	public <V>T getByProperty(final String property, final V value) {
		try {
			return createCriteria().add(new CriteriaHibernateImpl().equals(property, value))
					.uniqueResult();
		} catch (final HibernateException e) {
			throw new UserException("Error cargando el objeto "
					+ getEntityType().getSimpleName() , e);
		}
	}
	
	public T getByLikeProperty(final String property, final String value) {
		try {
			return createCriteria().add(new CriteriaHibernateImpl().like(property, value))
					.uniqueResult();
		} catch (final HibernateException e) {
			throw new UserException("Error cargando el objeto "
					+ getEntityType().getSimpleName() , e);
		}
	}
	
	public T getByProperty(final String property, final String value) {
		try {
			return createCriteria().add(new CriteriaHibernateImpl().equals(property, value))
					.uniqueResult();
		} catch (final HibernateException e) {
			throw new UserException("Error cargando el objeto "	
					+ getEntityType().getSimpleName() , e);
		}
	}
	
	public <V> List<T> getsByProperty(final String property, final V value) {
		try {
			return createCriteria().add(new CriteriaHibernateImpl().equals(property, value)).list();
		} catch (final HibernateException e) {
			throw new UserException("Error cargando el objeto "
					+ getEntityType().getSimpleName() , e);
		}
	}
	
	public  List<T> getsByProperty(final String property, final String value) {
		try {
			return createCriteria().add(new CriteriaHibernateImpl().like(property, value)).list();
		} catch (final HibernateException e) {
			throw new UserException("Error cargando el objeto "
					+ getEntityType().getSimpleName() , e);
		}
	}

	
	public T getByName(final String name) {
		return getByProperty("name", name);
	}

}
