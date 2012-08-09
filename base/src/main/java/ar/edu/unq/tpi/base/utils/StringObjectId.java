package ar.edu.unq.tpi.base.utils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import ar.edu.unq.tpi.base.persistence.SerializationStrategy;
import ar.edu.unq.tpi.base.persistence.Through;
import ar.edu.unq.tpi.util.common.ObjectId;

/**
 * Instances of this class are used as a unique identifier to BusinessObjects.
 *
 */
@Embeddable
public class StringObjectId implements Serializable, ObjectId {

	@SerializationStrategy(access=Through.TRANSIENT)
	private static final long serialVersionUID = 1L;

	@Column(length = 50)
    private String value;


    public StringObjectId() {
    }


    public StringObjectId(String value) {
        this.setValue(value);
    }


    public void setValue(String value) {
        this.value = value;
    }


    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof StringObjectId) ) {
            return false;
        }
        return ((StringObjectId) obj).value.equals(this.value);
    }
    
    
    @Override
    public int hashCode() {
    	return this.value.hashCode();
    }


    @Override
    public String toString() {
        return this.value;
    }

}
