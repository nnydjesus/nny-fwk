package ar.edu.unq.tpi.ui.swing.components.search;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

import ar.edu.unq.tpi.base.common.Item;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.ui.swing.components.ActionMethodListener;
import ar.edu.unq.tpi.ui.swing.components.CenteredJFrame;
import ar.edu.unq.tpi.ui.swing.components.GeneralTable;
import ar.edu.unq.tpi.ui.swing.components.WindowsSearch;
import ar.edu.unq.tpi.ui.swing.components.abms.ABMFrame;
import ar.edu.unq.tpi.ui.swing.components.abms.WindowsEdition;
import ar.edu.unq.tpi.util.common.ReflectionUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class GeneralFrame<T extends IdentificablePersistentObject> extends CenteredJFrame implements
                                            Item, WindowsEdition,WindowsSearch {

    private static final long serialVersionUID = 1L;


    protected GeneralTable table;

    private JTabbedPane panel = new JTabbedPane();

    protected Class<ABMFrame<T>> ambClass;

    protected Home<T> home;

    private String nombre;


    protected Boolean tengo;

    private SearchPanel<T> search;

    private Class<T> entityClazz;

    public GeneralFrame(final String name, final Class clase) {
        this.setEntityClazz(clase);
        this.nombre = name;
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        T newInstance;
        home = this.createHome();
        

        newInstance = (T) ReflectionUtils.instanciate(clase);
        ambClass = abmClass();
        search = this.createSearchPanel(newInstance); 

        this.createSearchForm(getSearch());
        this.addPanels(panel);
        this.addActions();
        this.setSize(1024, 780);
        this.setVisible(false);
    }

    public abstract Class abmClass();

    protected SearchPanel<T> createSearchPanel(final T newInstance) {
        return new SearchPanel<T>( newInstance, home, this);
    }

    protected void setLayout() {
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(layout
                .createParallelGroup()
                .addGroup(Alignment.CENTER,
                        layout.createSequentialGroup().addComponent(panel, -1, panel.getWidth(), panel.getWidth())));
        layout.setVerticalGroup(layout.createParallelGroup().addComponent(panel, -1, panel.getHeight(), panel.getHeight()));

        this.getContentPane().setLayout(layout);

    }

    protected Home<T> createHome() {
        return new Home<T>(getEntityClazz());
    }

    protected void addPanels(final JTabbedPane panel) {
        this.add(getSearch());
    }

    protected void addActions() {

        getSearch().getTable().addtableListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    GeneralFrame.this.tableListener(e);
                }

            }
        });

    }

    public void edicionAceptar(Object object) {
        update();
    }

    public void comboboxListener() {
    }

    protected void tableListener(final MouseEvent e) {
        this.setModel((T) getSearch().getTable().getSelected());
    }

    public void setModel(final T observable) {
//        ambClass.setModel(observable);
        update();
    }

    public void edicionModificar() {
        update();
    }

    public void edicionCancelar() {
    }
    
    
    private ABMFrame<T> editionOpened;
    @Override
    public void editSelected(Object selected) {
    	if(editionOpened == null){
    		editionOpened = ReflectionUtils.instanciate(ambClass, selected, true);
    		editionOpened.onSave(new ActionMethodListener(search, SearchPanel.SEARCH));
    		editionOpened.onClose(new ActionMethodListener(this, "closeEdition"));
    		editionOpened.open();
    	}
    }
    
    public void closeEdition(){
    	editionOpened = null;
    }

    @Override
    public void open() {
        home.refresh();
        super.open();
    }

    @Override
    public String toString() {
        return nombre;
    }

    protected void createSearchForm(final SearchPanel<T> panel) {
    }

    public List<T> getObjects() {
        return home.getAll();
    }
    
    public void update(){
        SwingUtilities.updateComponentTreeUI(this);
    }

    public SearchPanel<T> getSearch() {
        return search;
    }
    
    @Override
    public void deleteObject(Object selected) {
    }
    
    public void selectedTable(T selected, MouseEvent event) {
    	if(event.getClickCount()>=2){
    		editSelected(selected);
    	}
    };
    
    @Override
    public void conconfigureTableHeader(JTableHeader header) {
    }
    
    
    public T getModel(){
    	return this.search.getModel();
    }
    
    
    @Override
    public Object getDefaultModel() {
    	return home.createExample();
    }

	public void setTopPanel(TopPanel topPanel) {
		this.search.setTopPanel(topPanel) ;
	}

	public void setEntityClazz(Class<T> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public Class<T> getEntityClazz() {
		return entityClazz;
	}

}
