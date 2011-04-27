package ar.edu.unq.tpi.util.services.services;

import ar.edu.unq.tpi.commons.configuration.jfig.Transaction;
import ar.edu.unq.tpi.commons.configuration.jfig.TransactionType;


@Service(denyable=true)
public class CommandService {

    @Transaction(TransactionType.REQUIRES)
    public void runRunnable(final Runnable runnable) {
        runnable.run();
    }

}
