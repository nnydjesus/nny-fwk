package ar.edu.unq.tpi.ui.swing.components;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 */
@SuppressWarnings("serial")
public class CenteredJFrame extends JFrame {

	public CenteredJFrame() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.centerWindow();
		
	}
	
    /**
     * Centra la ventana
     */
    public void centerWindow() {
//    	this.setLocationRelativeTo(null);
    	this.setLocation(200, 100);
    }
    
    // Es localizable
    protected ResourceBundle bundle;
    protected Locale currentLocale = new Locale("es", "ES");
    /**
     * Devuelve el ResourceBundle asociado a esta ventana
     */
    public ResourceBundle getBundle() {
        return this.bundle;
    }

     /**
     * Setea un nuevo archivo al cual asociarse.
     */
    public void setBundleFile(String newBundleFile) {
         this.bundle = ResourceBundle.getBundle(newBundleFile, this.currentLocale);
    }

    /**
     * Setea un nuevo lenguaje para quienes hereden de esta clase
     */
    public void setCurrentLocale(Locale locale) {
        this.currentLocale = locale;
    }
    
    
	protected void infoMessage(String title, String message) {
		JOptionPane.showMessageDialog(this,
			    message, 
			    title,
			    JOptionPane.INFORMATION_MESSAGE);
	}

	protected void warningMessage(String title, String message) {
		JOptionPane.showMessageDialog(this,
			    message, 
			    title,
			    JOptionPane.WARNING_MESSAGE);
	}

	protected void errorMessage(String title, String message) {
		JOptionPane.showMessageDialog(this,
			    message, 
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
}
