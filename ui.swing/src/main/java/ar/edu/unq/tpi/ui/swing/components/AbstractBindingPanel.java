package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

/**
 */
public class AbstractBindingPanel<T> extends JPanel {
	private static final long serialVersionUID = -4916057975139862174L;

	private JLabel titulo;

	private ButtonBarBuilder2 panelDeBotones;

	private FormBuilder<T> builder;

	public AbstractBindingPanel(final T model) {
		builder = new FormBuilder<T>(model);
		this.titulo = new JLabel();
		panelDeBotones = new ButtonBarBuilder2();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.construirPanelEdicion();

	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if(panelDeBotones != null){
			titulo.setFont(font);
			panelDeBotones.getContainer().setFont(font);
		}
	}

	protected void construirPanelEdicion() {
		this.addButtons();
		this.add(this.titulo);
		this.add(builder.build());
		this.add(this.getPanelDeBotones().getPanel());

//		GroupLayout layout = new GroupLayout(this);
//		layout.setHorizontalGroup(layout.createParallelGroup()
//				.addComponent(titulo)
//				.addComponent(builder.build())
//				.addComponent(this.getPanelDeBotones().getPanel()));
//		layout.setVerticalGroup(layout.createSequentialGroup()
//				.addComponent(titulo)
//				.addComponent(builder.build())
//				.addComponent(this.getPanelDeBotones().getPanel()));
//
//		this.setLayout(layout);
	}

	protected void addButtons() {
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
	public JTextField addBindingTextField(PropertyModel model,
			final String property, final String label) {
		return builder.addBindingTextField(model, property, label);
	}

	public JTextField addBindingTextField(final String property,
			final String label) {
		return builder.addBindingTextField(property, label);
	}

	public JFormattedTextField addBindingIntegerField(final String property,
			final String label) {
		return builder.addBindingIntegerField(property, label);
	}
	
	public JFormattedTextField addBindingDoubleField(final String property,
			final String label) {
		return builder.addBindingDoubleField(property, label);
	}

	public JDateChooser addBindingDateField(final String property,
			final String label) {
		return builder.addBindingDateField(property, label);
	}

	public JCheckBox addBindingCheckBox(final String property,
			final String label) {
		return builder.addBindingCheckBox(property, label);
	}

	public void addComponent(final String label, final Component component) {
		builder.addComponent(label, component);
	}

	public JComboBox addBindingComboBox(final String property,
			final List<?> list) {
		return this.addBindingComboBox(StringUtils.capitalize(property),
				property, list);
	}

	public JComboBox addBindingComboBox(final String label,
			final String property, final List<?> list) {
		return builder.addBindingComboBox(label, property, list);
	}

	public JComboBox addBindingComboBox(String property, Object[] values) {
		return this.addBindingComboBox(StringUtils.capitalize(property),
				property, values);
	}

	public JComboBox addBindingComboBox(final String label, String property,
			Object[] values) {
		return addBindingComboBox(label, property, new SelectionInList<Object>(
				values));
	}

	public JComboBox addBindingComboBox(final String label, String property,
			SelectionInList<?> selectionInList) {
		return builder.addBindingComboBox(label, property, selectionInList);
	}

	public void bind(final JComponent component, final String propertyName,
			final String property) {
		builder.bind(component, propertyName, property);
	}

	public void bind(final JComponent component, final String propertyName,
			ValueModel valueModel) {
		builder.bind(component, propertyName, valueModel);
	}

	public void setTitulo(final String title) {
		this.titulo.setText(title);
	}

	public FormBuilder<T> getBuilder() {
		return builder;
	}

	public ButtonBarBuilder2 getPanelDeBotones() {
		return panelDeBotones;
	}

	public T getModel() {
		return builder.getModel();
	}

	public void setModel(final T object) {
		builder.setModel(object);

	}

	@Override
	public String toString() {
		return getModel().toString();
	}

	public void setComboBoxModel(JComboBox combo, String property) {
		builder.setComboBoxModel(combo, property);
	}

}
