package ar.edu.unq.tpi.util.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.lang.StringUtils;

/**
 * @author Ronny
 * 
 */
@SuppressWarnings("unchecked")
public class ReflectionUtils {

    /**
     * Instantiate class and handles reflection exceptions
     */
    public static <T> T instanciate(final Class<T> clazz) {
        return instanciate(clazz, new Object[0]);
    }

    /**
     * Instantiate class and handles reflection exceptions
     */
    public static <T> T instanciate(final Class<T> clazz, final Object... params) {
        try {
            if (params != null && params.length > 0) {
                final Class[] _types = new Class[params.length];

                for (int _i = 0; _i < _types.length; _i++) {
                    _types[_i] = params[_i].getClass();
                }

                return clazz.getConstructor(_types).newInstance(params);
            } else
                return clazz.newInstance();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T instanciatePrivate(final Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            return constructor.newInstance(new Object[0]);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Class for name, instead of ClassNotFoundException returns null when class
     * cannot be loaded.
     */
    public static Class classForName(final String className) {
        try {
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            return null;
        }

    }


    /**
     * Returns every declared field on the specified class.
     */
    public static List<Field> getAllFields(final Class clase) {
        return getAllFields(clase, null);
    }
    
	public static List<Field> getAllFields(Object object){
		
		ArrayList<Field> fields = new ArrayList<Field>();
		Class<? extends Object> cls = object.getClass();
		
		while (cls != null) {
			CollectionUtils.addAll(fields, cls.getDeclaredFields());
			cls = cls.getSuperclass();
		}
		
		return fields;
	}

    /**
     * Returns every declared field on the specified class.
     */
    public static List<Field> getAllFields(final Class clase, final Predicate<Field> fieldPredicate) {
        final List<Field> result = new ArrayList<Field>();

        if (clase != null) {
            for (final Field each : clase.getDeclaredFields()) {
                if (fieldPredicate == null || fieldPredicate.evaluate(each)) {
                    result.add(each);
                }
            }

            for (final Field each : getAllFields(clase.getSuperclass())) {
                if (fieldPredicate == null || fieldPredicate.evaluate(each)) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    /**
     * Reads a binded generic from a field
     */
    public static Class readGeneric(final Field field, final int index) {
        final Type genericType = field.getGenericType();
        return readGeneric((ParameterizedType) genericType, index);
    }

    public static Class[] readGenerics(final Field field) {
        final Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            final Type[] readGenerics = readGenerics((ParameterizedType) genericType);
            final Class[] ret = new Class[readGenerics.length];
            for (int i = 0; i < readGenerics.length; i++) {
                final Type type = readGenerics[i];
                ret[i] = (Class) type;
            }
            return ret;
        } else
            return new Class[] {};
    }

    public static Type[] readGenerics(final ParameterizedType genericType) {
        return genericType.getActualTypeArguments();
    }

    public static Class readGeneric(final ParameterizedType genericType, final int index) {
        final Type actualType = readGenerics(genericType)[index];
        return (Class) actualType;
    }

    /**
     * Invokes a method and handle reflection exceptions. It uses variable
     * parameter syntax.
     */
    public static Object invoke(final Object object, final Method method, final Object... params) {
        return invokeNotDynarg(object, method, params);
    }

    public static Object invokeNotDynarg(final Object object, final Method method, final Object[] params) {
        try {
            return method.invoke(object, params);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException("Exception during method invocation", e);
        } catch (final Exception e) {
            throw new RuntimeException("Cannot invoke method", e);
        }
    }

    public static Method getPrivateMethod(final Class declaringClass, final String methodName,
            final Class... propertyType) {
        if (declaringClass == null)
            return null;

        final Method[] declaredMethods = declaringClass.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), propertyType))
                return method;
        }
        return getPrivateMethod(declaringClass.getSuperclass(), methodName, propertyType);
    }

    public static Method getPrivateMethodWithParametersQty(final Class declaringClass, final String methodName,
            final int qty) {
        if (declaringClass == null)
            return null;

        final Method[] declaredMethods = declaringClass.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            if (method.getName().equals(methodName) && method.getParameterTypes().length == qty)
                return method;
        }
        return getPrivateMethodWithParametersQty(declaringClass.getSuperclass(), methodName, qty);
    }

    public static Field getField(final Class clazz, final String string) {
        try {
            return clazz.getDeclaredField(string);
        } catch (final Exception e) {
            throw new RuntimeException("Cannot recover field " + string + " from class " + clazz, e);
        }
    }

    public static Object readField(final Object target, final Field field) {
        try {
        	field.setAccessible(true);
            return field.get(target);
        } catch (final Exception e) {
            throw new RuntimeException("Cannot get field value", e);
        }
    }

    public static void writeField(final Object target, final Field field, final Object value) {
        try {
            field.set(target, value);
        } catch (final Exception e) {
            throw new RuntimeException("Cannot set field value", e);
        }
    }

    public static Field getFieldFromAll(final Class clazz, final String propertyName) {
        final List<Field> all = getAllFields(clazz);
        for (final Field field : all) {
            if (field.getName().equals(propertyName))
                return field;
        }
        return null;
    }

    public static <T extends Annotation> Collection<T> getAnnotationInherited(final Class targetClass,
            final Class<T> annotationClass) {
        final Collection<T> result = new ArrayList<T>();

        final T annotation = (T) targetClass.getAnnotation(annotationClass);
        if (annotation != null) {
            result.add(annotation);
        }

        final Class superClass = targetClass.getSuperclass();
        if (superClass != null) {
            result.addAll(getAnnotationInherited(superClass, annotationClass));
        }
        return result;
    }

    public static Class getNullSafeClass(final Object object) {
        return object == null ? null : object.getClass();
    }

    public static Object readField(final Object object, final String property) {
    	return readField(object, getField(object.getClass(), property));
    }
    
    
    public static void invokeSetter(final Object object, final String property, final Object value) {
        if (object == null)
            return;

        final Method[] declaredMethods = object.getClass().getDeclaredMethods();

        try {

            for (final Method method : declaredMethods) {
                if (method.getName().equals("set" + StringUtils.capitalize(property))) {
                    ReflectionUtils.invoke(object, method, value);
                    return;
                }
            }

        } catch (final Exception ex) {
            throw new RuntimeException("Se ha producido un error invocando " + "set"
                    + StringUtils.capitalize(property), ex);
        }

        throw new RuntimeException("No se ha encontrado el setter " + "set" + StringUtils.capitalize(property));

    }

    @SuppressWarnings("rawtypes")
    public static Object invokeMethod(final Class model, final String actionName) {
        try {
            return findMethod(model, actionName, new Class[] {}).invoke(model, new Object[] {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(final Object model, final String actionName) {
        try {
            return findMethod(model.getClass(), actionName, new Class[] {}).invoke(model, new Object[] {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void invokeMethod(final Object model, final String actionName, final Class[] c, final Object... args) {
        try {
        	findMethod(model.getClass(), actionName, c).invoke(model, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void invokeMethod(final Class model, final String actionName, final Class[] c, final Object... args) {
        try {
        	findMethod(model, actionName, c).invoke(model, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void invokeMethod(Object object, String method, Object... args) {
        Class[] clazz = new Class[args.length] ;
        int i = 0;
        for (Object object2 : args) {
        	if(object2 == null){
        		clazz[i] = Object.class;
        	}else{
        		clazz[i] = object2.getClass();
        	}
            i++;
        }
        invokeMethod(object, method, clazz, args);
    }
    
    public static Method findMethod(Class<?> clazz, String name,
            Class<?>[] paramTypes) {
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType
                    .getMethods() : searchType.getDeclaredMethods());
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (name.equals(method.getName())
                        && (paramTypes.length == method
                                .getParameterTypes().length)) {

                    boolean found = true;
                    Class<?>[] methodParameterTypes = method
                            .getParameterTypes();

                    for (int j = 0; j < methodParameterTypes.length; j++) {
                        found = methodParameterTypes[j]
                                .isAssignableFrom(paramTypes[j]);
                        if (!found)
                            break;
                    }

                    if (found){
                    	method.setAccessible(true);
                    	return method;
                    }
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

}
