package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to demarcate which classes work as services
 * in the application.
 * All services must be annotated. The Service metadata is currently
 * being read by the dependency injection framework and the flex
 * communication framework.
 *
 * @author Claudio
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {

	/**
	 * If true and [environment].[deny.remote.services], the invocation to the annotated service will throw exception.
	 */
	boolean denyable() default false;
}
