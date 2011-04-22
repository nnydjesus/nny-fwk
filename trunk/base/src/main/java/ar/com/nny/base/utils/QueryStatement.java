package ar.com.nny.base.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Closure;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.persistence.PersistenceManager;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.persistence.QueryStatementBuilder;
import ar.com.nny.base.persistence.annotations.ConverterResolver;
import ar.com.nny.base.persistence.annotations.OrderType;


/**
 * This class represents a Query to the database.
 * The language for the query is HQL.
 *
 */
@SuppressWarnings("unchecked")
public class QueryStatement<T extends PersistentObject> implements QueryStatementBuilder<T> {

    private StringBuilder queryString = new StringBuilder();
	private List parameters = new ArrayList();

    private LinkedHashMap<String, OrderType> order = new LinkedHashMap<String, OrderType>();

    private int firstResult = -1;
    private int finalResult = -1;
    private int maxResults = -1;

    private boolean cacheable = false;
    private String cacheRegion = null;

//    private boolean naturallyOrderResults;


    /**
     * Constructs an empty query
     */
    public QueryStatement() {
    }


    /**
     * Constructs a query initialized with the given query string
     * This is a shortcut method for simple queries
     */
    public QueryStatement(final String query) {
        this.addQuery(query);
    }


    /**
     * Constructs a Query initialized with the given query string and parameters
     * This is a shortcut method for simple queries
     */
    public QueryStatement(final String query, final Object... parameters) {
        this.addQuery(query);
        for ( final Object param : parameters ) {
            this.addParameter(param);
        }
    }


    /**
     * Adds the string to the end of the query
     * Automatically adds a space at the end if none is present
     */
    public QueryStatement<T> addQuery(final String query) {
        this.queryString.append(query);
        if ( query.charAt(query.length() - 1) != ' ' ) {
            this.queryString.append(" ");
        }
        return this;
    }


    /**
     * Adds the parameter to the query. Parameters are binded to the "?" in the query string
     */
    public QueryStatement<T> addParameter(final Object parameter) {
        this.parameters.add(parameter);
        return this;
    }


    /**
     * Sets the pagination parameters.
     *
     * @param firstResult The first result that should be returned by this query (inclusive). Results are 0 based
     * @param finalResult The final result that should be returned by this query (inclusive).
     */
    public QueryStatement<T> setPaginationParameters(final int firstResult, final int finalResult) {
        this.firstResult = firstResult;
        this.finalResult = finalResult;
        return this;
    }


    /**
     * Executes the query returning a list with the results
     */
    public List<T> find() {
    	return this.find(null);
    }

    /**
     * Executes the query returning a list with the results
     */
    public List<T> find(final LockMode lockMode) {
        final String query = this.generateQueryString();
        try {
            final Query hibernateQuery = this.buildHibernateQuery(query, this.parameters);
            this.setPaginationToQuery(hibernateQuery);
            this.setCache(hibernateQuery);
            if ( this.maxResults > 0 ) {
                hibernateQuery.setMaxResults(this.maxResults);
            }
            if(lockMode != null) {
            	hibernateQuery.setLockMode("this", lockMode);
            }
            return hibernateQuery.list();
        }
        catch ( final HibernateException e ) {
            throw new RuntimeException("Error executing query: " + query, e);
        }
    }
    
    public void forAllDo(final Closure<T> closure) {
        final String query = this.generateQueryString();
        try {
            final Query hibernateQuery = this.buildHibernateQuery(query, this.parameters);
            this.setPaginationToQuery(hibernateQuery);
            this.setCache(hibernateQuery);
            if ( this.maxResults > 0 ) {
                hibernateQuery.setMaxResults(this.maxResults);
            }

            for ( final Iterator<T> iterator = hibernateQuery.iterate(); iterator.hasNext(); ) {
                closure.execute(iterator.next());
            }
        }
        catch ( final HibernateException e ) {
            throw new RuntimeException("Error executing query: " + query, e);
        }
    }


    /**
     * Executes the query and returns it's only result. If there is more than one result, an exception
     * will be thrown.
     * @param lockMode 
     */
    public T findUnique(final LockMode lockMode) {
        final String query = this.generateQueryString();
        try {
            final Query hibernateQuery = this.buildHibernateQuery(query, this.parameters);
            this.setCache(hibernateQuery);
            hibernateQuery.setLockMode("this", lockMode);
            return (T)hibernateQuery.uniqueResult();
        }
        catch ( final HibernateException e ) {
            throw new RuntimeException("Error executing query: " + this.queryString, e);
        }
    }


    /**
     * Counts the results that this query would return if executed
     */
    public long count() {
        final String query = this.generateQueryString();
        try {
            String countQuery;

            final int indexOfFrom = query.toLowerCase().indexOf("from");
            if ( indexOfFrom == 0 ) {
                countQuery = this.ripFetchJoins("select count(*) " + query);
            }
            else {
                final String select = query.substring(0, indexOfFrom - 1);
                final String rippedQuery = query.substring(indexOfFrom);
                if ( select.indexOf("distinct") == -1 ) {
                    countQuery = this.ripFetchJoins("select count(*) " + rippedQuery);
                }
                else {
                    countQuery = this.ripFetchJoins("select count(distinct this) " + rippedQuery);
                }
            }
            final Query hibernateQuery = this.buildHibernateQuery(this.ripOrderBy(countQuery), this.parameters);
            this.setCache(hibernateQuery);
            return (Long) hibernateQuery.list().get(0);
        }
        catch ( final HibernateException e ) {
            throw new RuntimeException("Error counting query: " + query, e);
        }
    }


    /**
     * Executes the update or remove query. This method should be called when the query is not a select,
     * but an update or remove.
     *
     * @return Number of affected objects
     */
    public int update() {
        final String query = this.generateQueryString();
        try {
            final Query hibernateQuery = this.buildHibernateQuery(query, this.parameters);
            return hibernateQuery.executeUpdate();
        }
        catch ( final HibernateException e ) {
            throw new RuntimeException("Error executing update/delete query: " + query, e);
        }
    }


    /**
     * Sets a cache region in which to store the cached results of the query.
     * If  region is set, the default one will be used (provided that the query is set as cacheable)
     */
    public QueryStatement<T> setCacheRegion(final String region) {
        this.cacheRegion = region;
        return this;
    }


    public QueryStatement<T> setCacheable(final boolean cacheable) {
        this.cacheable = cacheable;
        return this;
    }


    /**
     * Creates a hibernate query from a string.
     */
    private Query buildHibernateQuery(final String string, final List params) {
        final Query hibernateQuery = this.session().createQuery(string);
        int index = 0;
        for ( final Iterator i = params.iterator(); i.hasNext(); ) {
            final Object param = i.next();
            // Check if this is a custom type
            final Type type = ConverterResolver.getTypeFor(param);
            if ( type != null ) {
                hibernateQuery.setParameter(index, param, type);
            }
            else {
                hibernateQuery.setParameter(index, param);
            }
            index++;
        }
        return hibernateQuery;
    }


    private void setCache(final Query hibernateQuery) {
        if ( this.cacheable ) {
            hibernateQuery.setCacheable(this.cacheable);
            if ( this.cacheRegion != null ) {
                hibernateQuery.setCacheRegion(this.cacheRegion);
            }
        }
    }


    private void setPaginationToQuery(final Query hibernateQuery) {
        if ( this.firstResult != -1 && this.finalResult != -1 ) {
            hibernateQuery.setFirstResult(this.firstResult);
            hibernateQuery.setMaxResults(this.finalResult - this.firstResult + 1);
        }
    }


    /**
     * Removes the left join fetch from the query, to allow count on that query
     */
    private String ripFetchJoins(String query) {
        final String ripString = "left join fetch this.";
        int indexStart;
        while ( (indexStart = query.indexOf(ripString)) >= 0 ) {
            // Search space after left fetch join this.xxxx
            final int indexEnd = query.indexOf(" ", indexStart + ripString.length() - 1);
            if ( indexEnd == -1 ) {
                query = query.substring(0, indexStart);
            }
            else {
                query = query.substring(0, indexStart) + query.substring(indexEnd, query.length());
            }
        }
        return query;
    }


    private String ripOrderBy(final String query) {
        final int indexOfOrder = query.toLowerCase().indexOf("order by");
        if ( indexOfOrder == -1 ) {
            return query;
        }
        else {
            return query.substring(0, indexOfOrder - 1);
        }
    }


    /**
     * @return true if the from clause has been specified, either by query string or
     *         programaticaly through setFrom method.
     */
    public boolean hasFrom() {
        return this.hasExplicitFrom();
    }


    /**
     * @return true if the from clause has been specified by query string.
     */
    public boolean hasExplicitFrom() {
        final String query = this.queryString.toString();
        return query.startsWith("from ") || query.contains(" from ");
    }


    /**
     * Add from clause to this query.
     *
     * @throws NonBusinessException if the from was already specified.
     */
    public void setFrom(final Class persistentClass, final String alias) {
        if ( this.hasFrom() ) {
            throw new RuntimeException("From already specified for query: " + this.queryString);
        }
        final String fromString = new StringBuilder("from ").append(persistentClass.getName()).append(" ").append(alias).append(" ").toString();
        this.queryString.insert(0, fromString);
    }


    /**
     * @return true if the order by has been specified, either by query string or
     *         programaticaly through addOrderField method.
     */
    public boolean hasOrderBy() {
        return !this.order.isEmpty() || this.hasExplicitOrderBy();
    }


    /**
     * @return true if the order by has been specified by query string.
     */
    private boolean hasExplicitOrderBy() {
        final String query = this.queryString.toString();
        return query.contains(" order by ");
    }


    /**
     * Add order over a field
     *
     * @throws NonBusinessException if order by has been explicitly set before.
     */
    public void addOrderField(final String fieldName, final OrderType orderType) {
        if ( this.hasExplicitOrderBy() ) {
            throw new RuntimeException("Order by alredy specified for query: " + this.queryString);
        }
        this.order.put(fieldName, orderType);
    }


    /**
     * Add order over fields
     *
     * @throws NonBusinessException if order by has been explicitly set before.
     */
    public void addOrderFields(final Map<String, OrderType> fields) {
        if ( this.hasExplicitOrderBy() ) {
            throw new RuntimeException("Order by alredy specified for query: " + this.queryString);
        }
        this.order.putAll(fields);
    }


    private String generateQueryString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.queryString);

        //Append order to the query
        if ( !this.order.isEmpty() ) {
            builder.append(" order by ");
            for ( final Iterator<Entry<String, OrderType>> iterator = this.order.entrySet().iterator(); iterator.hasNext(); ) {
                final Entry<String, OrderType> orderEntry = iterator.next();
                final String fieldOrderString = orderEntry.getValue().getOrderString(orderEntry.getKey());
                builder.append(fieldOrderString);

                if ( iterator.hasNext() ) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }


    public int getMaxResults() {
        return this.maxResults;
    }


    public void setMaxResults(final int _maxResults) {
        this.maxResults = _maxResults;
    }


    //TODO: OMG! This should be done over the correct session. Entity could be using an alternate session
    private Session session() {
        return PersistenceManager.getInstance().getCurrentSession();
    }




    public QueryStatement<T> getQuery() {
        return this;
    }


}
