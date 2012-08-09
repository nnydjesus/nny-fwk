package ar.edu.unq.tpi.base.unitofwork;

/**
 * This interface is provided to loose coupling between ContextSession
 * and HTTPSession (That allows us to have another non-web implementations
 * of the long session).
 *
 * @author Claudio
 */
public interface SessionHolder {

    void set(String key, Object value);

    <T> T get(String key);

    <T> T remove(String key);

    void invalidate();

}
