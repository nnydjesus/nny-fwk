package ar.com.sys.kiosco.abms
import javax.swing.JTextField
import javax.swing.event.DocumentListener
import javax.swing.event.DocumentEvent

class Property[T](var value : T) {
    var listeners = List[T => Unit]()
 
    def apply() = value
 
    def :=(aValue : T) {
        if (value != aValue) {
            value = aValue
            fireEvent
        }
    }
 
    def registerListener(l : T => Unit) {
        listeners = l :: listeners
    }
 
    private def fireEvent {
        listeners.foreach(_(value))
    }
}
 
object Property {
    implicit def apply[T](t : T)(implicit owner : PropertyOwner) : Property[T] =
        new Property(t : T)
 
    implicit def toT[T](p : Property[T]) : T = p()
}
 
trait PropertyOwner {
    implicit val THE_OWNER = this
}
 
object Binder {
    def bind(p : Property[String], textField : JTextField) {
        var locked = false
 
        initTextField
        syncFromTextFieldToProperty
        syncFromPropertyToTextField
 
        def initTextField = p() match {
            case null => textField.setText("")
            case _    => textField.setText(p().toString())
        }
 
        def syncFromPropertyToTextField {
            p.registerListener { value : String =>
                if (textField.getText != value)
                    textField.setText(value)
            }
        }
        def syncFromTextFieldToProperty = textField.getDocument.addDocumentListener(new DocumentListener() {
 
            def changedUpdate(e : DocumentEvent) = updateProperty
            def insertUpdate(e : DocumentEvent) = updateProperty
            def removeUpdate(e : DocumentEvent) = updateProperty
        })
 
        def updateProperty = {
            p := textField.getText
        }
    }
}