package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotated attribute is injected with the corresponding section and key read from the configuration file. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectFromConfig {
	
	public String section();
	public String key() default "";
	
}
