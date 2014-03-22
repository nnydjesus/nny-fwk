package ar.com.sys.kiosco.util
import java.awt.Color
import java.awt.Font
import scala.swing.Component
import javax.swing.JComponent
import java.awt.Container


object TableFont{
  val headerFont = new Font("Arial", Font.BOLD, 18)
  val cellFont = new Font("Arial", Font.BOLD, 16)
}

trait FontSize extends Component {
  font = new Font("Arial", Font.PLAIN, 18)
}

trait JFontSize extends Container {
  setFont( new Font("Arial", Font.PLAIN, 18))
}