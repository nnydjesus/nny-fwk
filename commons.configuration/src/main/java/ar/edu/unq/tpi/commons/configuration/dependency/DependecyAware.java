package ar.edu.unq.tpi.commons.configuration.dependency;

import java.util.Collection;

/**
 */
public interface DependecyAware {

    public Collection<DependecyAware> dependencies();

}
