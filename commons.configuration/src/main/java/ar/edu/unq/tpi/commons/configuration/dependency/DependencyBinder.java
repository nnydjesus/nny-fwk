package ar.edu.unq.tpi.commons.configuration.dependency;

import java.lang.reflect.Field;
import java.util.List;

import ar.edu.unq.tpi.commons.configuration.common.ApplicationRegistryReader;
import ar.edu.unq.tpi.commons.configuration.injector.Injector;
import ar.edu.unq.tpi.util.common.ReflectionUtils;


public class DependencyBinder {

    /**
     * Applies the Injectors over a the fields of the given object. The result
     * of this process is an injector-populated bean.
     * <p/>
     * References to services and Repository are currently bean injected but more injectors
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
