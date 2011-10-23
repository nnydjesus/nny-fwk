package ar.edu.unq.tpi.util.common;

import java.util.List;

public interface Repositoy<T> {


	public abstract Class<T> getEntityType();


	public void delete(final T pgo);

	public void update(final T pgo);

	public void save(final T pgo);

	public T getById(final String id);

	public List<T> getByIds(final List<String> _ids);

	public List<T> getAll();

	public List<T> find(final String fieldName, final Object fieldValue);

}
