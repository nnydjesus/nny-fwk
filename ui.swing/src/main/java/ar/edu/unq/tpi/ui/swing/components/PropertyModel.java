package ar.edu.unq.tpi.ui.swing.components;

import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueModel;

public class PropertyModel<T> {

	private BeanAdapter<T> bean;
	private String property;

	public PropertyModel(T model, String property) {
		this.bean = new BeanAdapter<T>(model);
		this.setProperty(property);
	}

	public ValueModel getValueModel() {
		return bean.getValueModel(getProperty());
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

}
