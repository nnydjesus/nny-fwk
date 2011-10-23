package ar.edu.unq.tpi.ui.swing.components.autocomplete;

public class AssertFailure extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AssertFailure(final String message) {
        super(message);
    }

    public AssertFailure(final Throwable cause) {
        super(cause);
    }

    public AssertFailure(final String message, final Throwable cause) {
        super(message, cause);
    }
}