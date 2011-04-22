package ar.com.nny.base.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.com.nny.base.dao.GenericDao;
import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.utils.IdentificablePersistentObject;

@Entity
public class IdGenerator extends IdentificablePersistentObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private static IdGenerator INSTANCE = null;

    //

    @Transient
    private static GenericDao<PersistentObject> dao;

    @Basic
    private Double current = (double) 1 / 10000;

    private IdGenerator() {
        this.setId("00001");
    }

    public static synchronized IdGenerator getInstance() {
        dao = new GenericDao<PersistentObject>(IdGenerator.class);
        INSTANCE = null;
        IdGenerator byId;
        if (INSTANCE == null) {
            try {
                byId = (IdGenerator) dao.getById("00001");
            } catch (NonBusinessException e) {
                byId = new IdGenerator();
                dao.save(byId);
            }
            INSTANCE = byId;
        }
        return INSTANCE;

    }

    public String nextId() {
        current += 0.0001;
        dao.update(this);
        return current.toString();
    }

    @Override
    public String[] atributos() {
        return null;
    }

    public static void main(final String[] args) {
        // System.out.println((double)1/1000);
        System.out.println(IdGenerator.getInstance().nextId());
    }

    @Override
    public String getName() {
        return null;
    }

}
