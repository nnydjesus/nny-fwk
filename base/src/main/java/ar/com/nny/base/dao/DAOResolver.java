package ar.com.nny.base.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.configuration.ApplicationRegistryReader;
import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.persistence.annotations.DAO;
import ar.com.nny.base.utils.ReflectionUtils;

/**
 * This class holds the DAOs for the application.
 * When a DAO is needed in the application, it must be obtained through this class.
 *
 * @author Gunther Schneider
 */
public class DAOResolver {

    private static final Log LOGGER = LogFactory.getLog(DAOResolver.class);

	private static final DAOResolver INSTANCE = new DAOResolver();

	/**
	 * Key: PersistentClass (Class)
	 * Value: DAO for PersistentClass (GenericDAO)
	 */
	private final Map<Class, GenericDao> registry = new HashMap();
	private final Map<Class, GenericDao> customDAOS = new HashMap();

	private DAOResolver() {
		this.initRegistry();
	}


	/**
	 * Inits the DAO Registry. It instanciates a dao class for each registered persistent
	 * class in the system.
	 * <p/>
	 * ClasslessDAO is assigned to thouse classes that have no DAO annotation
	 */
	private void initRegistry() {
		LOGGER.debug("Initting DAOResolver registry...");
		final Collection<Class<? extends PersistentObject>> classes = ApplicationRegistryReader.getInstance().getAllPersistentClasses();
		for ( final Class<? extends PersistentObject> persistentClass : classes ) {
			final DAO daoAnnotation = persistentClass.getAnnotation(DAO.class);
			if ( daoAnnotation != null ) {
				final GenericDao dao = ReflectionUtils.instanciate(daoAnnotation.value());
				this.registry.put(persistentClass, dao);
				this.customDAOS.put(dao.getClass(), dao);
			}
			else {
				this.registry.put(persistentClass, new GenericDao(persistentClass));
			}
		}
	}


	private <T extends PersistentObject> GenericDao<T> findDAO(final Class<T> persistentClass) {
		final GenericDao dao = this.registry.get(persistentClass);
		if ( dao == null ) {
			throw new NonBusinessException("No DAO for persistent class " + persistentClass + " is registered.");
		}
		return dao;
	}


	private <D extends GenericDao> D findCustomDAO(final Class<D> daoType) {
		final GenericDao dao = this.customDAOS.get(daoType);
		if ( dao == null ) {
			throw new NonBusinessException("No Custom DAO of type " + daoType + " is registered. Are you using a custom dao and did you forgotten to annotate type with the @DAO annotation?");
		}
		return (D) dao;
	}


	public static <T extends PersistentObject> GenericDao<T> getDAOFor(final Class<T> persistentClass) {
		return INSTANCE.findDAO(persistentClass);
	}


	public static <T extends PersistentObject> GenericDao<T> getDAOFor(final T persistentObject) {
		return (GenericDao<T>) INSTANCE.findDAO(persistentObject.getClass());
	}


	public static GenericDao getCustomDAO(final Class daoType) {
		return INSTANCE.findCustomDAO(daoType);
	}

}
