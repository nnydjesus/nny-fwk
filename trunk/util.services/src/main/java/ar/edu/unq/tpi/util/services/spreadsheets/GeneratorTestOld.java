package ar.edu.unq.tpi.util.services.spreadsheets;

import java.util.Map;





public class GeneratorTestOld extends Generator {
	public GeneratorTestOld() {
		configMap(f(), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
	}

	protected  static Function<Map<String, Container>, Object> f() {
		return new Function<Map<String, Container>, Object>() {
			
			@Override
			public Container execute(Map<String, Container> item) {
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