package ar.com.nny.base.configuration.jfig;

import org.hibernate.Session;

public interface FilterEnabler {
	void enableFilters(Session session);
}
