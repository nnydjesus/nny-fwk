package ar.edu.unq.tpi.base.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.edu.unq.tpi.base.configuration.ApplicationPersistentRegistryReader;
import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.persistence.annotations.Repository;
import ar.edu.unq.tpi.util.common.ReflectionUtils;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

/**
 * This class holds the Repositorys for the application.
 * When aRepository is needed in the application, it must be obtained through this class.
 *
 */
public class RepositoryResolver {

    private static final Log LOGGER = LogFactory.getLog(RepositoryResolver.class);

	private static final RepositoryResolver INSTANCE = new RepositoryResolver();

	/**
	 * Key: PersistentClass (Class)
	 * Value: Repository for PersistentClass (GenericRepository)
	 */
	private final Map<Class, GenericRepository> registry = new HashMap();
	private final Map<Class, GenericRepository> customRepositorys = new HashMap();

	private RepositoryResolver() {
		this.initRegistry();
	}


	/**
	 * Inits the Repository Registry. It instanciates arepository class for each registered persistent
	 * class in the system.
	 * <p/>
	 * ClasslessRepository is assigned to thouse classes that have no repository annotation
	 */
	private void initRegistry() {
		LOGGER.debug("Initting repositoryResolver registry...");
		final Collection<Class<? extends PersistentObject>> classes = ApplicationPersistentRegistryReader.getInstance().getAllPersistentClasses();
		for ( final Class<? extends PersistentObject> persistentClass : classes ) {
			final Repository repositoryAnnotation = persistentClass.getAnnotation(Repository.class);
			if ( repositoryAnnotation != null ) {
				final GenericRepository repository = ReflectionUtils.instanciate(repositoryAnnotation.value());
				this.registry.put(persistentClass, repository);
				this.customRepositorys.put(repository.getClass(), repository);
			}
			else {
				this.registry.put(persistentClass, new GenericRepository(persistentClass));
			}
		}
	}


	private <T extends PersistentObject> GenericRepository<T> findRepository(final Class<T> persistentClass) {
		final GenericRepository repository = this.registry.get(persistentClass);
		if ( repository == null ) {
			throw new UserException("No Repository for persistent class " + persistentClass + " is registered.");
		}
		return repository;
	}


	private <D extends GenericRepository> D findCustomRepository(final Class<D> repositoryType) {
		final GenericRepository repository = this.customRepositorys.get(repositoryType);
		if ( repository == null ) {
			throw new UserException("No Custom Repository of type " + repositoryType + " is registered. Are you using a custom repository and did you forgotten to annotate type with the @Repository annotation?");
		}
		return (D) repository;
	}


	public static <T extends PersistentObject> GenericRepository<T> getRepositoryFor(final Class<T> persistentClass) {
		return INSTANCE.findRepository(persistentClass);
	}


	public static <T extends PersistentObject> GenericRepository<T> getRepositoryFor(final T persistentObject) {
		return (GenericRepository<T>) INSTANCE.findRepository(persistentObject.getClass());
	}


	public static GenericRepository getCustomRepository(final Class repositoryType) {
		return INSTANCE.findCustomRepository(repositoryType);
	}

}
