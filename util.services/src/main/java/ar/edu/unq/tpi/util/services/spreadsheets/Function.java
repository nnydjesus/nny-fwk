package ar.edu.unq.tpi.util.services.spreadsheets;

public interface Function<T, J> {
	
	J  execute(T object);

}
