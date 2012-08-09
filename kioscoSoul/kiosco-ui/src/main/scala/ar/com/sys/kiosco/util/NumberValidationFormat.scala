package ar.com.sys.kiosco.util
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFormattedTextField
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent

trait NumberValidationFormat extends JFormattedTextField {
  
  
  override def setValue(value:java.lang.Object) ={
    	try{
    	  if(!value.toString().equals("")){
    		  Double.unbox(value)
    	  }
    		super.setValue(value);
    	} catch {
    	  case ex: Exception => //
        }
    }

  override def setFocusLostBehavior(behavior: Int) = {
    super.setFocusLostBehavior(behavior)
    
    this.setFocusTraversalPolicyProvider(true)
    
    this.addFocusListener(new FocusAdapter(){
        	
        	override def focusGained(e:FocusEvent) {
        		super.focusGained(e);
        		setText("");
        		selectAll();
        	}
        });
    
    this.addKeyListener(new KeyAdapter() {

      override def keyTyped(e: KeyEvent) = {
        try {
          var char = e.getKeyChar()
          java.lang.Double.parseDouble(char.toString())
        } catch {
          case ex: Exception => e.consume()
        }
      }

    })
  }

}