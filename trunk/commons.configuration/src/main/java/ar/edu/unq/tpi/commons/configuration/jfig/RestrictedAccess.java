package ar.edu.unq.tpi.commons.configuration.jfig;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RestrictedAccess {

    public String[] roles() default {};
}
