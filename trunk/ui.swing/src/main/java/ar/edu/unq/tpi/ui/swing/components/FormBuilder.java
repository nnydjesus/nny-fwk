package ar.edu.unq.tpi.ui.swing.components;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import org.apache.commons.beanutils.PropertyUtilsBean;

import ar.edu.unq.tpi.base.common.Observable;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.PropertyAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

public class FormBuilder<T> extends DefaultFormBuilder{
    
    private T model;
    protected BeanAdapter<T> beanAdapter;
    
    
    public FormBuilder(final T model, FormLayout layout) {
        super(layout);
        this.model = model;
        this.setDefaultDialogBorder();
        this.construirPanelEdicion();
    }

    public FormBuilder(final T model) {
        this(model,new FormLayout("p, 2dlu, p:g"));
    }

    private void construirPanelEdicion() {
        this.beanAdapter = new BeanAdapter<T>(model);
    }

    public JPanel build() {
        return this.getPanel();
    }

    /***
     * este medoto "baindea" un campo de texto "TextField" a un modelo. El
     * modelo debe respetar el Contrato de Java beans, esoa debe tener getters y
     * setters para de la propiedad "property" esto permite que el campo le
     * pague al modelo automaticamente.
     * 
     * @param field
     * @return
     */
    public JTextField addBindingTextField(final String property, final String label) {
        ValueModel nameModel = beanAdapter.getValueModel(property);
        JTextField createTextField = ComponentFactory.createTextField(nameModel);
        this.append(label, createTextField);
        return createTextField;
    }
    public JTextField addBindingTextField(PropertyModel model,final String property, final String label) {
    	JTextField createTextField = ComponentFactory.createTextField(model.getValueModel());
        this.append(label, createTextField);
        return createTextField;
    }

    public JFormattedTextField addBindingIntegerField(final String property, final String label) {
        ValueModel nameModel = beanAdapter.getValueModel(property);
        JFormattedTextField createIntegerField = ComponentFactory.createIntegerField(nameModel,
                NumberFormat.getNumberInstance());
        this.append(label, createIntegerField);
        return createIntegerField;
    }

    public JFormattedTextField addBindingDoubleField(final String property, final String label) {
        ValueModel valueModel = beanAdapter.getValueModel(property);
        JFormattedTextField textField = new JFormattedDecimalTextField();
        Bindings.bind(textField, valueModel);
        this.append(label, textField);
        return textField;
    }

    public JDateChooser addBindingDateField(final String property, final String label) {
        JDateChooser date = new JDateChooser();
        date.setPreferredSize(new Dimension(130, 20));
        date.setFont(new Font("Verdana", Font.PLAIN, 10));
        date.setDateFormatString("dd/M/yyyy");

		 URL iconURL = date.getClass().getResource(
		 "/com/toedter/calendar/images/JMonthChooserColor32.gif");
		 ImageIcon icon = new ImageIcon(iconURL);
		 date.setIcon(icon);
        ValueModel nameModel = beanAdapter.getValueModel(property);
        Bindings.bind(date, "date", nameModel);
        this.append(label, date);
        return date;
    }

    public JCheckBox addBindingCheckBox(final String property, final String label) {
        ValueModel nameModel = beanAdapter.getValueModel(property);
        JCheckBox createCheckBox = ComponentFactory.createCheckBox(nameModel, label);
        this.append(label, createCheckBox);
        return createCheckBox;
    }

    public void addComponent(final String label, final Component component) {
        this.append(label, component);
    }

    public JComboBox addBindingComboBox(final String label, final String property, final List<?> list) {
        return addBindingComboBox(label, property,  new SelectionInList(list));
    }

    public JComboBox addBindingComboBox(String label, String property, Object[] values) {
        return addBindingComboBox(label, property,  new SelectionInList(values));
    }
    public JComboBox addBindingComboBox(String label, String property, SelectionInList<?> selectionInList) {
        ValueModel valueModel = beanAdapter.getValueModel(property);
        JComboBox createCombobox = ComponentFactory.createComboBox(selectionInList);
//        JComboBox createCombobox = new JComboBox(selectionInList.getList().toArray());
        this.bind(createCombobox, "selectedItem", valueModel);
        JLabel append = this.append(label, createCombobox);
        createCombobox.addActionListener(new ActionMethodListener(this, "setComboBoxModel", createCombobox, property));
        return createCombobox;
    }
    
    public void bind(final JComponent component, final String propertyName, final String property) {
        ValueModel valueModel = beanAdapter.getValueModel(property);
        bind(component, propertyName, valueModel);
    }
    public void bind(final JComponent component, final String propertyName, ValueModel valueModel) {
        Bindings.bind(component, propertyName, valueModel);
    }


    public T getModel() {
        return model;
    }

    public void setModel(final T object) {
        this.model = (T) object;
        beanAdapter.setBean(object);

    }

    public BeanAdapter<T> getBeandAdapter() {
        return beanAdapter;
    }

    public void setComboBoxModel(MyJComboBox combo,String property){
        this.setComboBoxModel((JComboBox)combo, property);
    }
    public void setComboBoxModel(JComboBox combo,String property){
        ((Observable)this.getModel()).setProperty(property, combo.getSelectedItem());
    }

    @Override
    public String toString() {
        return model.toString();
    }
}
