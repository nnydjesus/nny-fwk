package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.Comparator;



public final class ComtareToCompare implements Comparator<Long> {
	@Override
    public int compare(final Long l1, final Long l2) {
        return l2.compareTo(l1);
    }
}