package ar.edu.unq.tpi.util.services
import ar.edu.unq.tpi.util.services.spreadsheets.Function

 class ScalaFunction[E, T](var method:(E)  => T) extends Function[E, T](){

	override def execute(e:E):T=  method(e)
}