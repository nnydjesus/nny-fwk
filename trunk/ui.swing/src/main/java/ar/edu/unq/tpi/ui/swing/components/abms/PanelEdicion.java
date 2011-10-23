package ar.edu.unq.tpi.ui.swing.components.abms;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.base.utils.Path;
import ar.edu.unq.tpi.ui.swing.components.AbstractBindingPanel;

public class PanelEdicion<T extends IdentificablePersistentObject> extends AbstractBindingPanel<T> {
    private static final long serialVersionUID = -4916057975139862174L;

    private JButton botonAgregar;

    private JButton botonCancelar;

    public PanelEdicion(final T model) {
        super(model);
        this.setTitulo("Editar " + model.getName());

    }

    @Override
    protected void addButtons() {
        this.setBotonAgregar(new JButton(new ImageIcon(Path.path() + "Images/add.jpg")));
        this.getBotonAceptar().setText("Aceptar");
        this.setBotonCancelar(new JButton(new ImageIcon(Path.path() + "Images/cancel.jpg")));
        this.getBotonCancelar().setText("Cancelar");

        this.getPanelDeBotones().addButton(this.getBotonAceptar());
        this.getPanelDeBotones().addRelatedGap();        
        this.getPanelDeBotones().addButton(this.getBotonCancelar());
    }

    public void setBotonCancelar(final JButton botonCancelar) {
        this.botonCancelar = botonCancelar;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }

//    public void setBotonModificar(final JButton botonModificar) {
//        this.botonModificar = botonModificar;
//    }

//    public JButton getBotonModificar() {
//        return botonModificar;
//    }

    public void setBotonAgregar(final JButton botonAgregar) {
        this.botonAgregar = botonAgregar;
    }

    public JButton getBotonAceptar() {
        return botonAgregar;
    }

}
