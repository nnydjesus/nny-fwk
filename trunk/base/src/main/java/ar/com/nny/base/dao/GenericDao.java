package ar.com.nny.base.dao;

import java.util.List;

import ar.com.nny.base.persistence.PersistentObject;

@SuppressWarnings("unchecked")
public class GenericDao<T extends PersistentObject> extends GenericFlexyDAO<T> {

    private Class t;

    private String className;

    public GenericDao() {
    }

    public GenericDao(final Class t) {
        this(t, "");
    }

    public GenericDao(final Class t, final String className) {
        this.t = t;
        this.setClassName(className);
    }

    @Override
    public void save(final T object) {
        // Session s = HibernateUtil.getSession();
        // s.beginTransaction();
        // s.save(object);
        // s.getTransaction().commit();
        super.save(object);
    }

    @Override
    public void update(final T object) {
        // Session s = session();
        // s.beginTransaction();
        // s.update(object);
        // s.getTransaction().commit();
        super.update(object);
    }

    @Override
    public List<T> getAll() {
        // Session s = HibernateUtil.getSession();
        // List<T> vuelos = s.createQuery("from "+this.className).list();
        // return vuelos;
        return super.getAll();
    }

    @Override
    public Class<T> getPersistentClass() {
        return t;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    // @Override
    // public Class<T> getPersistentClass() {
    // return t;
    // }

}
