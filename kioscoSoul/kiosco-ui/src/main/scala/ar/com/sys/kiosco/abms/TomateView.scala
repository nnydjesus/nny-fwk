package ar.com.sys.kiosco.abms

import	scala.swing._
import javax.swing.JTextField

object TomateView extends SimpleGUIApplication {
    
  
	def top = new MainFrame {
    
	var toma = new Tomate()
	
	title = "Firts Swing App"
    val text2 = new JTextField()
	val text = new TextField(){

	 Binder.bind(toma.diaDeElaboracion, text2)	
		
	
	  text = text2.getText()
	}
	
    toma.diaDeElaboracion.:=("Viernes")
    
    println(toma.diaDeElaboracion.value)
    val label = new Label {
      text = "diaDeElaboracion:"
    }	
    contents = new BoxPanel(Orientation.Horizontal) {
      contents += label
      contents += text
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
  }

  }