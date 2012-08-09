package ar.edu.unq.tpi.base.persistence.query;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Handle the operations over the transactions
 *
 */
public enum TransactionManager {

    STANDARD {
        public void commitTransaction(Transaction transaction, Session session) {
            transaction.commit();
            session.flush();
        }

        public Transaction openTransaction(Session session) {
            return session.beginTransaction();
        }

        public void rollbackTransaction(Transaction transaction) {
            transaction.rollback();
        }
    },

    ROLLBACK {
        public void commitTransaction(Transaction transaction, Session session) {
            this.rollbackTransaction(transaction);
        }

        public Transaction openTransaction(Session session) {
            return session.beginTransaction();
        }

        public void rollbackTransaction(Transaction transaction) {
            transaction.rollback();
        }
    },

    DUMMY {
        public void commitTransaction(Transaction transaction, Session session) {
            //nothing to do;
        }

        public Transaction openTransaction(Session session) {
            return null;
        }

        public void rollbackTransaction(Transaction transaction) {
            //nothing to do
        }
    };


    public abstract Transaction openTransaction(Session session);


    public abstract void commitTransaction(Transaction transaction, Session session);


    public abstract void rollbackTransaction(Transaction transaction);

}
