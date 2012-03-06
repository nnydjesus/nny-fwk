package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 */
@SuppressWarnings("serial")
public class CenteredJFrame extends JFrame {
	
	private static final List<Class<? extends CenteredJFrame>> mapOfOpenWindows = new ArrayList<Class<? extends CenteredJFrame>>();
	

	public CenteredJFrame() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.centerWindow();

	}

	/**
	 * Centra la ventana
	 */
	public void centerWindow() {
		// this.setLocationRelativeTo(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 


		this.setLocation((screenSize.width / 2) - (this.getWidth() / 2),
				(screenSize.height / 2) - (this.getHeight() / 2));

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
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
		this.bundle = ResourceBundle.getBundle(newBundleFile,
				this.currentLocale);
	}
	
    public void open() {
    	if(mapOfOpenWindows.contains(this.getClass())){
    		this.dispose();
    	}else{
    		mapOfOpenWindows.add(this.getClass());
    		this.setVisible(true);
    	}
    }
    
    public void close() {
    	mapOfOpenWindows.remove(this.getClass());
    	this.setVisible(false);
    	this.dispose(); 
    }

	/**
	 * Setea un nuevo lenguaje para quienes hereden de esta clase
	 */
	public void setCurrentLocale(Locale locale) {
		this.currentLocale = locale;
	}

	protected void infoMessage(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	protected void warningMessage(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title,
				JOptionPane.WARNING_MESSAGE);
	}

	protected void errorMessage(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public void fullScreen() {
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}

}