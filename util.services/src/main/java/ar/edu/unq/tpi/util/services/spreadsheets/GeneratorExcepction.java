package ar.edu.unq.tpi.util.services.spreadsheets;

public class GeneratorExcepction extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GeneratorExcepction(final String message) {
		super(message);
	}

	public GeneratorExcepction(final Throwable cause) {
		super(cause);
	}

	public GeneratorExcepction(final String message, final Throwable cause) {
		super(message, cause);
	}

}
