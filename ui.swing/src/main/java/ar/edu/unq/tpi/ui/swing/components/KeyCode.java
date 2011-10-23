package ar.edu.unq.tpi.ui.swing.components;


public enum KeyCode {
	
	DELETE("C"),
	BACK("AC"),
	DIV("/"),
	MULT("*"),
	ADD("+"),
	MINUS("-"),
	SQRT("sqrt"),
	EQUAL("="),
	POINT("."),
	SIGN("+/-");
	
	private String value;

	private KeyCode(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return getValue();
	}
	
	public static KeyCode valueOfString(String string) {
		
		if(string.equals(DELETE.getValue()))
			return KeyCode.DELETE;
		if(string.equals(BACK.getValue()))
			return KeyCode.BACK;
		if(string.equals(DIV.getValue()))
			return KeyCode.DIV;
		if(string.equals(MULT.getValue()))
			return KeyCode.MULT;
		if(string.equals(ADD.getValue()))
			return KeyCode.ADD;
		if(string.equals(MINUS.getValue()))
			return KeyCode.MINUS;
		if(string.equals(SQRT.getValue()))
			return KeyCode.SQRT;
		if(string.equals(EQUAL.getValue()))
			return KeyCode.EQUAL;
		if(string.equals(POINT.getValue()))
			return KeyCode.POINT;
		if(string.equals(SIGN.getValue()))
			return KeyCode.SIGN;
		return null;
	}

	public String getValue() {
		return value;
	}
	public boolean equals(String string) {
		return value.equals(string);
	}
	


}
