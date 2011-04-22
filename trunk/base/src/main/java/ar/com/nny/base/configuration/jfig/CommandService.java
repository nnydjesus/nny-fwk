package ar.com.nny.base.configuration.jfig;


@Service(denyable=true)
public class CommandService {

    @Transaction(TransactionType.REQUIRES)
    public void runRunnable(final Runnable runnable) {
        runnable.run();
    }

}
