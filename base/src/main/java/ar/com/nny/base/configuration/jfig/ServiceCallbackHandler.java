package ar.com.nny.base.configuration.jfig;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Handles aspect interception to a service instance.
 * <p/>
 * Uses the DungeonKeeper to create a chain of interceptors to handle the
 * request and then forward the execution to it.
 *
 * @author Claudio
 */
public class ServiceCallbackHandler implements MethodInterceptor {

    public Object intercept(Object service, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        CodeBlock block = DungeonKeeper.getInstance().buildInvocationChain(service, method, args, methodProxy);

        return block.execute();
    }

}