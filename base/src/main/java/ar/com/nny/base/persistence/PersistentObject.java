package ar.com.nny.base.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import ar.com.nny.base.common.Observable;

/**
 * Persistent objects are BusinessObjects that persist in the data store. They
 * add the state version, used for optimistic locking.
 * <p/>
 * 
 */
@MappedSuperclass
public abstract class PersistentObject extends Observable {
    private static final long serialVersionUID = 1L;

    @Version
    @SerializationStrategy(access = Through.READ_ONLY_FIELD)
    @GeneratedValue
    private Long stateVersion;

    // @IgnoreProperty
    public Long getStateVersion() {
        return stateVersion;
    }

    public boolean isPersisted() {
        return stateVersion != null;
    }

    // /**
    // * Checks if an optimistic lock collision will be detected
    // * against certain version number.
    // */
    // @Override
    // public void checkVersionAgainst(final Number version) {
    // if ( version == null || this.stateVersion == null ) {
    // return;
    // }
    //
    // if ( this.stateVersion > version.longValue() ) {
    // throw new RuntimeException("Optimistic lock collision detected on " +
    // this + ". " +
    // "Previous version [" + this.stateVersion + "] - New Version [" + version
    // + "]");
    // }
    // }
}
