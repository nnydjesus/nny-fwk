package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;


public class Generator{

    private static final Map<Long, String> timesMap = Collections.synchronizedMap(new TreeMap<Long, String>(new Comparator<Long>() {

        @Override
        public int compare(final Long l1, final Long l2) {
            return l2.compareTo(l1);
        }
    }));

    protected static final Logger log = Logger.getLogger(Generator.class.getName());

    private Map<Class<?>, Generator> sortedGens = Collections.synchronizedMap(new HashMap<Class<?>, Generator>());

	private Function function;

	private SSConnectorStrategy connector;

	private String sheetName;

    
    public void configMap(Function f, SSConnectorStrategy connector, String sheetName) {
    	this.function = f;
    	this.connector = connector;
    	this.sheetName = sheetName;
	}
    
    public <T> List<T> map(Function f, SSConnectorStrategy connector, String sheetName) {
    	configMap(f, connector, sheetName);
    	try {
			return generate();
		} catch (Exception e) {
			throw new GeneratorExcepction("Bumm", e);
		}
    }

    public Generator getPreviousGenerator(final Class<?> clazz) {
        return sortedGens.get(clazz);
    }

    public void setSortedGens(final Map<Class<?>, Generator> sortedGens) {
        this.sortedGens.putAll(sortedGens);
    }

    public <T> List<T> generate() throws Exception {
        final String name = this.getClass().getName();

        log.info("Generating: " + name + " ...");

        long s = System.currentTimeMillis();
        List<T> ts = generateImpl();

        log.info(name + " done");

        timesMap.put((System.currentTimeMillis() - s), name);
        
        return ts;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> List<T> generateImpl() throws Exception {
		connector.parseSheet(this.sheetName);
		List results = new ArrayList();
		for (Map<String,Container> item : connector.getValues()) {
			results.add(this.function.execute(item));
		}
		
		return results;
	}


    public static String printTimes() {
        final StringBuilder builder = new StringBuilder(500);

        builder.append("{\n");

        for (final Long aLong : timesMap.keySet()) {
            builder.append("- [");
            builder.append(timesMap.get(aLong));
            builder.append("] took [");
            builder.append(aLong);
            builder.append("]\n");
        }

        builder.append("}\n");

        return builder.toString();
    }


	protected Function getFunction() {
		return function;
	}
}
