package ar.com.nny.base.generator.annotations;

import java.lang.annotation.*;

/**
 * This annotation is used to demarcate which method of the InitialDataGenerator
 * will be used to generate data.
 * <p/>
 * Generators method must receive no parameters.
 *
 * @author Claudio
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataGeneratorMethod {

    /**
     * Methods annotated with dummyData flag set to true
     * will be invoked only for generated test and development data,
     * not to create productive initial data.
     */
    public boolean dummyData() default true;


    /**
     * Methods with lower values run first.
     */
    public int order() default Integer.MAX_VALUE;


    /**
     * Methods that has a cvsFile associated will be invoked once per
     * csv line.
     */
    public String csvFile() default "";

}
