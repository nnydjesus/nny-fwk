package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.table.JTableHeader;

public interface TableSelection<T> {
	
    public void selectedTable(MouseEvent event);

    public Font getFont();
    
    public void configureTableHeader(JTableHeader header);

}
