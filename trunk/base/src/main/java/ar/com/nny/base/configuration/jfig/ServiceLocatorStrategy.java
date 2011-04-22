package ar.com.nny.base.configuration.jfig;

public interface ServiceLocatorStrategy {

    public <T> T locate(Class<T> serviceClass, boolean searchAmongNotFinishedOnes);

}
