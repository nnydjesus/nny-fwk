package ar.com.nny.base.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.collection.AbstractCollectionDecorator;

/**
 * Se banca una lista de objetos agregados en cualquier orden. Los devuelve
 * iterados por orden de dependencias. 
 * 
 */
public final class DependencyManager<E extends DependecyAware> extends AbstractCollectionDecorator<E> implements
        Serializable {

    private static final long serialVersionUID = 1L;

    private final Set<Class> circularReference = new HashSet<Class>();

    public DependencyManager() {
        this(null);
    }

    public DependencyManager(final Collection<E> _es) {
        super(new ArrayList<E>());

        if (_es != null) {
            this.addAll(_es);
        }
    }

    @Override
    public boolean add(final E _e) {
        this.addRecursively(_e, (List<DependecyAware>) collection);
        return true;
    }

    private void addRecursively(final DependecyAware _dependecyAware, final List<DependecyAware> _linkedList) {
        final Collection<DependecyAware> _innerDependencies = _dependecyAware.dependencies();

        if (_innerDependencies != null && !_innerDependencies.isEmpty()) {
            for (final DependecyAware _innerDependency : _innerDependencies) {
                this.addRecursively(_innerDependency, _linkedList);
            }
        }

        if (!circularReference.add(_dependecyAware.getClass()))
            return;

        _linkedList.add(_dependecyAware);
    }

    @Override
    public boolean addAll(final Collection<? extends E> _es) {
        boolean _result = false;

        if (_es != null) {
            for (final E _e : _es) {
                if (this.add(_e)) {
                    _result = true;
                }
            }
        }
        return _result;
    }
}
