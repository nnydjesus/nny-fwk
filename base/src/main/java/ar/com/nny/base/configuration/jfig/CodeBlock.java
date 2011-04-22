package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.Annotation;


/**
 * A CodeBlock is a piece of code wrapped in an object, to be used as a predicate.
 *
 * @author Claudio
 */
public interface CodeBlock {

    /**
     * This method is where the block code should be.
     */
    public Object execute() throws Exception;


    /**
     * Reads metadata from codeblock
     */
    public <T extends Annotation> T getMetadata(Class<T> annotationClass);

    /**
     * Reads metadata from method class
     */
    public <T extends Annotation> T getClassMetadata(final Class<T> annotationClass);

}
