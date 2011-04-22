package ar.com.nny.base.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.nny.base.persistence.annotations.OrderType;

/**
 * Indicates that the current field should should be used to naturally order
 * the given field when getAll operations are performed.
 *
 * @author Claudio
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface NaturalOrder {

    public OrderType orderType() default OrderType.ASC;
}
