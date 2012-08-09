package ar.edu.unq.tpi.base.persistence.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.edu.unq.tpi.base.repository.GenericRepository;

/**
 * This annotation is used to demarcate which Repository class should be
 * used for a persistent class. If not specified GenericRepository (properly
 * parametrized) is used instead.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Repository {

    public Class<? extends GenericRepository> value();
}
