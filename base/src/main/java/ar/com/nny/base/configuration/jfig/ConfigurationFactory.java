package ar.com.nny.base.configuration.jfig;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.engine.FilterDefinition;

import ar.com.nny.base.configuration.ApplicationRegistryReader;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.persistence.SoftDeleteManager;
import edu.emory.mathcs.backport.java.util.Collections;

public class ConfigurationFactory {

    private static Log LOGGER = LogFactory.getLog(ConfigurationFactory.class);


    public AnnotationConfiguration buildConfiguration(final String name, final Properties properties) {
    	LOGGER.debug("Building hibernate configuration...");
    	
        final AnnotationConfiguration configuration = new AnnotationConfiguration();
        configuration.setNamingStrategy(buildNamingStrategy(properties));

        properties.setProperty("hibernate.search.default.indexBase", BaseConfiguration.getWorkingDirectoryFile().getAbsolutePath() + File.separator + "lucene-db");
        configuration.setProperties(properties);

        final FilterDefinition filter = new FilterDefinition(SoftDeleteManager.SOFT_DELETE_FILTER, null, Collections.singletonMap(SoftDeleteManager.DELETED_FLAG, Hibernate.BOOLEAN));
        configuration.addFilterDefinition(filter);

//          final Collection<Class<? extends PersistentObject>> persistentClasses = ApplicationRegistryReader.getInstance().getAllPersistentClasses();
//          for (Class<? extends PersistentObject> class1 : persistentClasses) {
//              configuration.addAnnotatedClass(class1);
//        }
          
        return configuration;
    }


    private NamingStrategy buildNamingStrategy(final Properties properties) {
    	LOGGER.debug("Building naming strategy...");

    	NamingStrategy strategy = new ImprovedNamingStrategy();
        final String value = properties.getProperty("hibernate.namingStrategy");

        try {
            if (value != null && value.trim().length() > 0) {
                strategy = (NamingStrategy) Class.forName(value.trim()).newInstance();
            }
        }
        catch (final Exception e) {
            LOGGER.warn("Invalid Hibernate Naming Strategy: [" + value + "]: " + e.getMessage(), e);
        }
        return strategy;
    }

}
