package ar.edu.unq.tpi.base.configuration;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.annotations.Indexed;

import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.commons.configuration.common.ApplicationRegistryReader;
import ar.edu.unq.tpi.commons.configuration.jfig.FrameworkConfiguration;
import ar.edu.unq.tpi.util.common.TypeInferencer;
import ar.edu.unq.tpi.util.common.predicate.AnnotationClassPredicate;

/**
 * Read the moduleDescriptors for each module configured in the application and
 * returns information about all of them.
 *
 */
public class ApplicationPersistentRegistryReader  extends ApplicationRegistryReader{

    private static final Log LOGGER = LogFactory.getLog(ApplicationPersistentRegistryReader.class);

    private static ApplicationPersistentRegistryReader INSTANCE;


    public static ApplicationPersistentRegistryReader getInstance() {
        if ( INSTANCE == null ) {
        	INSTANCE = new ApplicationPersistentRegistryReader();
        }
        return INSTANCE;
    }
    
    public void buildTypeInferencer(){
        setInferencer(new TypeInferencer());
    }




    /**
     * Return all module descriptor instances.
     * <p/>
     * The application search for each module ModuleDescriptor class. ModuleDescriptor
     * classes should be named according this convention:
     * <p/>
     * [configuration:packagePrefix].[moduleName].ModuleDescriptor
     * <p/>
     * The system will ignore those modules whose ModuleDescriptor classes couldn't be
     * found.
     */
    public Set<Class<? extends PersistentObject>> getAllPersistentClasses() {
        final Set<Class<? extends PersistentObject>> result = new LinkedHashSet();
        

//        for ( final AbstractModuleDescriptor descriptor : getAllModuleDescritors() ) {
//            descriptor.registerPersistentClasses(result);
//        }
        loadPersitentBeans(result, STANDARD_BASE_PACKAGE);
         String specialPackage = FrameworkConfiguration.getPackagePrefix();

        if(specialPackage != null)
        	loadPersitentBeans(result,	specialPackage);

//        final List<String> modules = FlexyConfiguration.getModules();
//        final String packagePrefix = FlexyConfiguration.getPackagePrefix();

//        for (String moduleName : modules) {
//        	if ( !(packagePrefix + "." + moduleName).equals(STANDARD_BASE_PACKAGE) ) {
//        		loadPersitentBeans(result, packagePrefix + "." + moduleName);
//        	}
//		}

        return result;
    }


    public void loadPersitentBeans(final Set<Class<? extends PersistentObject>> result, final String packageName) {
        final AnnotationClassPredicate persistenceFilter = new AnnotationClassPredicate(MappedSuperclass.class, Entity.class);
        for ( final Class _class : getInferencer().getClassesFor(this.encodePackage(packageName) + ".*bean.*", persistenceFilter) ) {
            result.add(_class);
        }
    }


    /**
     * Returns a list of all scanned and registered persistence classes that are also lucene classes.
//     */
    public Collection<Class<? extends PersistentObject>> getAllLuceneIndexedClasses() {
        return CollectionUtils.select(this.getAllPersistentClasses(), new AnnotationClassPredicate(Indexed.class));
    }
}
