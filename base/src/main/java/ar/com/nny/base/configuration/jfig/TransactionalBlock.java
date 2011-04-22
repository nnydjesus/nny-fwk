package ar.com.nny.base.configuration.jfig;


/**
 * Runs a block inside a session/transaction
 *
 * @author Claudio
 */
public class TransactionalBlock extends AbstractBlockDecorator {

    public TransactionalBlock(final CodeBlock block) {
        super(block);
    }


    public Object execute() throws Exception {
        final TransactionType transactionType = this.getTransactionType();
//        if ( PersistenceManager.getInstance().isInTransaction() ) {
            return transactionType.executeWithTransactionPresent(this.block);
//        }
//        else {
//            return transactionType.executeWithoutTransactionPresent(this.block);
//        }
    }


    private TransactionType getTransactionType() {
        final Transaction serviceMethodMetadata = this.getMetadata(Transaction.class);
        if ( serviceMethodMetadata == null ) {
            return TransactionType.REQUIRES;
        }
        else {
            return serviceMethodMetadata.value();
        }
    }

}
