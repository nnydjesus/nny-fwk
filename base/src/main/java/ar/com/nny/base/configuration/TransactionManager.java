package ar.com.nny.base.configuration;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Handle the operations over the transactions
 *
 * @author Gunther Schneider
 */
public enum TransactionManager {

    STANDARD {
        public void commitTransaction(Transaction transaction) {
            transaction.commit();
        }

        public Transaction openTransaction(Session session) {
            return session.beginTransaction();
        }

        public void rollbackTransaction(Transaction transaction) {
            transaction.rollback();
        }
    },

    ROLLBACK {
        public void commitTransaction(Transaction transaction) {
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
        public void commitTransaction(Transaction transaction) {
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


    public abstract void commitTransaction(Transaction transaction);


    public abstract void rollbackTransaction(Transaction transaction);

}
