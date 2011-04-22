package ar.com.nny.base.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.utils.ReflectionUtils;

/**
 * This enumeration contains the possible strategies through which a given
 * field should be read / written for serialization.
 */
public enum Through {

    /**
     * The field will be accessed directly.
     */
    FIELD {
        public Object read(Object target, Field field) {
            field.setAccessible(true);
            return ReflectionUtils.readField(target, field);
        }

        public void write(Object target, Field field, Object value) {
            field.setAccessible(true);
            ReflectionUtils.writeField(target, field, value);
        }
    },

    /**
     * The field will be accessed through getter/setter pair. Getter and setter
     * should exists according to java bean specification
     */
    ACCESSOR {
        public Object read(Object target, Field field) {
            String fieldName = field.getName();
            String getterPrefix = "get";
            if ( Boolean.TYPE.equals(field.getType()) ) {
                getterPrefix = "is";
            }

            String getterName = getterPrefix + StringUtils.capitalize(fieldName);

            Method getter = ReflectionUtils.getPrivateMethodWithParametersQty(target.getClass(), getterName, 0);
            return ReflectionUtils.invoke(target, getter);
        }

        public void write(Object target, Field field, Object value) {
            String fieldName = field.getName();
            String setterName = "set" + StringUtils.capitalize(fieldName);

            Method setter = ReflectionUtils.getPrivateMethodWithParametersQty(target.getClass(), setterName, 1);
            ReflectionUtils.invoke(target, setter, value);
        }
    },

    /**
     * The field will be read directly but it won't be written.
     * If the caller attempts to overwrite the value of the field no
     * error will be raised (but no action will be taken).
     */
    READ_ONLY_FIELD {
        public Object read(Object target, Field field) {
            return Through.FIELD.read(target, field);
        }

        public void write(Object target, Field field, Object value) {
        }
    },

    /**
     * The field will be read through getter accessor (according to
     * java bean specification).
     * If the caller attempts to overwrite the value of the field no
     * error will be raised (but no action will be take).
     */
    READ_ONLY_ACCESSOR {
        public Object read(Object target, Field field) {
            return Through.ACCESSOR.read(target, field);
        }

        public void write(Object target, Field field, Object value) {
        }
    },

    /**
     * Field should not be written nor read.
     */
    TRANSIENT {
        public Object read(Object target, Field field) {
            throw new NonBusinessException("Attemping to read transient field" + field);
        }

        public void write(Object target, Field field, Object value) {
            throw new NonBusinessException("Attemping to write transient field " + field);
        }
    };


    /**
     * Reads the value of the field.
     */
    public abstract Object read(Object target, Field field);


    /**
     * Writes the value of the field.
     */
    public abstract void write(Object target, Field field, Object value);

}
