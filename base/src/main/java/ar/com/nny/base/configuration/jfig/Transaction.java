package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.*;

/**
 * Specify transaction type for service method.
 *
 * @author Claudio
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transaction {

    public TransactionType value() default TransactionType.REQUIRES;

}
