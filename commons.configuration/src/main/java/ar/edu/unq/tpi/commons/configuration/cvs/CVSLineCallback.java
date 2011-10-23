package ar.edu.unq.tpi.commons.configuration.cvs;

public interface CVSLineCallback {

    public abstract Class[] getParameterTypes();


    public abstract void invoke(Object[] parameters);

}