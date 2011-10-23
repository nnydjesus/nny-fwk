package ar.edu.unq.tpi.ui.swing.components.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ar.edu.unq.tpi.base.common.Observable;
import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.search.Home;
import ar.edu.unq.tpi.ui.swing.components.AbstractBindingPanel;
import ar.edu.unq.tpi.ui.swing.components.ActionMethodListener;
import ar.edu.unq.tpi.ui.swing.components.Calculadora;
import ar.edu.unq.tpi.ui.swing.components.DefaultFormBuilder;
import ar.edu.unq.tpi.ui.swing.components.GeneralTable;
import ar.edu.unq.tpi.ui.swing.components.Generator;
import ar.edu.unq.tpi.ui.swing.components.JFormattedDecimalTextField;
import ar.edu.unq.tpi.ui.swing.components.ModelBinding;
import ar.edu.unq.tpi.ui.swing.components.TableSelection;
import ar.edu.unq.tpi.ui.swing.components.WindowsSearch;
import ar.edu.unq.tpi.ui.swing.components.autocomplete.AutoCompleteTextField;
import ar.edu.unq.tpi.ui.swing.components.autocomplete.LimeTextField;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.layout.FormLayout;

public class SearchPanel<T extends PersistentObject> extends
		AbstractBindingPanel<T> implements TableSelection<T> {

	private static final long serialVersionUID = 1L;

	public static final String UPDATE_TOTAL = "updateTotals";

	private JButton search;

	private JButton clear;

	private Home<T> home;

	private GeneralTable table;

	private List<T> result = new ArrayList<T>();

	private Map<String, AutoCompleteTextField> autocompletable = new HashMap<String, AutoCompleteTextField>();
	private Map<Pair<String, Boolean>, JTextField> totals = new HashMap<Pair<String, Boolean>, JTextField>();

	private WindowsSearch<T> parent;

	private DefaultFormBuilder panelTotals = new DefaultFormBuilder(
			new FormLayout("p, 2dlu, p:g"));
	private JPanel panelPrint;

	private JButton delete;
	private TopPanel topPanel;

	public SearchPanel(final T model, final Home<T> home,
			WindowsSearch<T> parent) {
		super(model);
		this.parent = parent;
		this.setHome(home);
		setTable(this.createTable(home.createExample()));
		this.addActions();
		panelPrint = new JPanel();
		this.add(panelPrint);
		configPanelPrint();
	}

	protected void configPanelPrint() {
		panelPrint.add(this.table);
		panelPrint.add(panelTotals.getPanel());

		GroupLayout layout = new GroupLayout(panelPrint);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(table).addComponent(panelTotals.getPanel()));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(table).addComponent(panelTotals.getPanel()));

		panelPrint.setLayout(layout);

	}

	@Override
	protected void construirPanelEdicion() {
		setTopPanel(new TopPanel());
		this.add(getTopPanel());
		super.construirPanelEdicion();
	}

	public void delete() {
		try {
			Object selected = table.getSelected();
			getHome().delete((T) selected);
			search();
			parent.deleteObject(selected);
		} catch (IndexOutOfBoundsException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Seleccionar un elemento de la tabla para que sea eliminado",
							"Error de seleccion", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected GeneralTable createTable(final T newInstance) {
		return Generator.GENERATE_TABLE(getResult(), newInstance.atributos(),
				this);
	}

	@Override
	protected void addButtons() {
		this.search = new JButton();
		this.search.setText("Buscar");
		this.clear = new JButton();
		this.clear.setText("Limpiar");
		this.delete = new JButton();
		this.delete.setText("Borrar");
		delete.addActionListener(new ActionMethodListener(this, "delete"));

		this.getPanelDeBotones().addButton(search);
		this.getPanelDeBotones().addRelatedGap();
		this.getPanelDeBotones().addButton(clear);
		this.getPanelDeBotones().addRelatedGap();
		this.getPanelDeBotones().addButton(delete);

	}

	protected void addActions() {
		this.search.addActionListener(new ActionMethodListener(this, "search"));
		this.clear.addActionListener(new ActionMethodListener(this, "clear"));
		getTopPanel().getBtImprimir().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				printTabla();
				// try {
				// PrintUtilities.printComponent(SearchPanel.this.panelPrint);
				// try {
				// table.getTabla().print();
				// } catch (PrinterException e) {
				// e.printStackTrace();
				// }
				// } catch (PrinterException e) {
				// e.printStackTrace();
				// }
			}
		});

		getTopPanel().getCalculadora().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				Calculadora.open();
			}
		});

		getTopPanel().getBtCerrar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				parent.close();
			}
		});
	}

	public void clear() {
		getResult().removeAll(getResult());
		this.setModel(parent.getDefaultModel());
		updateTotals();
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void search() {
		getResult().removeAll(getResult());
		getResult().addAll(getHome().searchByExample(this.getModel()));
		updateTotals();
		SwingUtilities.updateComponentTreeUI(this);
	}

	public AutoCompleteTextField addAutocompleteTextField(
			final String property, final String label) {
		ValueModel valueModel = getBuilder().getBeandAdapter().getValueModel(
				property);
		AutoCompleteTextField createTextField = new AutoCompleteTextField();
		Bindings.bind(createTextField, valueModel);
		this.addListDataAutoComplete(createTextField, property);
		this.getBuilder().append(label, createTextField);
		autocompletable.put(property, createTextField);
		return createTextField;
	}

	protected void addListDataAutoComplete(final AutoCompleteTextField text,
			final String property) {
		for (T object : getHome().getAll()) {
			text.addToDictionary(property, object);
		}
	}

	public void addObjectToSearch(T object) {
		for (String property : autocompletable.keySet()) {
			autocompletable.get(property).addToDictionary(property, object);
		}
	}

	public void addTextTotal(String property, String label, boolean iva) {
		JFormattedDecimalTextField text = new JFormattedDecimalTextField();
		text.setEditable(false);
		panelTotals.append(label, text);
		totals.put(new Pair<String, Boolean>(property, iva), text);
	}

	public void addTextTotal(String property, String label) {
		addTextTotal(property, label, false);
	}

	public void updateTotals() {
		Double value;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		for (Pair<String, Boolean> pair : totals.keySet()) {
			double total = 0.0;
			for (Observable objeject : ((ModelBinding) table.getTabla()
					.getModel()).getDatos()) {
				value = (Double) objeject.getProperty(pair.getObject1());
				if (value != null)
					total += value;
			}
			if (pair.getObject2()) {
				total += total * 21 / 100;
			}
			totals.get(pair).setText(decimalFormat.format(total) + "");
		}
	}

	@Override
	public void selectedTable(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			parent.editSelected((T) table.getSelected());
		}
	}

	public GeneralTable getTable() {
		return table;
	}

	protected void setTable(GeneralTable table) {
		this.table = table;
	}

	public void setSearch(JButton search) {
		this.search = search;
	}

	public JButton getSearch() {
		return search;
	}

	public void setClear(JButton clear) {
		this.clear = clear;
	}

	public JButton getClear() {
		return clear;
	}

	public void setTopPanel(TopPanel topPanel) {
		this.topPanel = topPanel;
	}

	public TopPanel getTopPanel() {
		return topPanel;
	}

	public void setHome(Home<T> home) {
		this.home = home;
	}

	public Home<T> getHome() {
		return home;
	}

	public List<T> getResult() {
		return result;
	}

	public void printTabla() {
		try {
			table.getTabla().print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}

	}

}
