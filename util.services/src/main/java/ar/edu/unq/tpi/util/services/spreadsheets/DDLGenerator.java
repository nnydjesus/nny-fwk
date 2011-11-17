package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DDLGenerator {

    public static final GeneratorsEnvironments ENVIRONMENT = GeneratorsEnvironments.DEFAULT;


    private static final Logger log = Logger.getLogger(DDLGenerator.class.getName());


    public static String ROOT_RT_PATH() {
        return ENVIRONMENT.getPath();
    }


    public static void generate(Generator[] generators) {
        try {
            create(generators);
        } catch (Throwable e) {
            log.log(Level.SEVERE, "Error while creating: " + e.getMessage(), e);
            throw new GeneratorExcepction(e);
        }
    }

    private static void create(Generator[] generators) throws Exception {
        final Map<Class<?>, Generator> mapGenerators = Collections.synchronizedMap(new HashMap<Class<?>, Generator>());

        // staring UNSORTED ========================================================================================
        final ExecutorService executorService;
        if (GeneratorsEnvironments.DEVELOPMENT) {
            executorService = Executors.newFixedThreadPool(1);
        } else {
            executorService = Executors.newFixedThreadPool(10);
        }

        for (final Generator generator : generators) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        setListSynch(mapGenerators, generator);
                        generator.generate();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.log(Level.SEVERE, "Generator failed: " + generator.getClass().getName(), e);
                    }
                }
            });
        }


        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        log.log(Level.INFO, "Generators done");
        log.log(Level.INFO, Generator.printTimes());
    }


    private static final Object LOCK_OBJECT = new Object();

    private static void setListSynch(final Map<Class<?>, Generator> generators, final Generator generator) {
        synchronized (LOCK_OBJECT) {
            generators.put(generator.getClass(), generator);
            generator.setSortedGens(generators);
        }
    }
}
