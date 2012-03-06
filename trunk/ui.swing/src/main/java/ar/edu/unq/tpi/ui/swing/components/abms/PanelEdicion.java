package ar.edu.unq.tpi.ui.swing.components.abms;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.common.transaction.TaskOwner;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.base.utils.Path;
import ar.edu.unq.tpi.ui.swing.components.AbstractBindingPanel;

public class PanelEdicion<T extends IdentificablePersistentObject> extends AbstractBindingPanel<T> implements TaskOwner{
    private static final long serialVersionUID = -4916057975139862174L;

    private JButton botonAgregar;

    private JButton botonCancelar;

    public PanelEdicion(final T model) {
        super(model);
        this.setTitulo(model.getIdentificableName());

    }
    
    @Override
    protected void addButtons() {
        createButtons();

        configureButtons();
    }
    
	public void cleanButtons() {
		this.getPanelDeBotones().getPanel().removeAll();
	}

	public void configureButtons() {
		this.getPanelDeBotones().addGlue();
        this.getPanelDeBotones().addRelatedGap();
        this.getPanelDeBotones().addButton(this.getBotonAceptar());
        this.getPanelDeBotones().addRelatedGap();
        this.getPanelDeBotones().addRelatedGap();
        this.getPanelDeBotones().addButton(this.getBotonCancelar());
	}

	private void createButtons() {
		this.setBotonAgregar(new JButton(new ImageIcon(Path.path() + "Images/confirmar.png")));
        this.getBotonAceptar().setText("Confirmar");
        this.setBotonCancelar(new JButton(new ImageIcon(Path.path() + "Images/cancel.png")));
        this.getBotonCancelar().setText("Cancelar");
	}
    
    @Override
    public void setFont(Font font) {
    	super.setFont(font);
    	if(botonAgregar != null){
    		botonAgregar.setFont(font);
    		botonCancelar.setFont(font);
    	}
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

	@Override
	public boolean isTransactional() {
		return true;
	}

	private ObjectTransaction transaction;
	@Override
	public void setTransaction(ObjectTransaction transaction) {
		this.transaction = transaction; 
	}

	@Override
	public ObjectTransaction getTransaction() {
		return transaction;
	}

}
