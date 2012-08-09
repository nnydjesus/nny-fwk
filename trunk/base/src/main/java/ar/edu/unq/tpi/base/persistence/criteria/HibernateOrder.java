package ar.edu.unq.tpi.base.persistence.criteria;


public class HibernateOrder implements Order<org.hibernate.criterion.Order>{
	
	private org.hibernate.criterion.Order order;

	@Override
	public void asc(String property) {
		this.order =  org.hibernate.criterion.Order.asc(property);
	}

	@Override
	public void desc(String property) {
		this.order =  org.hibernate.criterion.Order.desc(property);
	}

	@Override
	public org.hibernate.criterion.Order getSpecificOrder() {
		return this.order;
	}
	

}
