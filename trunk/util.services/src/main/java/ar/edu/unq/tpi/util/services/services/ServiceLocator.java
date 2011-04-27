package ar.edu.unq.tpi.util.services.services;


/**
 * This class is a service instance provider (and factory). Every service instance
 * should be obtained through ServiceLocator methods (or through DI framework)
 *
 */
public class ServiceLocator {

    private static ServiceLocatorStrategy locatorStrategy = new ServiceLocatorStrategyImpl();


    public static void setLocatorStrateg(ServiceLocatorStrategy strategy) {
        locatorStrategy = strategy;
    }


    /**
     * Returns an implementation of the Service given by the specified class "serviceClass".
     * The returned instance could be shared by multiple threads, ServiceLocator access ensure
     * the existence of an unique instance of each service within the system.
     * <p/>
     * Returned instances could be decorated, extended or bytecode manipulated. So clients shouldn't
     * base its logic on services concrete return type.
     */
    public static <T> T locate(Class<T> serviceClass) {
        return locate(serviceClass, false);
    }


    /**
     * d
     * NOT set the searchAmongNotFinishedOnes flag to true in business code.
     * It could return a non fully built service.
     * <p/>
     * Returns an implementation of the Service given by the specified class "serviceClass".
     * The returned instance could be shared by multiple threads, ServiceLocator access ensure
     * the existence of an unique instance of each service within the system.
     * <p/>
     * Returned instances could be decorated, extended or bytecode manipulated. So clients shouldn't
     * base its logic on services concrete return type.
     */
    public static <T> T locate(Class<T> serviceClass, boolean searchAmongNotFinishedOnes) {
        return locatorStrategy.locate(serviceClass, searchAmongNotFinishedOnes);
    }
    
    public static void main(String[] args) {
//        MailService locate = locate(MailService.class);
//        Mail buildMail = locate.buildMail();
//        buildMail.setMailMode(MailMode.HTML);
        
    }

}
