package ar.edu.unq.tpi.ui.swing.components.autocomplete;

public final class ErrorService {

    /**
     * The <tt>ErrorCallback</tt> instance that callbacks are sent to. We use
     * the <tt>ShellErrorCallback</tt> as the default in case no other callback
     * is set.
     */
    private static ErrorCallback _errorCallback = new ShellErrorCallback();

    /**
     * Private constructor to ensure this class cannot be instantiated.
     */
    private ErrorService() {
    }

    /**
     * Sets the <tt>ErrorCallback</tt> class to use.
     */
    public static void setErrorCallback(final ErrorCallback callback) {
        _errorCallback = callback;
    }

    /**
     * Gets the <tt>ErrorCallback</tt> currently in use.
     */
    public static ErrorCallback getErrorCallback() {
        return _errorCallback;
    }

    /**
     * Displays the error to the user.
     */
    public static void error(final Throwable problem) {
        _errorCallback.error(problem);
    }

    /**
     * Displays the error to the user with a specific detail information.
     */
    public static void error(final Throwable problem, final String detail) {
        _errorCallback.error(problem, detail);
    }

    /**
     * Helper class that simply outputs the stack trace to the shell.
     */
    private static class ShellErrorCallback implements ErrorCallback {

        /**
         * Implements the <tt>ErrorCallback</tt> interface. Simply prints out
         * the stack trace for the given <tt>Throwable</tt>.
         * 
         * @param t
         *            the <tt>Throwable</tt> to display
         */
        public void error(final Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t.getMessage());
        }

        // inherit doc comment
        public void error(final Throwable t, final String msg) {
            t.printStackTrace();
            System.out.println(msg);
            throw new RuntimeException(t.getMessage());
        }
    }
}