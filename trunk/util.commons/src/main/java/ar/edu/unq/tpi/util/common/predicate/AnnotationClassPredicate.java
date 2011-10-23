package ar.edu.unq.tpi.util.common.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections15.Predicate;

/**
 * Helper for common usage.
 *
 */
public final class AnnotationClassPredicate implements Predicate<Class> {

    private final List<Class> annotations = new ArrayList<Class>();


    public AnnotationClassPredicate(final Class... _annotations) {
        annotations.addAll(Arrays.asList(_annotations));
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean evaluate(final Class _class) {
        // no se puede usar CollectionUtils.containsAny porque una annotation no es equals a la otra :( (una es proxied)
        for ( final Class _wantedAnnotation : annotations ) {
            if ( _class.isAnnotationPresent(_wantedAnnotation) ) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
    	return this.getClass().getSimpleName() + ": [" + annotations.toString() + "]";
    }
}
