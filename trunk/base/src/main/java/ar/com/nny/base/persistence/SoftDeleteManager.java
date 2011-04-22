package ar.com.nny.base.persistence;

import org.hibernate.Session;


/**
 * This class has utility method to configure the soft delete behavior
 * of a Hibernate Session.
 *
 */
public class SoftDeleteManager {

    public static final String DELETED_FLAG = "deletedFlag";
    public static final String SOFT_DELETE_FILTER = "softDeleteFilter";
    public static final String SOFT_DELETE_FILTER_CONDITION = ":" + DELETED_FLAG + "= deleted";


    private SoftDeleteManager() {
    }


    /**
     * Instructs the given session to include only deleted objects in the
     * query results
     */
    public static void readDeletedOnly() {
        getCurrentSession().enableFilter(SOFT_DELETE_FILTER).setParameter(DELETED_FLAG, Boolean.TRUE);
    }


    /**
     * Instructs the given session to include only non deleted objects in the
     * query results. This is the default mode for new sessions.
     */
    public static void readNonDeletedOnly() {
        getCurrentSession().enableFilter(SOFT_DELETE_FILTER).setParameter(DELETED_FLAG, Boolean.FALSE);
    }


    /**
     * Instructs the given session to include all objects in the query results.
     * This means, deleted objects as well as non deleted.
     */
    public static void readAll() {
        getCurrentSession().disableFilter(SOFT_DELETE_FILTER);
    }


    private static Session getCurrentSession() {
        return PersistenceManager.getInstance().getCurrentSession();
    }

}
