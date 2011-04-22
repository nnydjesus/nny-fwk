package ar.com.nny.base.configuration.jfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public class InvocationBlock implements CodeBlock {

    protected Object service;
    protected MethodProxy methodProxy;
    protected Method method;
    protected Object[] args;


    public InvocationBlock(final Object service, final Object[] args, final Method method, final MethodProxy methodProxy) {
        this.service = service;
        this.args = args;
        this.method = method;
        this.methodProxy = methodProxy;
    }


    /**
     * It proceeds with the service invocation.
     */
    public Object execute() {
        try {
            return methodProxy.invokeSuper(this.service, this.args);
        }
        catch ( final Throwable e ) {
            throw new CodeBlockTargetException(e);
        }
    }


    public <T extends Annotation> T getMetadata(final Class<T> annotationClass) {
        return this.method.getAnnotation(annotationClass);
    }

    public <T extends Annotation> T getClassMetadata(final Class<T> annotationClass) {
    	return this.method.getDeclaringClass().getAnnotation(annotationClass);
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Invocation[");
        builder.append(methodProxy.getSignature().toString());
        builder.append("]");
        return builder.toString();
    }

}