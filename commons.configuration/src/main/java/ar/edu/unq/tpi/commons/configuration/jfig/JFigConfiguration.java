package ar.edu.unq.tpi.commons.configuration.jfig;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.igfay.jfig.JFig;
import org.igfay.jfig.JFigException;
import org.igfay.jfig.JFigIF;
import org.igfay.jfig.JFigLocator;

/**
 * Esta clase es un wrapper de Jfig. Cuando se instancia, llama a su metodo
 * privado refresh, el cual carga los parametros. Dado que se registra como
 * listener de cambios a properties, tambien ejecuta el refresh en el metodo
 * configurationUpdate.
 * 
 */
public final class JFigConfiguration {

    private static final String CONFIG_FILE_NAME = "fconfig.xml";

    private static JFigConfiguration INSTANCE;

    private JFigIF jFig;

    public static JFigConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JFigConfiguration();
        }
        return INSTANCE;
    }

    private JFigConfiguration() {
        try {
            JFigLocator locator = new JFigLocator(CONFIG_FILE_NAME);
            locator.setConfigLocation("classpath");
            jFig = JFig.initialize(locator);
        } catch (JFigException e) {
            throw new RuntimeException("Could not initialize configuration. Check for location of file ["
                    + CONFIG_FILE_NAME + "]:", e);
        }
    }

    public Map getEntries() {
        return jFig.getConfigDictionary().getDictionaryOfSectionDictionaries();
    }

    public boolean getBoolean(final String section, final String key) {
        return jFig.getBooleanValue(section, key, "false");
    }

    public Properties getSectionAsProperties(final String sectionName) {
        return jFig.getSectionAsProperties(sectionName);
    }

    public float getFloat(final String section, final String key) {
        try {
            return jFig.getFloatValue(section, key, "0.0");
        } catch (JFigException e) {
            throw this.handleException(e, section, key);
        }
    }

    public int getInt(final String section, final String key) {
        try {
            return jFig.getIntegerValue(section, key);
        } catch (JFigException e) {
            throw this.handleException(e, section, key);
        }
    }

    public String getString(final String section, final String key) {
        try {
            return jFig.getValue(section, key);
        } catch (JFigException e) {
            throw this.handleException(e, section, key);
        }
    }

    public Map getStartingWith(final String section, final String key) {
        return jFig.getEntriesStartingWith(section, key);
    }

    public String[] getArray(final String section, final String key) {
        try {
            return jFig.getArrayValue(section, key);
        } catch (JFigException e) {
            throw this.handleException(e, section, key);
        }
    }

    public String fileToString() {
        StringBuilder buffer = new StringBuilder(this.getClass().toString());
        buffer.append("\n");

        for (Iterator it = this.getEntries().entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            buffer.append(entry.getKey());
            buffer.append(entry.getValue());
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private RuntimeException handleException(final JFigException jFigException, final String section, final String key) {
        StringBuilder buffer = new StringBuilder(80);

        buffer.append("Could not find configuration parameter for section [").append(section).append("] with key [")
                .append(key).append("]: ").append(jFigException.getMessage());

        return new RuntimeException(buffer.toString(), jFigException);
    }

    public void setValue(final String section, final String parameter, final String value) {
        jFig.setConfigurationValue(section, parameter, value);
    }
}
