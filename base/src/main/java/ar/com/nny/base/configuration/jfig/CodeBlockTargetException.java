package ar.com.nny.base.configuration.jfig;

import ar.com.nny.base.exception.NonBusinessException;

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
