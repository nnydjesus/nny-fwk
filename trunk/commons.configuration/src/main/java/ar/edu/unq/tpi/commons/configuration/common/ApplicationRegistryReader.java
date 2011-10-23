package ar.edu.unq.tpi.commons.configuration.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.edu.unq.tpi.commons.configuration.injector.Injector;
import ar.edu.unq.tpi.commons.configuration.jfig.ConfigurationInjector;
import ar.edu.unq.tpi.util.common.TypeInferencer;

/**
 * Read the moduleDescriptors for each module configured in the application and
 * returns information about all of them.
 *
 */
public class ApplicationRegistryReader {

    public static final String STANDARD_BASE_PACKAGE = "ar.edu.unq.tpi";

    private static ApplicationRegistryReader INSTANCE;

    private TypeInferencer inferencer ;;


    public static ApplicationRegistryReader getInstance() {
        if ( INSTANCE == null ) {
//            INSTANCE = (ApplicationRegistryReader) Enhancer.create(ApplicationRegistryReader.class, new CachedMethodInterceptor());
        	INSTANCE = new ApplicationRegistryReader();
        }
        return INSTANCE;
    }
    
    public void buildTypeInferencer(){
        setInferencer(new TypeInferencer());
    }


    public static String encodePackage(final String packageName) {
        return packageName.replaceAll("\\.", "\\.");
    }

    public List<Injector> getAllInjectors() {
        final List<Injector> result = new ArrayList();
        result.add(new ConfigurationInjector());
        return result;
    }

	public void setInferencer(TypeInferencer inferencer) {
		this.inferencer = inferencer;
	}

	public TypeInferencer getInferencer() {
		return inferencer;
	}


}
