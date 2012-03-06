package ar.edu.unq.tpi.ui.swing.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

public class JFormattedDecimalTextField extends JFormattedTextField {

	private static final long serialVersionUID = 1L;
	public static final DecimalFormat decimalFormat;

	static {
		decimalFormat = new DecimalFormat("#.#");
		decimalFormat.setMinimumFractionDigits(2);
	}

	public JFormattedDecimalTextField() {
		super(decimalFormat);

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				selectAll();
			}
		});
	}
}
