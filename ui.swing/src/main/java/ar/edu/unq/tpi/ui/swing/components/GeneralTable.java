package ar.edu.unq.tpi.ui.swing.components;

import java.awt.event.MouseListener;
import java.awt.print.PrinterException;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import ar.edu.unq.tpi.util.commons.exeption.UserException;

/**
 * Panel con toda la parte visual del ejemplo. Crea un JScrollPane con el JTable
 * en su interior
 * 
 * @author Ronny
 */
public class GeneralTable extends GeneralPanel {

    private static final long serialVersionUID = 1L;

    /** Modelo de la tabla */
    private ModelBinding modelo = null;

    private JScrollPane scroll = new JScrollPane();

    private JTable tabla;

    /**
     * Constructor que recibe el modelo de la tabla y el control. Guarda ambos y
     * llama al metodo construyeVentana() que se encarga de crear los
     * componentes.
     */
    public GeneralTable(final ModelBinding modelo){
        this(modelo, new JTable());
    }

    public GeneralTable(final ModelBinding modelo, final JTable tabla) {
    	super();
    	this.tabla = tabla;
    	this.setModelo(modelo);
        this.construyeVentana();

    }

    /**
     * Crea los componentes de este panel. Un JScrollPane, el JTable que va
     * dentro y dos JButton para a�adir y quitar elementos del JTable.
     */
    private void construyeVentana() {
        // Se crea el JScrollPane, el JTable y se pone la cabecera...

    	
        scroll.setOpaque(false);
        tabla.setModel((TableModel) getModelo());
//         tabla.setDefaultRenderer(Boolean.class,new MyRenderer());
        scroll.setViewportView(tabla);
        scroll.setColumnHeaderView(tabla.getTableHeader());

        // ... y se a�ade todo al panel
        
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        autoAjuste();
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        		layout.createSequentialGroup().addComponent(scroll));
        layout.setVerticalGroup(
        		layout.createParallelGroup().addComponent(scroll));
        this.setLayout(layout);
    }

    public JScrollPane getScroll() {
        return scroll;
    }


    public JTable getTabla() {
        return tabla;
    }

    public Object getSelected() {
        return getModelo().getSelected(tabla.getSelectedRow());
    }

    public void addtableListener(final MouseListener actionListener) {
        tabla.addMouseListener(actionListener);
    }

    public void setModelo(ModelBinding modelo) {
        this.modelo = modelo;
    }

    public ModelBinding getModelo() {
        return modelo;
    }

	public void print() {
		try {
			tabla.print();
		} catch (PrinterException e) {
			throw new UserException(e);
		}
	}
	
	public void autoAjuste(){
		for (int c = 0; c < tabla.getColumnCount(); c++) {
			DefaultTableColumnModel colModel = (DefaultTableColumnModel) tabla
					.getColumnModel();
			TableColumn col = colModel.getColumn(c);
			int length = col.getHeaderValue().toString().length();
			int maxWithByColum = maxWithByColum(c, length);
			col.setMaxWidth(maxWithByColum+10);
			col.setPreferredWidth(maxWithByColum);
			col.setMinWidth(maxWithByColum-10);
		}
	}
	
	public int maxWithByColum(int column, int length){
		int max=length;
		for (int i = 0; i < tabla.getRowCount(); i++) {
			Object valueAt = modelo.getValueAt(i, column);
			if(valueAt != null && valueAt.toString().length() > max){
				max = valueAt.toString().length();
			}
		}
		return max*11;
	}
    

//    static class MyRenderer extends BooleanRenderer {
//        //
//        @Override
//        public Component getTableCellRendererComponent(final JTable table,  final Object value, boolean isSelected,
//                boolean hasFocus, int row, int column) {
//            final JCheckBox check = (JCheckBox) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            check.addChangeListener(new ChangeListener() {
//                
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                   table.getAlignmentX();       
//                }
//            });
//            return check;
//        }
//    }

}
