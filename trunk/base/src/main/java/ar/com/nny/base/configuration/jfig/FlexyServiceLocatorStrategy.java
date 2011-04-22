package ar.com.nny.base.configuration.jfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.exception.NonBusinessException;
import ar.com.nny.base.generator.DependencyBinder;

public class FlexyServiceLocatorStrategy implements ServiceLocatorStrategy {

    private static Log LOGGER = LogFactory.getLog(ServiceLocatorStrategy.class);

    private static final ServiceCallbackHandler CALLBACK_HANDLER = new ServiceCallbackHandler();

    private Map servicesCacheMap = new HashMap();
    private Map currentlyBuildingMap = new HashMap();


    public <T> T locate(final Class<T> serviceClass, final boolean searchAmongNotFinishedOnes) {
        if ( serviceClass == null ) {
            throw new NonBusinessException("Service class must be specified");
        }

        if ( !servicesCacheMap.containsKey(serviceClass) ) {
            if ( searchAmongNotFinishedOnes && currentlyBuildingMap.containsKey(serviceClass) ) {
                return (T) currentlyBuildingMap.get(serviceClass);
            }

            LOGGER.debug("Service implementation not found in cache. Creating service of class: " + serviceClass);
            servicesCacheMap.put(serviceClass, instanciateService(serviceClass));
        }
        return (T) servicesCacheMap.get(serviceClass);
    }


    /**
     * Creates a new instance of serviceClass and add interception aspects to it.
     */
    private <T> T instanciateService(final Class<T> serviceClass) {
        try {
           // validateService(serviceClass);
            final T service = (T) Enhancer.create(serviceClass, CALLBACK_HANDLER);

            //This map is used to avoid stack overflow on certain circumstances (Dependency Binder).
            currentlyBuildingMap.put(serviceClass, service);
            DependencyBinder.autoInject(service);
            currentlyBuildingMap.remove(serviceClass);

            return service;
        }
        catch ( final Exception e ) {
            throw new NonBusinessException("Cannot instanciate service of class " + serviceClass.getName(), e);
        }
    }


    private void validateService(final Class serviceClass) {
        final Method[] methods = serviceClass.getMethods();

        final Map<String, Collection<Integer>> methodSignatures = new HashMap();
        for ( final Method method : methods ) {
            final String name = method.getName();
            final Integer parameterQty = method.getParameterTypes().length;

            if ( methodSignatures.containsKey(name) ) {
                final Collection<Integer> parametersQtyCollection = methodSignatures.get(name);
                if ( parametersQtyCollection.contains(parameterQty) ) {
                    throw new NonBusinessException("Cannot user parameter type overload in service methods. Service [" + serviceClass.getName() + "] method [" + name + "]");
                }
                parametersQtyCollection.add(parameterQty);
            }
            else {
                final Collection<Integer> col = new ArrayList();
                col.add(parameterQty);
                methodSignatures.put(name, col);
            }
        }
    }


}
