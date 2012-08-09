package ar.edu.unq.tpi.util.services.spreadsheets
import java.util.Map
import ar.edu.unq.tpi.util.services.ScalaFunction

object GeneratorTest extends Generator{
  
//  def GeneratorTest() {
//		configMap(f(), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
//	}
//
//	protected  static Function f() {
//		return new Function() {
//			
//			@Override
//			public <T, J> J execute(T object) {
//				Map<String,Container> item = (Map<String, Container>) object;
//				int articleId = item.get("code").asInteger();
//				String prop1 = item.get("prop1").asString();
//				String prop2 = item.get("prop2").asString();
//				System.out.println("code: " + articleId + " prop1: " + prop1 + " prop2: " + prop2);
//				return null;
//			}
//		};
//	}
  
	  def f(obj:Any):Any = {
		  	obj match{
		  	  case item: Map[String, Container] => {
		  	    
		  	    var code:Integer = item.get("code").asInteger();
		  	    var prop1:String = item.get("prop1").asString();
		  	    var prop2:String = item.get("prop2").asString();
		  	    Console.out.print("code: " + code + " prop1: " + prop1 + " prop2: " + prop2)
		  	    
		  	  }
		  	  case _ => throw new ClassCastException
		  	}
	  }
	

	  
	  implicit def funtion(method:(Any) => Any) = new ScalaFunction(method)
	  
	//"nny.fwk@gmail.com", "sarasaza" 
//	public static void main(String[] args) {
//		new Generator().map(f(), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
//	}
	
	def main(args: Array[String]) {
		new Generator().map[Any](new ScalaFunction(f), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza" ), "pruebaSS");
	}

}

