package ar.com.nny.base.generator;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import ar.com.nny.base.configuration.ApplicationRegistryReader;
import ar.com.nny.base.generator.annotations.DataGenerator;
import ar.com.nny.base.persistence.PersistenceManager;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class DDLGenerator {

    private static Log LOGGER = LogFactory.getLog(DDLGenerator.class);

    public static void initializeSchema() {
        SchemaExport exporter = new SchemaExport(PersistenceManager.getInstance().getConfiguration());
//        PersistenceManager.getInstance().clearCurrentSession();
        LOGGER.debug("Eliminando la tabla...");
        exporter.setOutputFile("script.sql");
        exporter.drop(true, true);
        LOGGER.debug("Creando el schema...");
        exporter.create(true, true);
        LOGGER.debug("End initialize schema");
    }

    public static void initializeData(final boolean dummyMode) {
        // SecurityHelper.overrideSecurity();

        LOGGER.debug("Creating initial data...");
        System.out.println("Initializing Data *****************************************");
        final Collection<Class<? extends PersistentObject>> persistentClasses = ApplicationRegistryReader.getInstance()
                .getAllPersistentClasses();

        final DependencyManager<InitialDataGenerator> _dependencyManager = new DependencyManager<InitialDataGenerator>();
        // final List<CharacteristicGenerator> _caracteristicaGenerators = new
        // ArrayList<CharacteristicGenerator>();

        
        TreeSet<Class<? extends PersistentObject>> orderedInitialdata = new TreeSet<Class<? extends PersistentObject>>(new Comparator<Class<? extends PersistentObject>>() {
            public int compare(Class<? extends PersistentObject> data1, Class<? extends PersistentObject> data2) {
                int order1 = data1.getAnnotation(DataGenerator.class).order();
                int order2 = data2.getAnnotation(DataGenerator.class).order();
                return order1 >= order2 ? 1 : -1;
            }
        });

        //Ordenando las los inialdata por orden
        CollectionUtils.select(persistentClasses, new AnnotationClassPredicate(DataGenerator.class), orderedInitialdata);
        

        for (Class<? extends PersistentObject> persistentClass : orderedInitialdata) {
            final DataGenerator generatorAnnotation = persistentClass.getAnnotation(DataGenerator.class);
            if (generatorAnnotation != null) {
                _dependencyManager.add(ReflectionUtils.instanciate(generatorAnnotation.value()));
            }

        }
        for (final InitialDataGenerator _initialDataGenerator : _dependencyManager) {
            runGenerator(dummyMode, _initialDataGenerator);
        }

        System.out.println("Initialization Done ***************************************");

        System.out.println("Optimizing Indexes ****************************************");
        System.out.println("Optimization Done *****************************************");
    }

    private static void runGenerator(final boolean dummyMode, final InitialDataGenerator generator) {
        generator.run(dummyMode);
    }
    
    public static void main() {
        // final DatabaseInitializerService service =
        // ServiceLocator.locate(DatabaseInitializerService.class);

        // run initial post statements
        // ************************************************//
        // service.runInitialPrevStatements();
        // ************************************************//
        ApplicationRegistryReader.getInstance().buildTypeInferencer();
        initializeSchema();
        initializeData(true);
        
        // build up dataBase with entities and generators
        // ************************************************//

        // ************************************************//

        // run initial post statements
        // ************************************************//
        // service.runInitialPostStatements();
        // ************************************************//
    }

}
