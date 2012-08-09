package ar.edu.unq.tpi.base.utils;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Index;

import ar.edu.unq.tpi.base.persistence.PersistentObject;

@MappedSuperclass
public abstract class IdentificablePersistentObject extends PersistentObject{
	private static final long serialVersionUID = 1L;
	
	@Basic
	@Index(name="INDX_key")
	private String valueKey="";

	
    public String mostrar() {
        return this.getKey();
    }

    @Override
    public String toString() {
        return this.mostrar();
    }

	protected void setKey(String key) {
		this.valueKey = key;
	}

	public String getKey() {
		return valueKey;
	}
	
    public  String getIdentificableName(){
    	return this.getClass().getSimpleName();
    }
    
}
