package ar.com.nny.base.generator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.nny.base.generator.InitialDataGenerator;

/**
 * Declare dependencies between data generators.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataGeneratorDepends {

    public Class<? extends InitialDataGenerator>[] value();
}
