package ar.edu.unq.tpi.ui.swing.components;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.tpi.base.common.Observable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.SelectionInList;

@SuppressWarnings({ "rawtypes" })
public class ModelBinding<E> extends AbstractTableAdapter<E> implements Model {

    private static final long serialVersionUID = 1L;
    private String[] columnNames;
    
    /**
     * Lista con los datos. Cada elemento de la lista es una instancia de
     */
    private SelectionInList<E> datos;


    public ModelBinding(final SelectionInList<E> listModel, final String[] columnNames) {
        super(listModel, columnNames);
        this.columnNames = columnNames;
        this.datos = listModel;
    }
    

    public E getSelected(final int i) {
        return this.getRow(i);
    }
    
    public List<E> getDatos() {
        return datos.getList();
    }    
    
    public void setDatos(List datos){
    	this.datos.setList(new ArrayList<E>(datos));
    }
    
    public String getMyColumnName(int columnIndex) {
    	return columnNames[columnIndex];
    }
    
    
    @Override
    public String getColumnName(int columnIndex) {
    	return super.getColumnName(columnIndex).toUpperCase();
    }
    
    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        Observable observable = (Observable) this.getRow(rowIndex);
        String colum = this.getMyColumnName(columnIndex);
        Object property = observable.getProperty(colum);
        return property;

    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        if (this.getRowCount() > 0) {
            Object valueAt = this.getValueAt(0, columnIndex);
            return valueAt == null ? Object.class : valueAt.getClass();
        } else
            return Object.class;
    }
}
