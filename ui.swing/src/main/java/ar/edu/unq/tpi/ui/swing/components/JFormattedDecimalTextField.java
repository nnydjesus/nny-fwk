package ar.edu.unq.tpi.ui.swing.components;

import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import ar.edu.unq.tpi.ui.swing.components.autocomplete.LimeTextField;

public class JFormattedDecimalTextField extends JFormattedTextField {

    private static final long serialVersionUID = 1L;
    private static DecimalFormat decimalFormat;
    private static NumberFormatter decimalFormatt;
    static{
    	decimalFormat = new DecimalFormat("#.##");
    	decimalFormat.setMinimumFractionDigits(2);
    	decimalFormatt = new NumberFormatter(decimalFormat);
    	decimalFormatt.setValueClass(Double.class);
//    	decimalFormat.set
//    	decimalFormat.set
//    	format.setDecimalSeparatorAlwaysShown(true);
//    	format.setParseBigDecimal(true);
//    	decimalFormat.setFormat(format);
//    	decimalFormat.setValueClass(Double.class);
    }

    public JFormattedDecimalTextField() {
        super(decimalFormatt);
    }
}
