package ar.com.sys.kiosco.app
import ar.edu.unq.tpi.base.persistence.transaction.UseCase

class ScalaUseCase(var name:String, var method:()=> Unit ) extends UseCase{

  override def getName():String = this.name
  
  override def run() = this.method.apply()
  
}