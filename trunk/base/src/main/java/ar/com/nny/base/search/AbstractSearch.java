package ar.com.nny.base.search;

import java.io.Serializable;
import java.util.List;

import ar.com.nny.base.common.Observable;

public abstract class AbstractSearch<T extends Observable> extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String LIST = "list";

    public static final String SELECTED = "selected";

    private T selected;

    private List<T> list;

    public AbstractSearch() {
        this.search();
    }

    // public abstract Class<T> getSearchType();

    // ***********************************************************
    // ** Actions
    // ***********************************************************

    public void search() {
        List<T> oldList = this.list;
        this.list = this.doSearch();
        this.setSelected(null);
    }

    protected abstract List<T> doSearch();

    public abstract void clear();

    // ***********************************************************
    // ** Accessors
    // ***********************************************************

    public List<T> getList() {
        return this.list;
    }

    public T getSelected() {
        return this.selected;
    }

    public void setSelected(final T selected) {
        T oldSelected = this.selected;
        this.selected = selected;
    }
}
