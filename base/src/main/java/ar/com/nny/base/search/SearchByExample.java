package ar.com.nny.base.search;

import java.util.List;

import ar.com.nny.base.utils.IdentificablePersistentObject;

/**
 * @param <T>
 */
public class SearchByExample<T extends IdentificablePersistentObject> extends AbstractSearch<T> {
    private static final long serialVersionUID = 1L;

    public static final String EXAMPLE = "example";

    private T example;

    private Home<T> home;

    /**
     * Straightforward constructor.
     * 
     * @param home
     *            The home into which we will delegate our searches.
     */
    public SearchByExample(final Home<T> home) {
        // super(home.getEntityType());
        this.home = home;

        this.createExampleObject();
    }

    /**
     * The object to serve as an example for the searches to be performed. Any
     * properties set into this object will be used as a filter when the search
     * method is invoked.
     */
    public T getExample() {
        return this.example;
    }

    @Override
    public void clear() {
        this.createExampleObject();
    }

    protected void createExampleObject() {
        this.example = this.home.createExample();
    }

    @Override
    protected List<T> doSearch() {
        return this.home.searchByExample(this.example);
    }

    @Override
    public String[] atributos() {
        return null;
    }

}