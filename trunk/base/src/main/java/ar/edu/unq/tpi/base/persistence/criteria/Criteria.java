package ar.edu.unq.tpi.base.persistence.criteria;


public interface Criteria<T> {

	public Criteria<T> like(String property, String value);

	public Criteria<T> equals(String property, Object value);

	public Criteria<T> distint(String property, Object value);

	public Criteria<T> greater(String property, Object value);

	public Criteria<T> less(String property, String value);

	public Criteria<T> lessOrEqual(String property, String value);

	public Criteria<T> greaterOrEqual(String property, Object value);

	public Criteria<T> and(Criteria<T> cri1, Criteria<T> cri2);

	public Criteria<T> or(Criteria<T> cri1, Criteria<T> cri2);
	
	public T build();

}
