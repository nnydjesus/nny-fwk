package ar.edu.unq.tpi.ui.swing.components;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.ListModel;

import ar.edu.unq.tpi.base.search.Home;

import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

@SuppressWarnings({ "unused", "unchecked" })
public class MyJComboBox<T> extends JComboBox {

    private static final long serialVersionUID = 1L;

    private String method;

    private List<T> list ;

    public MyJComboBox() {
    }
    
    public MyJComboBox(List<T> items, ValueModel selectionHolder) {
        this.setModel(new ComboBoxAdapter<T>(items, selectionHolder));
        list = items;
    }
    
    public MyJComboBox(final List<T> anList) {
        this(new SelectionInList<T>(anList));
    }


    public MyJComboBox(SelectionInList<T> selectionInList) {
        this.setModel(new ComboBoxAdapter<T>(selectionInList));
        list = selectionInList.getList();
    }

    @Override
    public void setSelectedItem(final Object object) {
        super.setSelectedItem(object);
    }

    @Override
    public T getSelectedItem() {
        return (T) super.getSelectedItem();
    }

    public void addDefaultValue(final T value) {
        list.add(0, value);
        this.setSelectedIndex(0);
    }
    
    public void setDefaultValue(){
        this.setSelectedIndex(0);
    }

    public void selectedDefault() {
        this.setSelectedIndex(0);
    }

    public Object[] getArray() {
        return list.toArray();
    }
    
    public void updateList(List<T> all) {
        this.list.removeAll(list);
        this.list.addAll(all);
    }

    // class MyRenderer extends DefaultListCellRenderer{
    // @Override
    // public Component getListCellRendererComponent(JList list, Object value,
    // int index, boolean isSelected, boolean cellHasFocus) {
    // JLabel jLabel = new JLabel();
    // if(value == null)
    // return jLabel;
    // jLabel.setText(((Observable) value).mostrar());
    // // if(value instanceof Observable)
    // // jLabel = new JLabel(((Observable) value).getId());
    // // else
    // // jLabel = new JLabel(value.toString());
    // if (isSelected) {
    // jLabel.setBackground(Color.BLACK);
    // jLabel.setForeground(Color.RED);
    // }
    // return jLabel;
    // }
    // }

}
