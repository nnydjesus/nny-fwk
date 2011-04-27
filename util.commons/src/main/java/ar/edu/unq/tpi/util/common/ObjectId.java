package ar.edu.unq.tpi.util.common;

import java.io.Serializable;

public interface ObjectId extends Serializable{
    public String toString();


    /**
     * This must be implemented and used only for deserialization.
     */
    public void setValue(String value);

}
