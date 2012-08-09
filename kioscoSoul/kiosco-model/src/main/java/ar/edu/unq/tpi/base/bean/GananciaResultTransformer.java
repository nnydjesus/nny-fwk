package ar.edu.unq.tpi.base.bean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class GananciaResultTransformer implements ResultTransformer{
	private static final long serialVersionUID = 1L;
	
	private List<GananciaPerType> list = new ArrayList<GananciaPerType>();

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		GananciaPerType ganancia = new GananciaPerType(tuple[0], tuple[1], tuple[2]);
		list.add(ganancia);
		return ganancia;
	}

	@Override
	public List<GananciaPerType> transformList(List collection) {
		return list;
	}

}
