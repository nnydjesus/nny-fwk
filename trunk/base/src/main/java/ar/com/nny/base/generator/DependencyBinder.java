package ar.com.nny.base.generator;

import java.lang.reflect.Field;
import java.util.List;

import ar.com.nny.base.configuration.ApplicationRegistryReader;
import ar.com.nny.base.persistence.injector.Injector;
import ar.com.nny.base.utils.ReflectionUtils;


public class DependencyBinder {

    /**
     * Applies the Injectors over a the fields of the given object. The result
     * of this process is an injector-populated bean.
     * <p/>
     * References to services and DAOS are currently bean injected but more injectors
     * could be added later on.
     */
    public static void autoInject(Object object) {
        if ( object == null ) {
            return;
        }

        Class clase = object.getClass();
        List<Injector> allInjectors = ApplicationRegistryReader.getInstance().getAllInjectors();

        for ( Field field : ReflectionUtils.getAllFields(clase) ) {
            for ( Injector injector : allInjectors ) {
                injector.inject(field, object);
            }
        }
    }


}
