package ar.edu.unq.tpi.base.persistence.criteria;

public interface Order<T>{
	
	public void asc(String property);
	
	public void desc(String property);
	
	public T getSpecificOrder();

}
