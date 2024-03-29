package ar.edu.unq.tpi.ui.swing.components.abms;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ar.edu.unq.tpi.base.common.Item;
import ar.edu.unq.tpi.base.common.ItemComposite;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.ui.swing.components.ActionMethodListener;
import ar.edu.unq.tpi.ui.swing.components.CenteredJFrame;

public abstract class ABMFrame<T extends IdentificablePersistentObject> extends CenteredJFrame implements WindowsEdition, Item  {

    private static final long serialVersionUID = 1L;

    private ABMEdition<T> edicion;

    private String nombre;
    
    private ItemComposite item;
    
    private Window parent;
    
    private ActionMethodListener onSaveListener;
    private ActionMethodListener onCloseListener;
    private Class<T> entityclass;
	private boolean isEditing;


    public ABMFrame(T model,  Window parent,Boolean isEditing) {
        this.parent = parent;
        this.entityclass = (Class<T>) model.getClass();
        this.setNombre(model.getIdentificableName());
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setEditing(isEditing);
        edicion = new ABMEdition<T>( model, getHome(), this);

        this.createForm(edicion);
        this.addPanels(edicion);
        setEditionModel(model);
        this.addActions();
        this.setVisible(false);
    }

    protected void addActions() {
    	this.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent arg0) {
        		edicion.rollback();
            }
          });
        
    }

    protected Home<T> getHome() {
        return  new Home<T>(entityclass);
    }

    protected void addPanels(final JPanel panel) {
        this.add(panel);
    }
    
    @Override
    public void setFont(Font f) {
    	if(edicion != null){
    		edicion.setFont(f);
    	}
    	super.setFont(f);
    }



    public void setModel(final T observable, ItemComposite item) {
        this.setModel(observable);
        this.item = item;
    }
    public void setModel(final T observable) {
        setEditionModel(observable);
        update();
    }

    protected void setEditionModel(final T observable) {
        edicion.setEditionModel(observable);
    }

    public void edicionCancelar() {
    	if(onCloseListener!= null)
            onCloseListener.execute();
    	close();
    }
    
    public void edicionAceptar(Object object) {
        update();
        if(onSaveListener!= null)
            onSaveListener.execute();
        edicionCancelar();   
    }


    @Override
    public String toString() {
        return getNombre();
    }

    protected abstract void createForm(ABMEdition<T> edicion2);

    public List<T> getObjects() {
        return getHome().getAll();
    }
    
    public void update(){
        SwingUtilities.updateComponentTreeUI(this);
    }

    public ABMEdition<T> getEdicion() {
        return edicion;
    }

    public ABMFrame<T> setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public Window getParent() {
        return parent;
    }

    public void onSave(ActionMethodListener actionMethodListener) {
        onSaveListener = actionMethodListener;
    }
    
    public void onClose(ActionMethodListener actionMethodListener) {
        onCloseListener = actionMethodListener;
    }

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEntityclass(Class<T> entityclass) {
		this.entityclass = entityclass;
	}

	public Class<T> getEntityclass() {
		return entityclass;
	}
	
	public T getModel(){
		return this.edicion.getModel();
	}
	
}
