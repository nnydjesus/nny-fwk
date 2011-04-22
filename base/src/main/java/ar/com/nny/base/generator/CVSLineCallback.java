package ar.com.nny.base.generator;

public interface CVSLineCallback {

    public abstract Class[] getParameterTypes();


    public abstract void invoke(Object[] parameters);

}