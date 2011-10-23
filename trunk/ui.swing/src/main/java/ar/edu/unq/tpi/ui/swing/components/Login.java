package ar.edu.unq.tpi.ui.swing.components;

import javax.swing.JOptionPane;

import ar.edu.unq.tpi.base.bean.User;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class Login extends GeneralLogin {
    private static final long serialVersionUID = 1L;

    private User user;

    protected DefaultFormBuilder defaultFormBuilder;

    public Login() {
        super();
        this.setSize(250, 150);
        this.setVisible(true);
    }

    @Override
    public void init() {
        this.setTitle("Login");
        super.init();
        super.agregarComponentes();
        this.getPanel().append(this.getAceptar(), this.getCancelar());
        this.addActions();
        defaultFormBuilder = new DefaultFormBuilder(new FormLayout("pref, pref"));
        defaultFormBuilder.append(this.getPanel().getPanel());
        defaultFormBuilder.append(this.getNotifyError());
        this.add(defaultFormBuilder.getPanel());
    }

    @Override
    public void addActions() {
        super.addActions();

    }

    @Override
    public void onClickAceptar() {
        if (this.verificarCamposVacios()) {
            JOptionPane.showMessageDialog(this.getAceptar(), "Debe completar todos los campos.");
        } else {
            String nombreIngresado = this.getNombret().getText();
            String passwordIngresado = this.getPass().getText();
            user = this.buscarUsuario(nombreIngresado);
            if (user != null) {
                if (this.inOn()) {
                    this.getNotifyError().setText("Ya estas logeado.");
                } else {
                    if (user.checkPassword(passwordIngresado)) {
                        this.onSubmit(nombreIngresado);
                        this.limpiar();
                    } else {
                        this.getNotifyError().setText("Contrase�a incorrecta.");
                    }
                }

            } else {
                this.getNotifyError().setText("Usuario Incorrecto.");
            }
        }

    }

    @Override
    public void onCancel() {
        super.onCancel();
        this.dispose();
    }

    protected void onSubmit(final String nombreIngresado) {
        Login.this.getNotifyError().setText("Bienvenido  " + nombreIngresado);
    }

    public boolean inOn() {
        return user.isConect();
    }

    public static void main(final String[] args) {
        new Login();
    }
}
