package ar.edu.unq.tpi.ui.swing.components;

import javax.swing.JOptionPane;

import ar.edu.unq.tpi.base.bean.User;
import ar.edu.unq.tpi.util.common.HashUtils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("deprecation")
public class Registrar extends GeneralLogin {
	private static final long serialVersionUID = -2516849884023358109L;

	public Registrar() {
	super();
	this.setSize(350, 180);	
	this.setVisible(false);
	}

	public void init() {
		this.setTitle("Registrarse");
		super.init();
		this.agregarComponentes();
		this.getPanel().append(this.getConfirmaContrasenia(),
				this.getConfirmacon());
		this.addActions();
		this.getPanel().append(this.getAceptar(), this.getCancelar());
		DefaultFormBuilder defaultFormBuilder = new DefaultFormBuilder(new FormLayout("pref, pref"));
		defaultFormBuilder.append(this.getPanel().getPanel());
		defaultFormBuilder.append(this.getNotifyError());
		this.add(defaultFormBuilder.getPanel());
	}

	public void agregarComponentes() {
		super.agregarComponentes();

	}

	@Override
	public void addActions() {
		super.addActions();
	}
	
	@Override
	public void onClickAceptar() {
		String nombrer = getNombret().getText();
		String passwordr = getPass().getText();
		String passwordv = getConfirmacon().getText();
		if (verificarCamposVacios()
				| getConfirmacon().getText().isEmpty()) {
			//Registrar.this.limpiar();
			Registrar.this.getNotifyError().setText("");
			JOptionPane.showMessageDialog(Registrar.this.getAceptar(),
					"Debe de completar todos los campos.");
		} else {
			if (buscarUsuario(nombrer) == null) {
				if (passwordr.equals(passwordv)) {
					User usuarionew = new User(nombrer,HashUtils.hash(passwordr));
					usuarionew.setConect(false);
					onSubmit(usuarionew);
					Registrar.this.limpiar();
					Registrar.this.getNotifyError().setText("");
					JOptionPane.showMessageDialog(Registrar.this
							.getConfirmacon(),
							"se ha registrado con exito.");

				} else {
					Registrar.this.limpiar();
					Registrar.this.getNotifyError().setText(
							"Contrase�as no coinciden.");
				}
			} else {
				Registrar.this.limpiar();
				Registrar.this.getNotifyError().setText(
						"El usuario ya existe.");
			}

		}
	}

	
	protected void onSubmit(User usuarionew) {
		home.saveOrUpdate(usuarionew);
		this.setVisible(false);
	}
	
	public void limpiar() {
		super.limpiar();
		this.getConfirmacon().setText("");

	}

	public static void main(String[] args) {
		new Registrar();
	}
}
