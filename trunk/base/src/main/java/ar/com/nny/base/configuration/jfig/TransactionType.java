package ar.com.nny.base.configuration.jfig;


public enum TransactionType {

    /**
     * Method requires to run inside a transaction (could join to an old one).
     */
    REQUIRES {
        @Override
		public Object executeWithTransactionPresent(final CodeBlock block) throws Exception {
			return block.execute();
        }

        @Override
		public Object executeWithoutTransactionPresent(final CodeBlock block) throws Exception {
            return block.execute();
//            return PersistenceManager.getInstance().executeTransactionalContext(block);
        }
    },

    /**
     * Method requires to run in a new transaction. Old transaction, if exists,
     * should be suspended.
     */
    REQUIRES_NEW {
        @Override
		public Object executeWithTransactionPresent(final CodeBlock block) throws Exception {
            return block.execute();
//            return PersistenceManager.getInstance().executeTransactionalContext(block);
        }

        @Override
		public Object executeWithoutTransactionPresent(final CodeBlock block) throws Exception {
            return block.execute();
//            return PersistenceManager.getInstance().executeTransactionalContext(block);
        }
    };



    public abstract Object executeWithTransactionPresent(CodeBlock block) throws Exception;


    public abstract Object executeWithoutTransactionPresent(CodeBlock block) throws Exception;

}
