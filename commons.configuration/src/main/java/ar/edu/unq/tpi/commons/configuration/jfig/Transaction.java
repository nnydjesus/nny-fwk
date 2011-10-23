package ar.edu.unq.tpi.commons.configuration.jfig;

import java.lang.annotation.*;

/**
 * Specify transaction type for service method.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transaction {

    public TransactionType value() default TransactionType.REQUIRES;

}
