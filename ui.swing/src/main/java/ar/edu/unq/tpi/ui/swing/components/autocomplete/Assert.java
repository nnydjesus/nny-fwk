package ar.edu.unq.tpi.ui.swing.components.autocomplete;

public class Assert {

    /**
     * A silent assert. Checks a boolean condition and notifies ErrorService if
     * the error occurred, but does not throw an exception to propogate further.
     */
    public static void silent(final boolean ok, final String msg) {
        if (!ok) {
            // System.err.println("Assertion failed: "+msg);
            // Thread.dumpStack();
            RuntimeException re = new AssertFailure(msg);
            ErrorService.error(re);
        }
    }

    public static void silent(final boolean ok) {
        Assert.silent(ok, "");
    }

    public static void that(final boolean ok, final String msg) {
        if (!ok) {
            // System.err.println("Assertion failed: "+msg);
            // Thread.dumpStack();
            RuntimeException re = new AssertFailure(msg);
            throw re;
        }
    }

    public static void that(final boolean ok) {
        Assert.that(ok, "");
    }
}