package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Font;

import javax.swing.table.JTableHeader;

public interface WindowsSearch<T> {
    
    public void editSelected(T selected);

    public void deleteObject(Object selected);

	public void close();

	public T getDefaultModel();
	
	public Font getFont();

	public void conconfigureTableHeader(JTableHeader header);

}
