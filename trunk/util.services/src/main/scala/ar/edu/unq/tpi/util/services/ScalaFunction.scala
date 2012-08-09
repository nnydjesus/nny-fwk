package ar.edu.unq.tpi.util.services
import ar.edu.unq.tpi.util.services.spreadsheets.Function

 class ScalaFunction(var method:(Any)  => Any) extends Function[Any, Any](){

	override def execute(t:Any):Any =  method(t)
}