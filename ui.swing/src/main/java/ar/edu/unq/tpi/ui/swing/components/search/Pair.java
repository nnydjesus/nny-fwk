package ar.edu.unq.tpi.ui.swing.components.search;

public class Pair<T, K> {
	
	private T object1;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object1 == null) ? 0 : object1.hashCode());
		result = prime * result + ((object2 == null) ? 0 : object2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (object1 == null) {
			if (other.object1 != null)
				return false;
		} else if (!object1.equals(other.object1))
			return false;
		if (object2 == null) {
			if (other.object2 != null)
				return false;
		} else if (!object2.equals(other.object2))
			return false;
		return true;
	}

	private K object2;
	
	public Pair(T object1, K object2) {
		this.setObject1(object1);
		this.setObject2(object2);
	}

	public void setObject2(K object2) {
		this.object2 = object2;
	}

	public K getObject2() {
		return object2;
	}

	public void setObject1(T object1) {
		this.object1 = object1;
	}

	public T getObject1() {
		return object1;
	}


}
