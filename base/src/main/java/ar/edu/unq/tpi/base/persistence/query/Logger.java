package ar.edu.unq.tpi.base.persistence.query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
        private static final Log LOG = LogFactory.getLog(Logger.class);
        
        public static void log(Object object){
                LOG.debug(object);
        }

}