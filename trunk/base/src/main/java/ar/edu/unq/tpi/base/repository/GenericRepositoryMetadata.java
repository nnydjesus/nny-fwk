package ar.edu.unq.tpi.base.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unq.tpi.base.persistence.PersistentObject;
import ar.edu.unq.tpi.base.persistence.annotations.OrderType;
import ar.edu.unq.tpi.base.persistence.FieldQueryCache;
import ar.edu.unq.tpi.base.persistence.query.GetAllCacheable;
import ar.edu.unq.tpi.base.persistence.query.NaturalOrder;
import ar.edu.unq.tpi.util.common.ReflectionUtils;
import ar.edu.unq.tpi.util.common.predicate.AnnotationFieldPredicate;



/**
 * This class encapsulate the metadata retrieval from the repository's persistent class.
 *
 */
@SuppressWarnings("unchecked")
public class GenericRepositoryMetadata<T extends PersistentObject> {

	private final FWKRepository repository;

	private Map<String, OrderType> naturalOrderFields;
	private Map<String, String> cachedFields;
	private String[] storedFields;

	private Boolean getAllCacheable = null;


	public GenericRepositoryMetadata(final FWKRepository<T> repository) {
		this.repository = repository;
	}


	private Class getPersistentClass() {
		return this.repository.getEntityType();
	}


	/**
	 * Returns all the fields that should be stored into Lucene indexes, if exists any.
	 */
	protected String[] getStoredFields() {
		if ( this.storedFields != null ) {
			return this.storedFields;
		}

		final List<String> result = getStoredFieldsRecursively(this.getPersistentClass());
		final String[] _value = new String[result.size()];
		int _i = 0;
		for ( final String _field : result ) {
			_value[_i] = _field;
			_i++;
		}

		this.storedFields = _value;
		return _value;
	}


	private List<String> getStoredFieldsRecursively(final Class persistentClass) {
		final List<String> ret = new ArrayList<String>();

		return ret;
	}


	/**
	 * Returns all the fields that are marked with the @FieldQueryCache annotation into the given persistent class
	 */
	protected Map<String, String> getCachedFields() {
		if ( this.cachedFields != null ) {
			return this.cachedFields;
		}

		final List<Field> allFields = ReflectionUtils.getAllFields(this.getPersistentClass(), new AnnotationFieldPredicate(FieldQueryCache.class));
		final Map<String, String> value = new HashMap<String, String>();
		for ( final Field field : allFields ) {
			final FieldQueryCache queryCache = field.getAnnotation(FieldQueryCache.class);
			value.put(field.getName(), queryCache.region());
		}
		this.cachedFields = value;
		return value;
	}


	/**
	 * Returns all the fields that are marked with the @NaturalOrder annotation into the given persistent class
	 */
	protected Map<String, OrderType> getNaturalOrderFields() {
		if ( this.naturalOrderFields != null ) {
			return this.naturalOrderFields;
		}

		final List<Field> naturalOrderFields = ReflectionUtils.getAllFields(this.getPersistentClass(), new AnnotationFieldPredicate(NaturalOrder.class));
		final Map<String, OrderType> value = new HashMap<String, OrderType>();
		for ( final Field field : naturalOrderFields ) {
			final NaturalOrder orderAnnotation = field.getAnnotation(NaturalOrder.class);
			value.put(field.getName(), orderAnnotation.orderType());
		}

		this.naturalOrderFields = value;
		return value;
	}


	/**
	 * @return true if the annotation @GetAllCacheable is present in the persistent class.
	 */
	protected boolean isGetAllOperationCached() {
		if ( this.getAllCacheable == null ) {
			this.getAllCacheable = this.getPersistentClass().isAnnotationPresent(GetAllCacheable.class);
		}
		return this.getAllCacheable;
	}
}

