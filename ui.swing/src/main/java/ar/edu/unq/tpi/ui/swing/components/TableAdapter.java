package ar.edu.unq.tpi.ui.swing.components;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

public class TableAdapter<T> extends JTable {
	private static final long serialVersionUID = 1L;
	
	
	private WindowsSearch<T> owner;

	@Override
	public void columnSelectionChanged(ListSelectionEvent e) {
		super.columnSelectionChanged(e);
		// JOptionPane.showConfirmDialog(this, "afsdfasdf");
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// JOptionPane.showConfirmDialog(this, e.getColumn());
		super.tableChanged(e);
	}

	public void setOwner(WindowsSearch<T> owner) {
		this.owner = owner;
	}

	public WindowsSearch<T> getOwner() {
		return owner;
	}
}
