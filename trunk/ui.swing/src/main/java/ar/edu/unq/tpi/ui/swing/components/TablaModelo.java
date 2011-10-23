package ar.edu.unq.tpi.ui.swing.components;

import java.util.List;

import ar.edu.unq.tpi.base.common.Observable;

@SuppressWarnings({ "unused", "unchecked" })
public class TablaModelo<T> extends Modelo<T> {

    private static final long serialVersionUID = 1L;

    private ModelBinding modelBinding;

    private String[] columnNames;

    public TablaModelo() {
    }

    public TablaModelo(final List<T> listModel, final String[] columnNames) {
        this.getDatos().addAll(listModel);
        this.columnNames = columnNames;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return this.getDatos().size();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        Observable observable = (Observable) this.getDatos().get(rowIndex);
        String colum = this.columnNames[columnIndex];
        Object property = observable.getProperty(colum);
        System.out.println(property);
        return property;

    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return this.getValueAt(0, columnIndex).getClass();

    }

    @Override
    public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
        this.getDatos().add(rowIndex, aValue);
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumnClass(columnIndex)==Boolean.class ;
    }
}
