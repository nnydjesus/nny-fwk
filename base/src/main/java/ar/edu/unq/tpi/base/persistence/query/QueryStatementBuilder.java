package ar.edu.unq.tpi.base.persistence.query;


import ar.edu.unq.tpi.base.persistence.PersistentObject;

public interface QueryStatementBuilder<T extends PersistentObject> {

    public QueryStatement<T> getQuery();

}
