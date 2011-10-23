package ar.edu.unq.tpi.ui.swing.components.abms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;

public class ABMEdition<T extends IdentificablePersistentObject> extends PanelEdicion<T> {
    private static final long serialVersionUID = 1L;

    protected Home<T> home;

    private String nombre;

    protected Boolean tengo;

    private WindowsEdition parent;
    

    public ABMEdition(T model,Home<T> home, WindowsEdition parent) {
        super(model);
        this.parent = parent;
        this.home = home;
        this.addActions();
    }

    protected void addActions() {

        this.getBotonAceptar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                tengo = true;
                ABMEdition.this.edicionAceptar();
                tengo = false;
            }
        });

//        this.getBotonModificar().addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                ABMEdition.this.edicionModificar();
//                setEditionModel(home.createExample());
//            }
//        });
        this.getBotonCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                ABMEdition.this.edicionCancelar();
                setEditionModel(home.createExample());
            }
        });

    }

    protected void edicionAceptar() {
        parent.edicionAceptar(this.getModel());
        home.saveOrUpdate(this.getModel());
        setEditionModel(home.createExample());
    }




    protected void setEditionModel(final T observable) {
        this.setModel(observable);
    }

//    protected void edicionModificar() {
//        T model = this.getModel();
//        if (model.getId() != null) {
//            home.update(model);
////            this.getBotonModificar().setEnabled(false);
//            parent.edicionModificar();
//        }
//    }

    protected void edicionCancelar() {
//        this.getBotonModificar().setEnabled(false);
        parent.edicionCancelar();
    }


    @Override
    public String toString() {
        return getNombre();
    }

    public List<T> getObjects() {
        return home.getAll();
    }


    public ABMEdition<T> setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
    public String getNombre() {
        return nombre;
    }

}
