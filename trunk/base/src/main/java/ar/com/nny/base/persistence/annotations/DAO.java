package ar.com.nny.base.persistence.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.nny.base.dao.GenericDao;

/**
 * This annotation is used to demarcate which DAO class should be
 * used for a persistent class. If not specified GenericDAO (properly
 * parametrized) is used instead.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DAO {

    public Class<? extends GenericDao> value();
}
