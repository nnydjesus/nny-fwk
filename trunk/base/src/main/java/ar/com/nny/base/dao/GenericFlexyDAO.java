package ar.com.nny.base.dao;


import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Closure;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;

import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.persistence.LoadByIdsQuery;
import ar.com.nny.base.persistence.PersistenceManager;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.persistence.QueryStatementBuilder;
import ar.com.nny.base.persistence.annotations.OrderType;
import ar.com.nny.base.utils.HibernateUtil;
import ar.com.nny.base.utils.QueryStatement;



/**
 * Abstract base class for all application DAOs
 * <p/>
 * There should be one DAO for every persistent class, and they are used to interact with the
 * data store.
 * Typical operations as save, update and delete are inherited, and any specific query can be added
 * to the specific DAO.
 * <p/>
 * All DAOs must implement the default constructor (protected is the suggested visibility,
 * since only the DAORegistry must instantiate them).
 *
 */
@SuppressWarnings("unchecked")
public abstract class GenericFlexyDAO<T extends PersistentObject> {

	@SuppressWarnings("unused")
	private static final Log LOGGER = LogFactory.getLog(GenericDao.class);
	private final GenericDAOMetadata<T> metadata;
	private final static Session session =  PersistenceManager.getInstance().getCurrentSession();


	protected GenericFlexyDAO() {
		this.metadata = new GenericDAOMetadata<T>(this);
	}


	/**
	 * This method should be overridden to return the class that this DAO persists
	 */
	public abstract Class<T> getPersistentClass();


	/**
	 * GenericDAO access to its metadata through this method. Subclasses may
	 * overwrite in order to perform a different metadata retrieval process.
	 */
	protected GenericDAOMetadata<T> getMetadata() {
		return this.metadata;
	}


	/**
	 * Deletes the given instance from the database.
	 * After the instance has been deleted, using it is discouraged.
	 *
	 * @param pgo Object to delete
	 */
	public void delete(final T pgo) {
		try {
			Session session = this.session();
			session.beginTransaction();
			session.delete(pgo);
			session.getTransaction().commit();
		}
		catch ( final HibernateException e ) {
			throw new NonBusinessException("Error removing object " + pgo, e);
		}
	}


	/**
	 * Copy the state of the given object onto the persistent object with the same identifier.
	 * If there is no persistent instance currently associated with the session, it will be loaded.
	 * Return the persistent instance. If the given instance is unsaved, save a copy of and return it as a newly persistent
	 * instance. The given instance does not become associated with the session. This operation cascades to associated
	 * instances if the association is mapped with cascade="merge".
	 */
	public T merge(final T pgo) {
		try {
			return (T) this.session().merge(pgo);
		}
		catch ( final HibernateException e ) {
			throw new NonBusinessException("Error merging object " + pgo, e);
		}
	}


	/**
	 * Restores the soft deleted object. If the object is not soft deleteable it throws
	 * an exception.
	 */
	public void undelete(final T pgo) {
//		if ( !(pgo instanceof SoftDeleteablePersistentObject) ) {
//			throw new NonBusinessException("Cannot undelete non soft deleteable persistent object");
//		}
//
//		if ( !((SoftDeleteablePersistentObject) pgo).isDeleted() ) {
//			throw new NonBusinessException("Attempting to undelete a non deleted instance is illegal: " + pgo);
//		}

//		final Field deletedField = ReflectionUtils.getFieldFromAll(SoftDeleteablePersistentObject.class, "deleted");
//		ReflectionUtils.writeField(pgo, deletedField, false);
//		this.update(pgo);
	}


	/**
	 * Updates the given instance in the database
	 * Actually this method is here for convention, since the objects are automatically synchronized
	 * with the database.
	 * Only useful if you pass a detached instance.
	 *
	 * @param pgo Object to update
	 */
	public void update(final T pgo) {
		try {
			Session session = this.session();
			session.beginTransaction();
			session.update(pgo);
			session.getTransaction().commit();
		}
		catch ( final HibernateException e ) {
			throw new NonBusinessException("Error updating object " + pgo, e);
		}
	}


	/**
	 * Stores the given object into the database.
	 *
	 * @param pgo Object to save
	 */
	public void save(final T pgo) {
		try {
			
			Session session = this.session();
			session.beginTransaction();
			session.save(pgo);
			session.getTransaction().commit();
		}
		catch ( final HibernateException e ) {
			throw new RuntimeException("Error updating object " + pgo, e);
		}
	}


	/**
	 * Stores or Update the given object into the database.
	 *
	 * @param pgo Object to save
	 */
	public void saveOrUpdate(final T pgo) {
		try {
			Session session = this.session();
			session.beginTransaction();
			session.saveOrUpdate(pgo);
			session.getTransaction().commit();
		}
		catch ( final HibernateException e ) {
			throw new RuntimeException("Error updating object " + pgo, e);
		}
	}


	/**
	 * If the entity is lazy returns a uninitialized proxy for the object with the given ID.
	 * Keep in mind that if the class is configured to be lazy, even if the object does not exists an
	 * uninitialized proxy will be returned. The exception will be thrown by the time the object is accessed.
	 */
	public T lazyGetById(final String id) {
		try {
			final T object = (T) this.session().load(this.getPersistentClass(), id);
			return object;
		}
		catch ( final HibernateException e ) {
			throw new RuntimeException("Error loading object " + this.getPersistentClass().getName() + "#" + id, e);
		}
	}


	/**
	 * Returns an instance of the object with the given ID.
	 * If the object is not found in the database, an error will be thrown. If the possibility that an object
	 * with the given identifier doesn't exists is not an error for this case, use getByIdOrNull instead.
	 */
	public T getById(final String id) {
		try {
			final T object = (T) this.session().get(this.getPersistentClass(), id);
			if ( object == null ) {
				throw new NonBusinessException("No object was found of class " + this.getPersistentClass().getName() + " with identifier " + id);
			}
			return object;
		}
		catch ( final HibernateException e ) {
			throw new RuntimeException("Error loading object " + this.getPersistentClass().getName() + "#" + id, e);
		}
	}

	@SuppressWarnings("deprecation")
	public List<T> getByIds(final List<String> _ids) {
		final LoadByIdsQuery loadQuery = new LoadByIdsQuery(this.getPersistentClass());
		return getByIds(_ids, loadQuery);
	}
	
	/**
	 * Will be private soon, use List<T> getByIds(final List<ObjectId> _ids) instead
	 * @param _ids
	 * @param loadQuery
	 * @return
	 */
	@Deprecated
	public List<T> getByIds(final List<String> _ids, final LoadByIdsQuery loadQuery) {
		if(_ids != null && _ids.isEmpty()) {
			throw new RuntimeException("Must specify at least one id");
		}

		try {
			final QueryStatement<T> queryStatement = new QueryStatement<T>(loadQuery.getQuery().replace(loadQuery.getReplaceToken(), "'" + StringUtils.join(_ids, "','") +"'"));
			return queryStatement.find();
		} catch ( final HibernateException e ) {
			throw new RuntimeException("Error loading objects " + this.getPersistentClass().getName() + "#" + _ids, e);
		}

	}

	/**
	 * Returns an instance of the object with the given ID.
	 * If the object is not found in the database, this method will return null.
	 */
	public T getByIdOrNull(final String id) {
		try {
			final T object = (T) this.session().get(this.getPersistentClass(), id);
			return object;
		}
		catch ( final HibernateException e ) {
			throw new RuntimeException("Error loading object " + this.getPersistentClass().getName() + "#" + id, e);
		}
	}


	/**
	 * This method returns all the existing instances.
	 * Be careful using it, since it can cause a performance havoc
	 */
	public List<T> getAll() {
		return this.getAll(false);
	}


	/**
	 * This method returns all the existing instances.
	 * Be careful using it, since it can cause a performance havoc
	 *
	 * @param cacheable true if will use de hibernate query cache
	 */
	public List<T> getAll(final boolean cacheable) {
		return this.getAll(cacheable, null);
	}


	public List<T> getAll(final boolean cacheable, final String region) {
		final QueryStatement<T> query = this.buildGetAllQuery(true);

		if ( cacheable ) {
			query.setCacheable(true);
			if(region != null) {
				query.setCacheRegion(region);
			}
		}
		return this.find(query);
	}


	public List<T> find(final String fieldName, final Object fieldValue) {
		return this.find(fieldName, fieldValue, false);
	}


	public List<T> find(final String fieldName, final Object fieldValue, final boolean cacheable) {
		final QueryStatement<T> queryStatement = this.buildFieldQuery(fieldName, fieldValue);

		if ( cacheable ) {
			queryStatement.setCacheable(true);
		}

		return this.find(queryStatement);
	}


	public List<T> find(final QueryStatementBuilder<T> query) {
		return this.find(query, true);
	}

	public List<T> find(final QueryStatementBuilder<T> query, final LockMode lockMode) {
		return this.find(query, true, lockMode);
	}
	
	public List<T> find(final QueryStatementBuilder<T> query, final boolean naturallyOrder) {
		return this.find(query, naturallyOrder, null);
	}

	public List<T> find(final QueryStatementBuilder<T> query, final boolean naturallyOrder, final LockMode lockMode) {
		final QueryStatement<T> hql = query.getQuery();
		this.ensureQueryCompletion(hql, naturallyOrder);
		return hql.find(lockMode);
	}

	public List<T> find(final String fieldName, final Object fieldValue, final LockMode lockMode) {
		final QueryStatement<T> hql = this.buildFieldQuery(fieldName, fieldValue).getQuery();
                this.ensureQueryCompletion(hql, true);
                return hql.find(lockMode);
	}

	public T findUnique(final String fieldName, final Object fieldValue) {
		return findUnique(fieldName, fieldValue, false);
	}
	
	public T findUnique(final String fieldName, final Object fieldValue, final boolean cacheable) {
		return this.findUnique(this.buildFieldQuery(fieldName, fieldValue), LockMode.NONE, cacheable);
	}
	
	public T findUnique(final String fieldName, final Object fieldValue, final LockMode lockMode) {
		return findUnique(fieldName, fieldValue, lockMode, false);
	}

	public T findUnique(final String fieldName, final Object fieldValue, final LockMode lockMode, final boolean cacheable) {
		return this.findUnique(this.buildFieldQuery(fieldName, fieldValue), lockMode, cacheable);
	}
	

	public T findUnique(final QueryStatementBuilder<T> query) {
		return findUnique(query, LockMode.NONE, false);
	}

	public T findUnique(final QueryStatementBuilder<T> query, final LockMode lockMode, final boolean cacheable) {
		final QueryStatement<T> hql = query.getQuery();
		if(cacheable) {
			hql.setCacheable(true);
		}
		this.ensureQueryCompletion(hql, false);
		return hql.findUnique(lockMode);
	}
	

	public long count() {
		return this.count(new QueryStatement<T>());
	}


	public long count(final QueryStatementBuilder<T> query) {
		final QueryStatement<T> hql = query.getQuery();
		this.ensureQueryCompletion(hql, false);
		return hql.count();
	}


	public void forAllDo(final QueryStatementBuilder<T> query, final Closure<T> closure) {
		final QueryStatement<T> hql = query.getQuery();
		this.ensureQueryCompletion(hql, false);
		hql.forAllDo(closure);
	}


	public void forAllDo(final Closure<T> closure) {
		this.buildGetAllQuery(false).forAllDo(closure);
	}


	/**
	 * Builds the getAllQuery used for getAll and forAllDo methods.
	 */
	private QueryStatement<T> buildGetAllQuery(final boolean naturallyOrder) {
		final QueryStatement<T> query = new QueryStatement<T>();
		query.setCacheable(this.getMetadata().isGetAllOperationCached());
		this.ensureQueryCompletion(query, naturallyOrder);
		return query;
	}


	/**
	 * Builds the fieldQuery used for field search.
	 */
	private QueryStatement<T> buildFieldQuery(final String fieldName, final Object fieldValue) {
		final QueryStatement<T> query = new QueryStatement<T>("where this." + fieldName + " = ?", fieldValue);
		final String region = this.getMetadata().getCachedFields().get(fieldName);
		if ( region != null ) {
			query.setCacheable(true);
			query.setCacheRegion(region);
		}
		return query;
	}


	/**
	 * If the given query hasn't got a from sentence specified add it by default.
	 * Add order by if it's needed
	 */
	private void ensureQueryCompletion(final QueryStatement<T> query, final boolean naturallyOrder) {
		if ( !query.hasFrom() ) {
			query.setFrom(this.getPersistentClass(), "this");
		}
		if ( !query.hasOrderBy() && naturallyOrder ) {
			final Map<String, OrderType> fields = this.getMetadata().getNaturalOrderFields();
			query.addOrderFields(fields);
			this.addIdentityOrderByForNaturalOrder(query, fields);
		}
	}


	/**
	 * If natural order as been specified add id field as part of the order by clause. This method
	 * should be overwrite in custom daos if LegacyPersistentObjects want to use NaturalOrder
	 * capabilities.
	 */
	protected void addIdentityOrderByForNaturalOrder(final QueryStatement<T> query, final Map<String, OrderType> fields) {
		if ( !fields.containsKey("id") ) {
			query.addOrderField("this.id", OrderType.ASC);
		}
	}


	protected Session session() {
//		final AlternateSessionFactory annotation = this.getPersistentClass().getAnnotation(AlternateSessionFactory.class);
//		if ( annotation != null ) {
//			return PersistenceManager.getInstance().getCurrentSession(annotation.value());
//		}
//		else {
//			return PersistenceManager.getInstance().getCurrentSession();
//		}
//		return HibernateUtil.getSession();
		return session;
	}

}
