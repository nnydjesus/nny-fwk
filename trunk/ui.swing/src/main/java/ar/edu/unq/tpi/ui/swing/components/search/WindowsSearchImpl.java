package ar.edu.unq.tpi.ui.swing.components.search;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ar.edu.unq.tpi.base.common.Item;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;

public class WindowsSearchImpl<T extends IdentificablePersistentObject> extends JFrame implements Item, ar.edu.unq.tpi.ui.swing.components.WindowsSearch<T>{
	private static final long serialVersionUID = 1L;
	
	
	private SearchPanel<T> search;
    
    public WindowsSearchImpl(T model, Home<T> home) {
         search = new SearchPanel<T>(model, home, this);
         this.add(search);
         this.setSize(1024, 768);
    }
    public WindowsSearchImpl(SearchPanel<T> search) {
        this.search = search;
        this.add(search);
        this.setSize(1024, 768);
    }
    
    @Override
    public String toString() {
        return "Totalidad de " + search.getModel().getName();
    }

    @Override
    public void mostrar() {
        search.search();
        this.setVisible(true);
    }
    @Override
    public void editSelected(T selected) {
    }
    @Override
    public void update() {
        SwingUtilities.updateComponentTreeUI(search);        
    }
    @Override
    public void deleteObject(Object selected) {
    }
    
    @Override
    public void close() {
    	setVisible(false);
    	dispose();    	
    }
	@Override
	public T getDefaultModel() {
		return search.getHome().createExample();
	}

}
