package ar.edu.unq.tpi.base.bean;

import ar.edu.unq.tpi.base.common.Observable;


public class GananciaPerType extends Observable{
	
	private  Double total;
	private  Double ganancia;
	private ProductType type;
	
	public static final String[] ATRIBUTES = new String[]{"type", "ganancia", "total"};
	
	public GananciaPerType(Double total, Double ganancia,  ProductType type) {
		this.total = total;
		this.ganancia = ganancia;
		this.type = type;
	}

	public GananciaPerType(Object total, Object ganancia, Object type) {
		this((Double)total, (Double)ganancia, (ProductType)type);
	}

	
	public GananciaPerType() {
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getGanancia() {
		return ganancia;
	}

	public void setGanancia(Double ganancia) {
		this.ganancia = ganancia;
	}
	
	

}
