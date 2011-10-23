package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ar.edu.unq.tpi.base.bean.User;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.Path;
import ar.edu.unq.tpi.util.commons.exeption.UserException;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public abstract class GeneralLogin extends CenteredJFrame {

    private static final long serialVersionUID = 1L;

    // private JLabel nombre = new JLabel("Nombre de Usuario :");
    // private JLabel password =new JLabel("Password :");
    private JTextField nombret = new JTextField();;

    private JPasswordField pass = new JPasswordField();

    private JButton aceptar = new JButton(new ImageIcon(Path.path() + "Images/check.jpg"));

    private JButton cancelar = new JButton(new ImageIcon(Path.path() + "Images/cancel.jpg"));

    private DefaultFormBuilder panel;

    private JLabel notifyError = new JLabel();

    private JLabel confirmaContrasenia = new JLabel("Confirmacion de contrasenia");

    private JPasswordField confirmacon = new JPasswordField();

    protected Home<User> home = new Home<User>(User.class, false);

    public GeneralLogin() {
        this.init();
        this.pack();
        this.setLocation(350, 330);
    }

    public void agregarComponentes() {
        panel.append("Nombre de Usuario :", nombret);
        panel.append("Password :", pass);

    }

    public void addActions() {
        cancelar.addActionListener(new ActionMethodListener(this, "onCancel"));

        aceptar.addActionListener(new ActionMethodListener(this, "onClickAceptar"));
        pass.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GeneralLogin.this.onClickAceptar();
                }
            }
        });

    }

    public abstract void onClickAceptar();

    public void onCancel() {
        this.setVisible(false);
    }

    public void init() {
        panel = new DefaultFormBuilder(new FormLayout("pref, 4dlu, pref"));
        aceptar.setText("Aceptar");
        cancelar.setText("Cancelar");
        notifyError.setForeground(Color.RED);
    }

    public void limpiar() {
        nombret.setText("");
        pass.setText("");
    }

    public User buscarUsuario(final String nombre) {
        User byId = null;
        try {
            byId = home.getByName(nombre);
        } catch (UserException e) {
            notifyError.setText(e.getMessage());
        }
        return byId;
    }

    @SuppressWarnings("deprecation")
    public boolean verificarCamposVacios() {
        return nombret.getText().isEmpty() | pass.getText().isEmpty();
    }

    public JTextField getNombret() {
        return nombret;
    }

    public void setNombret(final JTextField nombret) {
        this.nombret = nombret;
    }

    public JPasswordField getPass() {
        return pass;
    }

    public void setPass(final JPasswordField pass) {
        this.pass = pass;
    }

    public JButton getAceptar() {
        return aceptar;
    }

    public void setAceptar(final JButton aceptar) {
        this.aceptar = aceptar;
    }

    public JButton getCancelar() {
        return cancelar;
    }

    public void setCancelar(final JButton cancelar) {
        this.cancelar = cancelar;
    }

    public DefaultFormBuilder getPanel() {
        return panel;
    }

    public void setPanel(final DefaultFormBuilder panel) {
        this.panel = panel;
    }

    public void setNotifyError(final JLabel notifyError) {
        this.notifyError = notifyError;
    }

    public JLabel getNotifyError() {
        return notifyError;
    }

    public void setConfirmaContrasenia(final JLabel confirmaContrasenia) {
        this.confirmaContrasenia = confirmaContrasenia;
    }

    public JLabel getConfirmaContrasenia() {
        return confirmaContrasenia;
    }

    public void setConfirmacon(final JPasswordField confirmacon) {
        this.confirmacon = confirmacon;
    }

    public JPasswordField getConfirmacon() {
        return confirmacon;
    }

}
