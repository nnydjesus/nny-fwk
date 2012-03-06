package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.jvnet.substance.utils.border.SubstanceBorder;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;

public class Generator {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E>GeneralTable<E> GENERATE_TABLE(List<E> tablaList, String[] fields, final TableSelection parent) {
		SelectionInList<E> selectionInList = new SelectionInList(tablaList);		
		ModelBinding tablaModel = new ModelBinding(selectionInList, fields);		
		final TableAdapter table = new TableAdapter();
		table.setFont(parent.getFont());
		parent.configureTableHeader(table.getTableHeader());
		table.setBorder(new SubstanceBorder(new Insets(20, 20, 20, 20)));
		
		
//		table.setAutoCreateRowSorter(true);
		table.setSelectionModel(new SingleListSelectionAdapter(
				selectionInList.getSelectionIndexHolder()));

		final GeneralTable<E> generalTable = new GeneralTable<E>(tablaModel, table);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(parent != null){
					parent.selectedTable(e);
				}
			}
		});
		
		return generalTable;
	}
	


}
