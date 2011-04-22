package ar.com.nny.base.persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;

/**
 * This class is responsible for handling the initialization and configuration of the
 * persistence framework. This is where the Hibernate Session Factory is kept.
 * Also, all transaction handling is done here
 *
 */
public class PersistenceManager {

    private static Log LOGGER = LogFactory.getLog(PersistenceManager.class);
    private static PersistenceManager INSTANCE;
    private static org.hibernate.classic.Session session;
    
    private static AnnotationConfiguration cfg = new AnnotationConfiguration().configure("hibernate.cfg.xml");
    private SessionFactory sessionFactory;

//    static{
//        final ConfigurationFactory configurationBuilder = new ConfigurationFactory();
//        cfg = configurationBuilder.buildConfiguration(null, BaseConfiguration.getHibernateProperties());
//    }

    public static PersistenceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersistenceManager();
        }
        return INSTANCE;
    }



//	private Map<String, AnnotationConfiguration> alternateConfigurations = new HashMap();
//    private Map<String, SessionFactory> alternateFactories = new HashMap();


    private PersistenceManager() {
        LOGGER.debug("Starting persistence framework...");
      
        this.sessionFactory = this.cfg.buildSessionFactory();
//        this.cfg = configurationBuilder.buildConfiguration(null, FlexyConfiguration.getHibernateProperties());
//        final Collection<Class<? extends PersistentObject>> persistentClasses = ApplicationRegistryReader.getInstance().getAllPersistentClasses();
//        for (Class<? extends PersistentObject> class1 : persistentClasses) {
//        	cfg.addAnnotatedClass(class1);
//		}
        sessionFactory = cfg.buildSessionFactory();
//        final Map<String, Properties> alternates = FlexyConfiguration.getAlternateHibernateProperties();
//        for (final Map.Entry<String, Properties> each : alternates.entrySet()) {
//            final FlexyAnnotationConfiguration alternateConfiguration = configurationBuilder.buildConfiguration(each.getKey(), each.getValue());
//            this.alternateConfigurations.put(each.getKey(), alternateConfiguration);
//            this.alternateFactories.put(each.getKey(), alternateConfiguration.buildSessionFactory());
//        }
        LOGGER.debug("Persistence framework ready");
        session = sessionFactory.openSession();
    }


    /**
     * Returns the Hibernate configuration object
     */
    public Configuration getConfiguration() {
        return this.cfg;
    }


    public SessionFactory getDefaultSessionFactory() {
        return this.sessionFactory;
    }


    /**
     * This destroys the sessionFactory and forces hibernate to a full reload.
     */
    public void destroy() {
        INSTANCE = null;
    }


//    public UnitOfWork getCurrentUnitOfWork(final String name) {
//        final UnitOfWork uow = ContextSessionFacade.getCurrentUOWStack().peek(name);
//        if (uow == null) {
//            throw new NonBusinessException("No open database session. Make sure the call is within a transactional context");
//        }
//        return uow;
//    }


    /**
     * This method returns the current session. The session is associated with the thread.
     *
     * @throws NonbusinessException If there is no current session (meaning there will be a
     *                              database access attempt outside transactional scope).
     */
    public Session getCurrentSession() {
        return getCurrentSession(null);
    }


    public Session getCurrentSession(final String value) {
//        final UnitOfWork uow = this.getCurrentUnitOfWork(value);
//        return uow.getSession();
    	if(!session.isOpen())
    		session = sessionFactory.openSession();
		return session;
    }


    public FullTextSession getCurrentFullTextSession() {
        return (FullTextSession) getCurrentSession(null);
    }


    public FullTextSession getCurrentFullTextSession(final String value) {
        return (FullTextSession) getCurrentSession(value);
    }


    /**
     * Flushes and clears the Hibernate Session.
     */
    public void clearCurrentSession() {
        final Session currentSession = this.getCurrentSession();
        currentSession.flush();
        currentSession.clear();
    }


    /**
     * Return true if a session is already opened
     */
//    public boolean isInSession() {
//        return this.isInSession(null);
//    }


//    public boolean isInSession(final String name) {
//        return ContextSessionFacade.getCurrentUOWStack().peek(name) != null;
//    }


    /**
     * Return true if a session is already opened
     */
//    public boolean isInTransaction() {
//        return this.isInTransaction(null);
//    }


//    public boolean isInTransaction(final String name) {
//        final UnitOfWork peeked = ContextSessionFacade.getCurrentUOWStack().peek(name);
//        if (peeked == null) {
//            return false;
//        }
//        return peeked.getTransaction() != null;
//    }


    /**
     * Calling this method sets the PersistenceManager in a rollback only stance.
     * Calls to commit will be rollbacked.
     */
//    public void avoidTransactionCommit() {
//        this.avoidTransactionCommit(null);
//    }


//    public void avoidTransactionCommit(final String name) {
//        this.getCurrentUnitOfWork(name).setTxManager(TransactionManager.ROLLBACK);
//    }


    /**
     * This method is used to reset a prior call to avoidTransactionCommit.
     * Leaves the PersistenceManager in the regular stance.
     */
//    public void allowTransactionCommit() {
//        this.allowTransactionCommit(null);
//    }


//    public void allowTransactionCommit(final String name) {
//        this.getCurrentUnitOfWork(name).setTxManager(TransactionManager.STANDARD);
//    }


    /**
     * Executes the given predicate inside a context with a database session and a transaction opened .
     * This method should only be used in a service entry control point, and not from within business
     * code to start and end transactions.
     * <p/>
     * This method will open a session (and transaction) upon entering. Then will call the execute method
     * of the block.
     * After the block finished execution, the session will be closed (and transaction commited).
     * If an exception is thrown from the block, the transaction will be rollbacked.
     *
     * @param block Code block to execute
     * @return Whatever the execute method of the block returns.
     */
//    public Object executeTransactionalContext(final CodeBlock block) {
//        return this.executeTransactionalContext(block, UnitOfWorkFactory.SIMPLE);
//    }
////
////
//    public Object executeTransactionalContext(final CodeBlock block, final UnitOfWorkFactory uowFactory) {
//        UnitOfWork uow = DUMMY_UOW;
//        final Collection<UnitOfWork> alternates = new ArrayList();
//
//        FlushMode previousFlushMode = null;
//        try {
//          //  uow = uowFactory.build(ContextSessionFacade.getCurrentUOWStack().peek(null), this.sessionFactory);
//           // ContextSessionFacade.getCurrentUOWStack().push(uow);
//
//            //Open alternates
//            //TODO: quizas no necesitemos abrir session sobre todas las tx
//            for (final Map.Entry<String, SessionFactory> each : this.alternateFactories.entrySet()) {
//                final String alternateName = each.getKey();
////                final UnitOfWork alternate = uowFactory.build(ContextSessionFacade.getCurrentUOWStack().peek(alternateName), each.getValue());
//                final UnitOfWork alternate = uowFactory.build(uow, each.getValue());
//                alternate.setSessionName(alternateName);
//
//                alternates.add(alternate);
//              //  ContextSessionFacade.getCurrentUOWStack().push(alternate);
//            }
//
//            previousFlushMode = this.setFlushMode(FlushMode.AUTO);
//            SoftDeleteManager.readNonDeletedOnly();
//
//            // Execute
//            final Object returnValue = block.execute();
//
//            // Commit & Go
//            uow.commit();
//            for (final UnitOfWork alternate : alternates) {
//                alternate.commit();
//            }
//
//            return returnValue;
//
//        }
//        catch (final Throwable t) {
//            uow.rollback();
//            for (final UnitOfWork alternate : alternates) {
//                alternate.rollback();
//            }
//
//            if (t instanceof Error) {
//                throw (Error) t;
//            } else
//                throw new NonBusinessException("Error handling database session", t);
//        }
//        finally {
//            try {
//                if (previousFlushMode != null) {
//                    this.setFlushMode(previousFlushMode);
//                }
//                uow.close();
//               // ContextSessionFacade.getCurrentUOWStack().pop();
//
//
//                for (final UnitOfWork alternate : alternates) {
//                    alternate.close();
//                   // ContextSessionFacade.getCurrentUOWStack().pop();
//                }
//            }
//            catch (final Exception e) {
//                LOGGER.error("Error closing sessions", e);
//            }
//        }
//    }


    public FlushMode setFlushMode(final FlushMode mode) {
        final Session currentSession = this.getCurrentSession();
        final FlushMode previousFlushMode = currentSession.getFlushMode();
        currentSession.setFlushMode(mode);
        return previousFlushMode;
    }

    
    public static Configuration getStaticConfiguration(){
    	return cfg;
    }

    public void flush() {
        final Session currentSession = this.getCurrentSession();
        currentSession.flush();
    }


	public void close() {
		this.clearCurrentSession();
		this.session.close();
	}


}
