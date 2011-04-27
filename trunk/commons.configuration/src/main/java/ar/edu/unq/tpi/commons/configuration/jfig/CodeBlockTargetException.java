package ar.edu.unq.tpi.commons.configuration.jfig;

import ar.edu.unq.tpi.util.commons.exeption.NonBusinessException;

/**
 * This exception is similar to reflections InvocationTargetException
 * 
 */
public class CodeBlockTargetException extends NonBusinessException {
    private static final long serialVersionUID = 1L;

    public CodeBlockTargetException(final Throwable cause) {
        super(cause);
    }

}
