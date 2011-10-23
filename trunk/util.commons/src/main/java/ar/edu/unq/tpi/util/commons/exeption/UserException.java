package ar.edu.unq.tpi.util.commons.exeption;

/**
 * This exception is thrown to indicate an error in the application. Examples of
 * errors can be a file open error, database error, or the such.
 * <p/>
 * This kind of exception are usually irrecoverable, and end up in an error
 * message to the user indicating an error in the system.
 * 
 */
public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserException(final String message) {
        super(message);
    }

    public UserException(final Throwable cause) {
        super(cause);
    }

    public UserException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
