package ar.com.nny.base.generator;

import org.apache.commons.collections15.Predicate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper for common usage.
 *
 */
public final class AnnotationMethodPredicate implements Predicate<Method> {

    private final List<Class> annotations = new ArrayList<Class>();


    public AnnotationMethodPredicate(final Class... _annotations) {
        annotations.addAll(Arrays.asList(_annotations));
    }


    public boolean evaluate(final Method _method) {
        // no se puede usar CollectionUtils.containsAny porque una annotation no es equals a la otra :( (una es proxied)
        for ( final Class _wantedAnnotation : annotations ) {
            if ( _method.isAnnotationPresent(_wantedAnnotation) ) {
                return true;
            }
        }
        return false;
    }
}
