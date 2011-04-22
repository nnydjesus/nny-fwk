package ar.com.nny.base.configuration.jfig;



public class CommandServiceRunnable implements Runnable {

    private final Runnable runnable;


    public CommandServiceRunnable(Runnable runnable) {
        this.runnable = runnable;
    }


    public void run() {
        ServiceLocator.locate(CommandService.class).runRunnable(runnable);
    }

}
