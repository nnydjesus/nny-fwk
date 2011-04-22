package ar.com.nny.base.persistence.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark custom types with the converter It should
 * have the converter class as parameter.
 * <p/>
 * For example @Converter(MyCustomTypeConverter.class)
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Converter {

    public Class value();
}
