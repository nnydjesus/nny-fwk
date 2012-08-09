package ar.com.sys.kiosco.util
import java.awt.Color
import java.awt.Font
import scala.swing.Component
import javax.swing.JComponent
import java.awt.Container

trait FontSize extends Component {
  font = new Font("Arial", Font.PLAIN, 24)
}


trait JFontSize extends Container {
  setFont( new Font("Arial", Font.PLAIN, 24))
}