package ar.edu.unq.tpi.util.services.services;

public interface ServiceLocatorStrategy {

    public <T> T locate(Class<T> serviceClass, boolean searchAmongNotFinishedOnes);

}
