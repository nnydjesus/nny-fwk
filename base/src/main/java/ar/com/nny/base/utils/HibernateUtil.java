package ar.com.nny.base.utils;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import ar.com.nny.base.configuration.ApplicationRegistryReader;
import ar.com.nny.base.persistence.PersistenceManager;
import ar.com.nny.base.persistence.PersistentObject;


/**
 * Clase de utilidad para obtener la sesion de hibernate.
 *
 * @author documentacion hibernate
 */
public class HibernateUtil {

    private static  SessionFactory sessionFactory;
    private static Log LOGGER = LogFactory.getLog(HibernateUtil.class);
    private static AnnotationConfiguration configuration;

    
    static{
        // Si no ponemos fichero, intenta cargar "/hibernate.cfg.xml" en el
        // raiz
        configuration = new AnnotationConfiguration().configure(
                new File("hibernate.cfg.xml"));
        final Collection<Class<? extends PersistentObject>> persistentClasses = ApplicationRegistryReader.getInstance().getAllPersistentClasses();
        for (Class<? extends PersistentObject> class1 : persistentClasses) {
        	configuration.addAnnotatedClass(class1);
		}
    	
    }
    
//
//    private static void generatefactory() {
//        try {
////	       
//	        sessionFactory = configuration.buildSessionFactory();
//	        BasicConfigurator.configure();
//	        Logger.getAnonymousLogger().setLevel(Level.INFO);
////        	sessionFactory =   PersistenceManager.getInstance().getDefaultSessionFactory();
//        } catch (Throwable ex) {
//            // Log exception!
//        	LOGGER.error("No pudo leer el hibernate.cfg.xml ", ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//    
//    public static AnnotationConfiguration getConfiguration(){
////        if( sessionFactory == null )
////        	generatefactory();
//    	return configuration;
//    }
//
//    public static Session getSession() throws HibernateException {
//        if( sessionFactory == null )
//        	generatefactory();
//        	
//        return  sessionFactory.openSession();
//    }
    
    public static void main(String[] args) {
//		HibernateUtil.getSession().close();
//		PersistenceManager.getInstance().getDefaultSessionFactory();
	}

//	public static void getConfigurationGenerator() {
//		 Properties properties = new Properties();
//	     properties.setProperty("hbm2ddl.auto", "create");
//	     configuration.addProperties(properties);
//	}
}

