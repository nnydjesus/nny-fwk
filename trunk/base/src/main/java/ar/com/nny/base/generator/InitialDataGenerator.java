package ar.com.nny.base.generator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.nny.base.generator.annotations.DataGeneratorDepends;
import ar.com.nny.base.generator.annotations.DataGeneratorMethod;
import ar.com.nny.base.persistence.PersistenceManager;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.utils.ReflectionUtils;

/**
 * Base class for every DataGenerator.
 * <p/>
 * DataGenerators must extend this class and should declare one or more methods
 * with @DataGeneratorMethod annotation.
 *
 */
public abstract class InitialDataGenerator<P extends PersistentObject> implements DependecyAware {

    private static Log LOGGER = LogFactory.getLog(InitialDataGenerator.class);


    protected InitialDataGenerator() {
        DependencyBinder.autoInject(this);
    }


    /**
     * Runs every @DataGeneratorMethod.
     */
    public final void run(boolean dummyMode) {
        LOGGER.debug("Running generator [" + this.getClass().getName() + "]");

        TreeSet<Method> orderedMethods = new TreeSet<Method>(new Comparator<Method>() {
            public int compare(Method method1, Method method2) {
                int order1 = method1.getAnnotation(DataGeneratorMethod.class).order();
                int order2 = method2.getAnnotation(DataGeneratorMethod.class).order();
                return order1 >= order2 ? 1 : -1;
            }
        });

        CollectionUtils.select(Arrays.asList(this.getClass().getMethods()), new AnnotationMethodPredicate(DataGeneratorMethod.class), orderedMethods);

        for ( Method method : orderedMethods ) {
            this.processMethod(method, dummyMode);
        }

        PersistenceManager.getInstance().getCurrentSession().flush();
        PersistenceManager.getInstance().getCurrentSession().clear();
    }


    protected void processMethod(Method method, boolean dummyMode) {
        DataGeneratorMethod annotation = method.getAnnotation(DataGeneratorMethod.class);
        boolean isDummyMethod = annotation.dummyData();
        if ( (isDummyMethod && dummyMode) || !isDummyMethod ) {
            this.invokeMethod(annotation, method);
        }
    }


    protected void invokeMethod(DataGeneratorMethod annotation, Method method) {
        String csvFile = annotation.csvFile();
        if ( "".equals(csvFile) ) {
            ReflectionUtils.invoke(this, method);
        }
        else {
            CSVParser parser = new CSVParser(this, new DDLGeneratorCVSLineCallback(this, method, 500));
            parser.parseStream(InitialDataGenerator.class.getClassLoader().getResourceAsStream(csvFile));
        }
    }


    public Collection<DependecyAware> dependencies() {
        final List<DependecyAware> _list = new ArrayList<DependecyAware>();

        DataGeneratorDepends annotation = this.getClass().getAnnotation(DataGeneratorDepends.class);
        if ( annotation != null ) {
            for ( final Class<? extends InitialDataGenerator> _class : annotation.value() ) {
                _list.add(ReflectionUtils.instanciate(_class));
            }
        }
        return _list;
    }
    
//	private GenericDAO<P> dao;
//
//    protected GenericDAO<P> getDao() {
//    	return dao;
//    }
}
