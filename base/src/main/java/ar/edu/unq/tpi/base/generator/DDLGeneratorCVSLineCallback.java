package ar.edu.unq.tpi.base.generator;

import java.lang.reflect.Method;

import ar.edu.unq.tpi.base.persistence.PersistenceManager;
import ar.edu.unq.tpi.commons.configuration.cvs.CVSLineCallback;
import ar.edu.unq.tpi.util.common.ReflectionUtils;

public class DDLGeneratorCVSLineCallback implements CVSLineCallback {

    private final Method callback;
    private final InitialDataGenerator object;

    private int callCount = 0;
    private int flushSize;


    public DDLGeneratorCVSLineCallback(InitialDataGenerator object, Method callback, int flushSize) {
        this.object = object;
        this.callback = callback;
        this.flushSize = flushSize;
    }


    public Class[] getParameterTypes() {
        return this.callback.getParameterTypes();
    }


    public void invoke(Object[] parameters) {
        this.callCount++;
        if ( this.flushSize == this.callCount ) {
            this.callCount = 0;
            PersistenceManager.getInstance().clearCurrentSession();
        }

        ReflectionUtils.invokeNotDynarg(this.object, this.callback, parameters);
    }

}
