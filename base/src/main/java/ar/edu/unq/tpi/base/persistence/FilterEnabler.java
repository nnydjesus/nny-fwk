package ar.edu.unq.tpi.base.persistence;

import org.hibernate.Session;

public interface FilterEnabler {
	void enableFilters(Session session);
}
