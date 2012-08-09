package ar.edu.unq.tpi.base.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.repository.GenericRepository;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

@Entity
public class IdGenerator extends PersistentObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    private static IdGenerator INSTANCE = null;

    //

    @Transient
    private static GenericRepository<IdGenerator> repository;

    @Basic
    private Double current = (double) 1 / 10000;

    private IdGenerator() {
//        this.setId("00001");
    }

    public static synchronized IdGenerator getInstance() {
        repository = new GenericRepository<IdGenerator>(IdGenerator.class);
        INSTANCE = null;
        IdGenerator byId;
        if (INSTANCE == null) {
            try {
                byId = (IdGenerator) repository.getById("00001");
            } catch (UserException e) {
                byId = new IdGenerator();
                repository.save(byId);
            }
            INSTANCE = byId;
        }
        return INSTANCE;

    }

    public String nextId() {
        current += 0.0001;
        repository.update(this);
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

}
