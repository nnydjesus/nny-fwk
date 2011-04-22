package ar.com.nny.base.configuration;

import org.apache.commons.collections15.Predicate;

/**
 * Helper for common usage.
 *
 * @author Rodrigo Merino
 */
public final class SuperclassPredicate implements Predicate<Class> {

    private final Class superclass;


    public SuperclassPredicate(final Class _superclass) {
    	superclass = _superclass;
    }


    public boolean evaluate(final Class _class) {
    	Class current = _class;
    	
    	while(current.getSuperclass() != null) {
    		if(current.getSuperclass().equals(superclass)) {
    			return true;
    		}
    		current = current.getSuperclass();
    	}
    	return false;
//        return _class.getSuperclass() != null && _class.getSuperclass().equals(superclass);
    }
}
