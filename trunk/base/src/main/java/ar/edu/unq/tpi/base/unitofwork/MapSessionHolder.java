package ar.edu.unq.tpi.base.unitofwork;

import java.util.HashMap;
import java.util.Map;

public class MapSessionHolder implements SessionHolder {

    private Map<String, Object> map;


    public MapSessionHolder(final Map map) {
        this.map = map;
    }


    public MapSessionHolder() {
        this(new HashMap());
    }


    public <T> T get(final String key) {
        return (T) this.map.get(key);
    }


    public <T> T remove(final String key) {
        return (T) this.map.remove(key);
    }


    public void set(final String key, final Object value) {
        this.map.put(key, value);
    }
    
    public void invalidate() {
    	//nothing to do
    }

}
