package ar.edu.unq.tpi.base.common;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;


abstract public class ItemComposite extends Vector<Object> implements Item {

    private static final long serialVersionUID = 1L;

    @Override
    public abstract String toString(); 

    @Override
    public void open() {
    }
    public void addItem(Object object) {
    }
    public abstract void updateTree();

    public abstract void mostrar(Object item);
    
    public  List<ItemComposite> getItems(){
    	return Arrays.asList(this);
    }

    @Override
    public  void update() {
        for (Object item : this) {
            ((Item) item).update();
        }
    }

}
