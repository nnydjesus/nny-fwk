package ar.com.nny.base.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.nny.base.persistence.AnnotationFieldPredicate;
import ar.com.nny.base.persistence.FieldQueryCache;
import ar.com.nny.base.persistence.GetAllCacheable;
import ar.com.nny.base.persistence.NaturalOrder;
import ar.com.nny.base.persistence.PersistentObject;
import ar.com.nny.base.persistence.annotations.OrderType;
import ar.com.nny.base.utils.ReflectionUtils;



/**
 * This class encapsulate the metadata retrieval from the dao's persistent class.
 *
 */
@SuppressWarnings("unchecked")
public class GenericDAOMetadata<T extends PersistentObject> {

	private final GenericFlexyDAO dao;

	private Map<String, OrderType> naturalOrderFields;
	private Map<String, String> cachedFields;
	private String[] storedFields;

	private Boolean getAllCacheable = null;


	public GenericDAOMetadata(final GenericFlexyDAO<T> dao) {
		this.dao = dao;
	}


	private Class getPersistentClass() {
		return this.dao.getPersistentClass();
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

//		final List<Field> indexedFields = ReflectionUtils.getAllFields(persistentClass, new AnnotationFieldPredicate(org.hibernate.search.annotations.Field.class, DocumentId.class));
//		final List<Field> storedFields = (List<Field>) CollectionUtils.select(indexedFields, new Predicate<java.lang.reflect.Field>() {
//			public boolean evaluate(final Field _field) {
//				return _field.isAnnotationPresent(DocumentId.class) || !Store.NO.equals(_field.getAnnotation(org.hibernate.search.annotations.Field.class).store());
//			}
//		});
//
//		for ( final Field _field : storedFields ) {
//			ret.add(_field.getName());
//		}
//
//		//Agregar los de los @IndexedEmbedded
//		final List<Field> embeddedIndexedFields = ReflectionUtils.getAllFields(persistentClass, new AnnotationFieldPredicate(org.hibernate.search.annotations.IndexedEmbedded.class));
//		for (final Field _field : embeddedIndexedFields) {
//			final IndexedEmbedded annotation = _field.getAnnotation(IndexedEmbedded.class);
//			final Class targetElement = annotation.targetElement();
//			final Class innerIndexed = targetElement.equals(void.class) ? _field.getType() : targetElement;
//
//			final List<String> storedFieldsRecursively = getStoredFieldsRecursively(innerIndexed);
//			for (final String string : storedFieldsRecursively) {
//				ret.add(_field.getName() + annotation.prefix() + string);
//			}
//		}

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

