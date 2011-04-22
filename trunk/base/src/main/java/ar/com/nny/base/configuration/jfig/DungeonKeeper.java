package ar.com.nny.base.configuration.jfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the one and only point of interception of every call to
 * the services. Every logic encapsulated inside the DungeonKeeper
 * will execute for every remote or local invocation to the service
 * <p/>
 * These are among its main responsibilities:
 * - open db transactional context for service execution if its needed.
 *
 * @author Claudio
 */
public class DungeonKeeper {

    private static final Log LOGGER = LogFactory.getLog(DungeonKeeper.class);
    private static DungeonKeeper instance;

    private Collection<String> excludedMethods;


    public static DungeonKeeper getInstance() {
        if ( instance == null ) {
            instance = new DungeonKeeper();
        }
        return instance;
    }


    //To avoid nonlocal instantiation
    private DungeonKeeper() {
        this.excludedMethods = new ArrayList<String>();
        this.excludedMethods.add("toString");
        this.excludedMethods.add("equals");
        this.excludedMethods.add("hashCode");
        this.excludedMethods.add("getClass");
    }


    //TODO: add a cache or something like that!
    public CodeBlock buildInvocationChain(final Object service, final Method method, final Object[] args, final MethodProxy methodProxy) {
        if ( this.excludedMethods.contains(method.getName()) ) {
            return new InvocationBlock(service, args, method, methodProxy);
        }
        else 
            return  new InvocationBlock(service, args, method, methodProxy);
    }

}