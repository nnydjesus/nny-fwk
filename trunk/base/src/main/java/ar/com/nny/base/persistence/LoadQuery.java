package ar.com.nny.base.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a query to be used by the DataSevice.getByIds() service to load the entities.
 * The service will replace the <b>replaceToken</b> in the query with the '(?, ?, ?...)' parameters.
 * 
 * If used isnide <b>LoadQueries</b>, one should leave id blank, but the others should define their ids. 
 * @author Rodro
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoadQuery {
	public String id() default "";

	public String query();

	public String replaceToken() default "$IDS$";
}
