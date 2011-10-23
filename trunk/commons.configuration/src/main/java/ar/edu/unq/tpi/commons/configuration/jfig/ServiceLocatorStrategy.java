package ar.edu.unq.tpi.commons.configuration.jfig;

public interface ServiceLocatorStrategy {

    public <T> T locate(Class<T> serviceClass, boolean searchAmongNotFinishedOnes);

}
