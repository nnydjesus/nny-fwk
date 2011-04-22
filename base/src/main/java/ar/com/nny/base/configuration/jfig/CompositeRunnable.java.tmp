package ar.com.nny.base.configuration.jfig;

import java.util.List;

public class CompositeRunnable implements Runnable {

    private List<Runnable> runnables;


    public CompositeRunnable(List<Runnable> runnables) {
        this.runnables = runnables;
    }


    public void run() {
        for ( Runnable runnable : this.runnables ) {
            runnable.run();
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Composite Runnable [");
        for ( Runnable runnable : this.runnables ) {
            builder.append(runnable.toString());
            builder.append(" - ");
        }
        builder.append("]");
        return builder.toString();
    }
}