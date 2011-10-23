package ar.edu.unq.tpi.ui.swing.components.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import ar.edu.unq.tpi.base.common.Item;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject;
import ar.edu.unq.tpi.ui.swing.components.ActionMethodListener;
import ar.edu.unq.tpi.ui.swing.components.GeneralTable;
import ar.edu.unq.tpi.ui.swing.components.Generator;
import ar.edu.unq.tpi.ui.swing.components.WindowsSearch;
import ar.edu.unq.tpi.ui.swing.components.abms.ABMFrame;
import ar.edu.unq.tpi.ui.swing.components.abms.WindowsEdition;
import ar.edu.unq.tpi.util.common.ReflectionUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class GeneralFrame<T extends IdentificablePersistentObject> extends JFrame implements
                                            Item, WindowsEdition,WindowsSearch {

    private static final long serialVersionUID = 1L;

    private TopPanel topPanel;

    protected GeneralTable table;

    private JTabbedPane panel = new JTabbedPane();

    protected Class<ABMFrame<T>> ambClass;

    protected Home<T> home;

    private String nombre;

//    protected MyJComboBox<T> comboBox;

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

        this.createComboBox();
//        comboBox.addDefaultValue(home.createExample());
        this.setTopPanel(new TopPanel());
        this.table = this.createTable(newInstance);
        this.createSearchForm(getSearch());
        this.add(getTopPanel());
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
                .addComponent(getTopPanel(), 0, 780, 780)
                .addGroup(Alignment.CENTER,
                        layout.createSequentialGroup().addComponent(panel, -1, panel.getWidth(), panel.getWidth())));
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(getTopPanel(), 0, 100, 100)
                .addGroup(layout.createParallelGroup().addComponent(panel, -1, panel.getHeight(), panel.getHeight())));

        this.getContentPane().setLayout(layout);

    }

    protected GeneralTable createTable(final T newInstance) {
        return Generator.GENERATE_TABLE(home.getAll(), newInstance.atributos(), null);
    }

    protected void createComboBox() {
//        this.comboBox = new MyJComboBox(home.getAll());
    }

    protected Home<T> createHome() {
        return new Home<T>(getEntityClazz());
    }

    protected void addPanels(final JTabbedPane panel) {
         panel.addTab("Tabla", null, table, "tabla");
        panel.addTab("Filtro", null, getSearch(), "Filtro");
        this.add(panel);
    }

    protected void addActions() {
        getTopPanel().getBtImprimir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                getSearch().printTabla();;
            }
        });

        getTopPanel().getBtCerrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                System.exit(DO_NOTHING_ON_CLOSE);
            }
        });

        getSearch().getTable().addtableListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    GeneralFrame.this.tableListener(e);
                }

            }
        });

//        comboBox.addActionListener(new ActionMethodListener(this, "comboboxListener"));
    }

    public void edicionAceptar(Object object) {
        update();
    }

    public void comboboxListener() {
//        this.setModel((T) comboBox.getSelectedItem());
    }

    protected void tableListener(final MouseEvent e) {
        this.setModel((T) getSearch().getTable().getSelected());
    }

    public void setModel(final T observable) {
//        ambClass.setModel(observable);
        update();
    }
//
//    protected void setEditionModel(final T observable) {
//        ambClass.setModel(observable);
//    }

    public void edicionModificar() {
        update();
    }

    public void edicionCancelar() {
    }
    
    @Override
    public void editSelected(Object selected) {
        ABMFrame<T> edition = ReflectionUtils.instanciate(ambClass, selected, true);
        edition.onSave(new ActionMethodListener(search, SearchPanel.UPDATE_TOTAL));
        edition.setVisible(true);
    }

    @Override
    public void mostrar() {
        home.refresh();
        this.setVisible(true);
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
    public void close() {
    	this.setVisible(false);
    	this.dispose();   
//    	System.exit(JFrame.ABORT);
    }
    
    @Override
    public Object getDefaultModel() {
    	return home.createExample();
    }

	public void setTopPanel(TopPanel topPanel) {
		this.topPanel = topPanel;
	}

	public TopPanel getTopPanel() {
		return topPanel;
	}

	public void setEntityClazz(Class<T> entityClazz) {
		this.entityClazz = entityClazz;
	}

	public Class<T> getEntityClazz() {
		return entityClazz;
	}

}
