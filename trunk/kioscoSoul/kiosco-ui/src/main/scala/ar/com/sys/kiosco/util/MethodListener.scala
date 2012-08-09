package ar.com.sys.kiosco.util
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

class MethodListener(var method:()=> Unit) extends ActionListener {
  
  override def actionPerformed(event:ActionEvent) =   this.method()

}