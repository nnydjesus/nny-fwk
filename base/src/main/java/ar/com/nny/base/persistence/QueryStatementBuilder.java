package ar.com.nny.base.persistence;


import ar.com.nny.base.utils.QueryStatement;

public interface QueryStatementBuilder<T extends PersistentObject> {

    public QueryStatement<T> getQuery();

}
