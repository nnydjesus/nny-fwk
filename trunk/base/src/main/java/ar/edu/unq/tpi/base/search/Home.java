package ar.edu.unq.tpi.base.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.repository.GenericRepository;
import ar.edu.unq.tpi.util.common.ReflectionUtils;

public class Home<T extends PersistentObject> {

    public static final String RESULTS = "objects";

    private final List<T> cache = new ArrayList<T>();

    protected GenericRepository<T> repository;

    private Class<T> clazz;

    public Home(final Class<T> clazz, final boolean refresh) {
        this(new GenericRepository<T>(clazz), refresh);
    }

    public Home(final Class<T> clazz) {
        this(clazz, true);
    }

    public Home(final GenericRepository<T> genreDao, final Boolean refresh) {
        this.repository = genreDao;
        this.clazz = genreDao.getEntityType();
        if (refresh) {
            this.refresh();
        }
    }

    public Home(final GenericRepository<T> genreDao) {
        this(genreDao, true);
    }

    // ********************************************************
    // ** Altas, bajas y modificaciones.
    // ********************************************************

    public void saveOrUpdate(final T object) {
        this.validatingCreation(object);
        this.cache.remove(object);
        this.cache.add(object);
        repository.saveOrUpdate(object);
    }
    
    public void save(final T object) {
        this.validatingCreation(object);
        this.cache.remove(object);
        this.cache.add(object);
        repository.save(object);
    }

    protected void validatingCreation(final T object) {
        // Nothing by default
    }

    public void update(final T object) {
        // T replaced = this.buscarPorId(object.getId());
        // this.objects.remove(this.objects.indexOf(replaced));
        this.cache.remove(object);
        this.cache.add(object);
        repository.update(object);
    }

    public void delete(final T object) {
        this.validatingDeleting(object);
        this.cache.remove(object);
        repository.delete(object);
    }

    protected void validatingDeleting(final T object) {
        // Nothing by default
    }

    // ********************************************************
    // ** Busquedas
    // ********************************************************

    @SuppressWarnings("unchecked")
    public List<T> searchByExample(final T example) {
        return (List<T>) CollectionUtils.select(this.cache, this.getCriteria(example));
    }

    protected Predicate getCriteria(final T example) {
        return this.getCriterioTodas();
    }

    public T getById(final String id) {
        return repository.getByName(id);
    }
    
    public T getByName(final String name) {
    	return repository.getByName(name);
    }
    
    public List<T> getsByName(final String name) {
    	return repository.getsByProperty("name", name);
    }

    public List<T> refresh() {
        this.cache.removeAll(cache);
        this.cache.addAll(repository.getAll());
        return this.cache;
    }

    public List<T> getAll() {
        return this.cache;
    }

    // ********************************************************
    // ** Criterios de busqueda
    // ********************************************************

    protected Predicate<T> getCriterioTodas() {
        return new Predicate<T>() {
            public boolean evaluate(final T arg) {
                return true;
            }
        };
    }

    public T createExample() {
        return ReflectionUtils.instanciate(this.clazz);
    }

}
