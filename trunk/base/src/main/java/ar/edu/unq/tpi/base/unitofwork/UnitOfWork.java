package ar.edu.unq.tpi.base.unitofwork;

import org.hibernate.Transaction;

import ar.edu.unq.tpi.base.persistence.PersistenceManager;
import ar.edu.unq.tpi.base.persistence.SessionContainer;
import ar.edu.unq.tpi.base.persistence.query.TransactionManager;

public class UnitOfWork {

	private Transaction transaction;
	private TransactionManager txManager;
	private final ThreadLocal<SessionContainer> sessionContainer;


	public UnitOfWork(ThreadLocal<SessionContainer> sessionContainer, TransactionManager txManager) {
		this.sessionContainer = sessionContainer;
		this.txManager = txManager;
	}



	public Transaction getTransaction() {
		return transaction;
	}


	public void setTransaction(final Transaction transaction) {
		this.transaction = transaction;
	}


	public void setTxManager(final TransactionManager txManager) {
		this.txManager = txManager;
	}


	public void commit() {
		txManager.commitTransaction(this.transaction, sessionContainer.get().getSession());
	}


	public void rollback() {
		if ( this.transaction != null ) {
			txManager.rollbackTransaction(this.transaction);
		}
	}


	public void close() {
		PersistenceManager.getInstance().close();
	}



	public void begin() {
		this.transaction = txManager.openTransaction(sessionContainer.get().getSession());
		this.transaction.begin();
	}



	public ThreadLocal<SessionContainer> getSessionContainer() {
		return sessionContainer;
	}

}