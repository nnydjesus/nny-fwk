package ar.edu.unq.tpi.util
import ar.edu.unq.tpi.base.persistence.transaction.UseCase


class ScalaUseCase(var name:String, var method:() => Unit) extends UseCase {

  override def getName() = this.name
  override def run() = method.apply()
  
}