package ar.com.nny.base.configuration.jfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.persistence.injector.Injector;
import ar.com.nny.base.utils.ReflectionUtils;

public class ConfigurationInjector extends Injector {

    private static Log LOGGER = LogFactory.getLog(ConfigurationInjector.class);

    private Map<String, Object> objects = new HashMap<String, Object>();
    
	@Override
	public boolean applies(final Field field) {
		return field.isAnnotationPresent(InjectFromConfig.class);
	}

	@Override
	protected void doInject(final Field field, final Object object) {
		final InjectFromConfig ifc = field.getAnnotation(InjectFromConfig.class);
        field.setAccessible(true);
		final Class<?> fieldType = field.getType();

		if(fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getInt(ifc.section(), ifc.key()));
		} else if(fieldType.equals(long.class) || fieldType.equals(Long.class)) {
			ReflectionUtils.writeField(object, field, (long)JFigConfiguration.getInstance().getInt(ifc.section(), ifc.key()));
		} else if(fieldType.equals(float.class) || fieldType.equals(Float.class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getFloat(ifc.section(), ifc.key()));
		} else if(fieldType.equals(double.class) || fieldType.equals(Double.class)) {
			ReflectionUtils.writeField(object, field, (double)JFigConfiguration.getInstance().getFloat(ifc.section(), ifc.key()));
		} else if(fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getBoolean(ifc.section(), ifc.key()));
		} else if(fieldType.equals(String.class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getString(ifc.section(), ifc.key()));
		} else if(fieldType.equals(String[].class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getArray(ifc.section(), ifc.key()));
		} else if(fieldType.equals(Properties.class)) {
			ReflectionUtils.writeField(object, field, JFigConfiguration.getInstance().getSectionAsProperties(ifc.section()));
		} else {
			final String className = JFigConfiguration.getInstance().getString(ifc.section(), ifc.key());
			LOGGER.debug("Instantiating config object of class " + className + " for section " + ifc.section() + " key " + ifc.key());
	    	if(!objects.containsKey(ifc.section() + "." + ifc.key())) {
	    		try {
	    			objects.put(ifc.section() + "." + ifc.key(), Class.forName(className).newInstance());
	    		} catch (final InstantiationException e) {
	    			throw new NonBusinessException(e);
	    		} catch (final IllegalAccessException e) {
	    			throw new NonBusinessException(e);
	    		} catch (final ClassNotFoundException e) {
	    			throw new NonBusinessException(e);
	    		}
	    	}
	    	ReflectionUtils.writeField(object, field, objects.get(ifc.section() + "." + ifc.key()));
		}
	}
    
}
