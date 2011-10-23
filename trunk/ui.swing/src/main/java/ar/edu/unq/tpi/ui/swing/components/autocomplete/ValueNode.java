package ar.edu.unq.tpi.ui.swing.components.autocomplete;

import ar.edu.unq.tpi.base.common.Observable;

public class ValueNode {
    private Observable object;
    private String property;
    
    public ValueNode(Observable object, String property) {
        this.setObject(object);
        this.setProperty(property);
        
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setObject(Observable object) {
        this.object = object;
    }

    public Observable getObject() {
        return object;
    }
    
    @Override
    public String toString() {
        return object.getProperty(property).toString();
    }

}
