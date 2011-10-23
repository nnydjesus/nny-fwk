package ar.edu.unq.tpi.ui.swing.components;

public interface WindowsSearch<T> {
    
    public void editSelected(T selected);

    public void deleteObject(Object selected);

	public void close();

	public T getDefaultModel();

}
