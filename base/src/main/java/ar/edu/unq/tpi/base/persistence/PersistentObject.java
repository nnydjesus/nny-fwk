package ar.edu.unq.tpi.base.persistence;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import ar.edu.unq.tpi.base.common.Observable;
import ar.edu.unq.tpi.base.utils.UUIDObjectId;

/**
 * Persistent objects are BusinessObjects that persist in the data store. They
 * add the state version, used for optimistic locking.
 * <p/>
 * 
 */
@MappedSuperclass
public abstract class PersistentObject extends Observable{
	private static final long serialVersionUID = 1L;

	@Version
	@SerializationStrategy(access = Through.ACCESSOR)
	@GeneratedValue
	private Long stateVersion;

	@Id
	@Embedded
	private UUIDObjectId id;

	public PersistentObject() {
		this.id = new UUIDObjectId();
	}


	public Long getStateVersion() {
		return stateVersion;
	}

	public void setStateVersion(Long stateVersion) {
		this.stateVersion = stateVersion;
	}
	
	public boolean isPersisted() {
		return getStateVersion() != null;
	}

	public void setId(UUIDObjectId id) {
		this.id = id;
	}

	public UUIDObjectId getId() {
		return id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistentObject other = (PersistentObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



}
