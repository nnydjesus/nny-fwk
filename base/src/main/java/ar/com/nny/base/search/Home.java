package ar.com.nny.base.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.nny.base.common.Observable;
import ar.com.nny.base.dao.GenericDao;
import ar.com.nny.base.utils.IdentificablePersistentObject;
import ar.com.nny.base.utils.ReflectionUtils;

public class Home<T extends IdentificablePersistentObject> {

    public static final String RESULTS = "objects";

    private int proximoId = 1;

    private final List<T> cache = new ArrayList<T>();

    protected GenericDao<T> dao;

    private Class<T> clazz;

    public Home(final Class<T> clazz, final boolean refresh) {
        this(new GenericDao<T>(clazz, clazz.getCanonicalName()), refresh);
    }

    public Home(final Class<T> clazz) {
        this(clazz, true);
    }

    public Home(final GenericDao<T> genreDao, final Boolean refresh) {
        this.dao = genreDao;
        this.clazz = genreDao.getPersistentClass();
        if (refresh) {
            this.refresh();
        }
    }

    public Home(final GenericDao<T> genreDao) {
        this(genreDao, true);
    }

    protected String getProximoId() {
        return this.proximoId++ + "";
    }

    // ********************************************************
    // ** Altas, bajas y modificaciones.
    // ********************************************************

    public void saveOrUpdate(final T object) {
        this.validatingCreation(object);
        this.cache.remove(object);
        this.cache.add(object);
        dao.saveOrUpdate(object);
    }

    protected void validatingCreation(final T object) {
        // Nothing by default
    }

    public void update(final T object) {
        // T replaced = this.buscarPorId(object.getId());
        // this.objects.remove(this.objects.indexOf(replaced));
        this.cache.remove(object);
        this.cache.add(object);
        dao.update(object);
    }

    public void delete(final T object) {
        this.validatingDeleting(object);
        this.cache.remove(object);
        dao.delete(object);
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
        return dao.getById(id);
        // TODO Mejorar el mensaje de error
        // throw new RuntimeException("No se encontro el objeto con el id: " +
        // id);
    }

    public List<T> refresh() {
        this.cache.removeAll(cache);
        this.cache.addAll(dao.getAll());
        return this.cache;
    }

    public List<T> getAll() {
        return this.cache;
    }

    // ********************************************************
    // ** Criterios de busqueda
    // ********************************************************

    protected Predicate getCriterioTodas() {
        return new Predicate() {
            public boolean evaluate(final Object arg) {
                return true;
            }
        };
    }

    protected Predicate getCriterioPorId(final String id) {
        return new Predicate() {
            public boolean evaluate(final Object arg) {
                Observable unaEntidad = (Observable) arg;
                return unaEntidad.getId().toLowerCase().contains(id.toLowerCase());
            }
        };
    }

    public T createExample() {
        return ReflectionUtils.instanciate(this.clazz);
    }

}
