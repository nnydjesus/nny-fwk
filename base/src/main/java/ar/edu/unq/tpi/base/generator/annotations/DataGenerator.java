package ar.edu.unq.tpi.base.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.edu.unq.tpi.base.generator.InitialDataGenerator;

/**
 * This annotation is used to demarcate which java class
 * should be used as the initial data generator associated
 * to the current persistent class
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataGenerator {

    public Class<? extends InitialDataGenerator> value();
    public int order() default Integer.MAX_VALUE;
}
