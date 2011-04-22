package ar.com.nny.base.generator.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Define que el padre le genere in id automaticamente
 */
@Target({TYPE}) @Retention(RUNTIME)
public @interface GeneratedId {

}
