package ar.com.nny.base.configuration.jfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.exception.NonBusinessException;

public class AsynchronicRunner {

    private static final Log LOGGER = LogFactory.getLog(AsynchronicRunner.class);

    private List<Runnable> delayedRunnables = new ArrayList();
    private boolean runningDelayed = false;


    public void delay(Runnable runnable) {
        LOGGER.debug("Delaying invocation of asynchronic " + runnable);
        if ( runningDelayed ) {
            throw new NonBusinessException("Delayed service cannot call to another delayed service");
        }
        this.delayedRunnables.add(runnable);
    }


    public void runDelayedOnes() {
        LOGGER.debug("Transaction commited - trying to run delayed asynchronic processes. Running: " + runningDelayed + " to run: " + this.delayedRunnables.size());
        if ( !runningDelayed && !this.delayedRunnables.isEmpty() ) {
            this.runningDelayed = true;
            this.runAsynchronic(new CommandServiceRunnable(new CompositeRunnable(this.delayedRunnables)));
        }
    }


    /**
     * Runs a runnable in a new thread immediately.
     */
    public void runAsynchronic(Runnable runnable) {
        LOGGER.debug("Running asynchronic " + runnable);
        Thread thread = new Thread(runnable);
        thread.setDaemon(false);
        thread.start();
    }

}
