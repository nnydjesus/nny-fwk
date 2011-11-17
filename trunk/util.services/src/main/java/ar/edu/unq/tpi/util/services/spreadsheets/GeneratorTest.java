package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.Map;



public class GeneratorTest extends Generator {
	public GeneratorTest() {
		configMap(f(), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
	}

	@SuppressWarnings("unchecked")
	protected  static Function f() {
		return new Function() {
			
			@Override
			public <T, J> J execute(T object) {
				Map<String,Container> item = (Map<String, Container>) object;
				int articleId = item.get("code").asInteger();
				String prop1 = item.get("prop1").asString();
				String prop2 = item.get("prop2").asString();
				System.out.println("code: " + articleId + " prop1: " + prop1 + " prop2: " + prop2);
				return null;
			}
		};
	}
	

	//"nny.fwk@gmail.com", "sarasaza" 
	public static void main(String[] args) {
		new Generator().map(f(), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
	}
}