package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Ronny
 * 
 */
public class GeneralPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public GeneralPanel(final LayoutManager layout) {
        super(layout);
    }

    public GeneralPanel() {
        super();
    }

    public void addBoton(final String nombre, final ActionListener listener) {
        JButton button = new JButton(nombre);
        button.addActionListener(listener);
        this.add(button);
    }

    public void addLabel(final String labelText, final int center) {
        this.add(new JLabel(labelText, center));
    }

    protected void addTextField(final String name, final String style) {
        this.add(new JTextField(name), style);
    }

    protected void addComponent(final Component component, final String style) {
        this.add(component, style);
    }
}