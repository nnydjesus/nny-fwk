package ar.com.nny.base.persistence.injector;

import java.lang.reflect.Field;

/**
 * This object has the responsability of injecting a value
 * into a field.
 *
 * @author Claudio
 */
public abstract class Injector {

    /**
     * Template Method.
     * <p/>
     * Injects the value of the given field into the object
     * if it is required to do it so.
     */
    public final void inject(Field field, Object object) {
        if ( this.applies(field) ) {
            this.doInject(field, object);
        }
    }


    /**
     * Tells if the injector should be used to initialize
     * the value of the given field.
     */
    public abstract boolean applies(Field field);


    /**
     * Injects the value of the given field into the object
     */
    protected abstract void doInject(Field field, Object object);

}
