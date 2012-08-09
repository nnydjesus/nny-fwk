package ar.edu.unq.tpi.ui.swing.components.abms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;

import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public class ABMEdition<T extends IdentificablePersistentObject> extends PanelEdicion<T> {
    private static final long serialVersionUID = 1L;

    protected Home<T> home;

    private String nombre;

    protected Boolean tengo;

    private WindowsEdition parent;
    
    private boolean transactional = true;
    

    public ABMEdition(T model,Home<T> home, WindowsEdition parent) {
        super(model);
        ObjectTransactionManager.begin(this);
        this.parent = parent;
        this.home = home;
        this.setFont(parent.getFont());
        this.addActions();
    }

    protected void addActions() {

        this.getBotonAceptar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
            	onAccept();
            }
        });

        this.getBotonCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
            	onCancel();
            }
        });
        

    }

    protected void edicionAceptar() {
        parent.edicionAceptar(this.getModel());
    }




    protected void setEditionModel(final T observable) {
        this.setModel(observable);
    }


    protected void edicionCancelar() {
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

	public void commit() {
		ObjectTransactionManager.commit(ABMEdition.this);
	}

	public void rollback() {
		ObjectTransactionManager.rollback(ABMEdition.this);
	}

	public void onAccept() {
		if(isTransactional()){
			commit();
		}
		tengo = true;
		ABMEdition.this.edicionAceptar();
		tengo = false;
	}

	public void onCancel() {
		if(isTransactional()){
			rollback();
		}
		ABMEdition.this.edicionCancelar();
		setEditionModel(home.createExample());
	}

	public boolean isTransactional() {
		return transactional;
	}

	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}

}
