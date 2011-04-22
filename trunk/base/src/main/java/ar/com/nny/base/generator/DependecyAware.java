package ar.com.nny.base.generator;

import java.util.Collection;

/**
 */
public interface DependecyAware {

    public Collection<DependecyAware> dependencies();

}
