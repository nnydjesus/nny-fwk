package ar.edu.unq.tpi.ui.swing.components;


/**
 * Clase para modificar el modelo de la tabla.
 * @author Ronny
 */
@SuppressWarnings("unchecked")
public class Control<T> {

	/** El modelo de la tabla */
	private Modelo modelo = null;
	private T home;

	/**
	 * Numero que nos servirá para construir Objetos distintas para la tabla
	 */
	private static int numero = 0;

	/**
	 * Constructor. Se le pasa el modelo
	 */
	public Control(Modelo tm, T home) {
		modelo = tm;
		this.home = home;
		
	};

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public static void setNumero(int numero) {
		Control.numero = numero;
	}


	public static int getNumero() {
		return numero;
	}

	public Modelo getModelo() {
		return modelo;
	}
	
	
    
    /** Elimina la fila index del modelo */
    public void borraFila (int index)
    {
        if (getModelo().getRowCount() > 0)
           this.getModelo().removeRow (index);
    }
    
    public void delete(Object obj){
    	this.getModelo().delete(obj);
    	this.update((Iterable<?>) modelo.getDatos().clone());
    }
    
    public void update(Iterable<?> data) {
    	modelo.removeAllRow();
    	for (Object object : data) {
			modelo.addRow(object);
		}
	}

	public void setHome(T home) {
		this.home = home;
	}

	public T getHome() {
		return home;
	}


}
