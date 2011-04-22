package ar.com.nny.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


//TODO: Mejorar este modelo.
public class TypedClass {

    private Class clazz;
    private Map<Integer, TypedClass> generics = new HashMap<Integer, TypedClass>();
    private Map<Class, Object> annotations = new HashMap<Class, Object>();


    public TypedClass(final Field field) {
    	this.clazz = field.getType();
    	
    	final Class[] readGenerics = ReflectionUtils.readGenerics(field);
    	for (int i = 0; i < readGenerics.length; i++) {
			final Class clazz = readGenerics[i];
    		this.setGeneric(i, new TypedClass(clazz));
		}
    	
    	for (final Annotation annotation : field.getAnnotations()) {
    		addAnnotation(annotation);
		}
    }
    
    public TypedClass(final Class clazz) {
        this.clazz = clazz;
    }


    public TypedClass(final Class clazz, final Class[] generics) {
        this.clazz = clazz;
        for ( int i = 0; i < generics.length; i++ ) {
            final Class generic = generics[i];
            this.setGeneric(i, new TypedClass(generic));
        }
    }


    public Class rawClass() {
        return this.clazz;
    }


    public TypedClass getGeneric(final int i) {
        return generics.get(i);
    }


    public void setGeneric(final int i, final TypedClass generic) {
        this.generics.put(i, generic);
    }


    public void addAnnotation(final Annotation annotationInstance) {
        annotations.put(annotationInstance.annotationType(), annotationInstance);
    }


    public boolean hasAnnotationFor(final Class annotationType) {
        return annotations.containsKey(annotationType);
    }


    public <T extends Annotation> T getAnnotationFor(final Class<T> annotationType) {
        return (T) annotations.get(annotationType);
    }


    @Override
	public String toString() {
        return "TypedClass{" + this.rawClass().toString() + "}";
    }
}
