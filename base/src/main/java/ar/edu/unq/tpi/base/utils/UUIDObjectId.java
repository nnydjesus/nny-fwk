package ar.edu.unq.tpi.base.utils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

import ar.edu.unq.tpi.base.persistence.SerializationStrategy;
import ar.edu.unq.tpi.base.persistence.Through;
import ar.edu.unq.tpi.commons.configuration.jfig.FrameworkConfiguration;
import ar.edu.unq.tpi.util.common.ObjectId;

/**
 * Instances of this class are used as a unique identifier to BusinessObjects.
 *
 */
@Embeddable
public class UUIDObjectId implements Serializable, ObjectId {

	@SerializationStrategy(access=Through.TRANSIENT)
	private static final long serialVersionUID = 1L;

	@SerializationStrategy(access = Through.TRANSIENT)
    private static EthernetAddress MAC;

    @Column(length = 36)
    private String id;

    
    /**
     * Creates a new object id. The UUID value is assigned on construction time.
     */
    public UUIDObjectId() {
        if ( MAC == null ) {
            MAC = new EthernetAddress(FrameworkConfiguration.getServerMAC());
        }

        UUID uuid = UUIDGenerator.getInstance().generateTimeBasedUUID(MAC);
        this.id = uuid.toString();
    }


    /**
     * Used by the converter
     */
    public UUIDObjectId(String id) {
        this.id = id;
    }


    public String toString() {
        return id;
    }


    public int hashCode() {
        return id.hashCode();
    }


    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof UUIDObjectId) ) {
            return false;
        }
        return ((UUIDObjectId) obj).id.equals(this.id);
    }


    public void setValue(String value) {
        this.id = value;
    }

}
