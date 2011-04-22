package ar.com.nny.base.persistence;

import java.lang.annotation.*;

/**
 * This annotation tells the to add cache over find(String,String) and
 * findUnique(String,String) methods if and only if the field name equals
 * to the current field.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldQueryCache {

    public String region() default "default";

}
