package ar.com.sys.kiosco.util
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.Color
import java.awt.Dimension
import java.text.DecimalFormat
import scala.swing.event.ButtonClicked
import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Component
import scala.swing.FormattedTextField
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.SimpleSwingApplication
import scala.swing.TextField
import org.jvnet.substance.utils.border.SubstanceBorder
import com.jgoodies.forms.builder.ButtonStackBuilder
import ar.com.sys.kiosco.app.SalesUI
import javax.swing.ImageIcon
import java.awt.Font

class TotalSale(var sale: Double, var salesUI: SalesUI) extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "Total de la venta"

    val totalSale = new Label with FontSize with LabelConfiguration {
      text = "Total venta"
      border = new SubstanceBorder()
    }

    val imputClient = new Label with FontSize with LabelConfiguration {
      text = "Input del cliente"
      border = new SubstanceBorder()
    }

    val vuelto = new Label with FontSize with LabelConfiguration {
      text = "vuelto"
      border = new SubstanceBorder()
    }

    var format = new DecimalFormat("#.#");
    format.setMinimumFractionDigits(2);

    val textTotalSale = new FormattedTextField(format) with FontSize with ComponentSize {
      peer.setValue(sale);
      editable = false
    }

    val textImputClient = new TextField with FontSize with ComponentSize {  }

    val textVuelto = new FormattedTextField(format) with FontSize with ComponentSize {
      editable = false
    }

    val confirmButton = new Button with FontSize with ButtonSize {
      text = "Confirmar"
      icon = new ImageIcon("./Images/confirmar.png")
    }

    val editButton = new Button with FontSize with ButtonSize {
      text = "Modificar"
      icon = new ImageIcon("./Images/confirmar.png")
    }

    val cancelButton = new Button with FontSize with ButtonSize {
      text = "cancelar"
      icon = new ImageIcon("./Images/cancel.png")
    }

    contents = new BoxPanel(Orientation.Horizontal) {
      contents += new BoxPanel(Orientation.Vertical) {
        var di = new Dimension(305, 400)
        preferredSize = di
        minimumSize = di
        maximumSize = di
        contents += new BorderPanel() { add(totalSale, BorderPanel.Position.West) }
        contents += new BorderPanel() { add(textTotalSale, BorderPanel.Position.West) }
        contents += new BorderPanel() { add(imputClient, BorderPanel.Position.West) }
        contents += new BorderPanel() { add(textImputClient, BorderPanel.Position.West) }
        contents += new BorderPanel() { add(vuelto, BorderPanel.Position.West) }
        contents += new BorderPanel() { add(textVuelto, BorderPanel.Position.West) }
        border = new SubstanceBorder()
      }

      var stack: ButtonStackBuilder = new ButtonStackBuilder()
      stack.addRelatedGap(); stack.addRelatedGap();  stack.addRelatedGap()
      stack.addRelatedGap(); stack.addRelatedGap();  stack.addRelatedGap()
      stack.addGridded(confirmButton.peer)
      stack.addRelatedGap()
      stack.addRelatedGap()
      stack.addGridded(editButton.peer)
      stack.addRelatedGap()
      stack.addRelatedGap()
      stack.addGridded(cancelButton.peer)
      stack.setBorder(new SubstanceBorder())

      this.peer.add(stack.getContainer())

    }

    this.size = new Dimension(570, 430)
    this.centerOnScreen()

    textImputClient.requestFocus()

    listenTo(confirmButton, editButton, cancelButton, textImputClient)
    reactions += {
      case ButtonClicked(`editButton`) => {
        close()
        salesUI.updateFocus()
      }

      case ButtonClicked(`confirmButton`) => {
        salesUI.confirmSale()
        close()
        salesUI.updateFocus()
      }

      case ButtonClicked(`cancelButton`) => {
        salesUI.edicionCancelar();
        close()
        salesUI.updateFocus()
      }

    }
    var keyAdapter = new KeyAdapter() {

      override def keyPressed(e: KeyEvent) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          val imput = textImputClient.text.toDouble
          var vuelto = (imput - sale)
          textVuelto.peer.setValue(vuelto)
          if (vuelto < 0) {
            textVuelto.background = Color.RED
          } else {
            textVuelto.background = Color.BLUE
          }
        }
      }

    }
    textImputClient.peer.addKeyListener(keyAdapter)
    this.peer.addKeyListener(keyAdapter)

    override def closeOperation() {
      this.close()
      this.dispose()
    }

  }
}


trait LabelConfiguration extends ComponentSize {
	background = Color.ORANGE
	foreground = Color.BLACK
	font = new Font("Arial", Font.BOLD, 24)
}

trait ComponentSize extends Component {
  var dimension = new Dimension(300, 50)
  this.minimumSize = dimension
  this.preferredSize = dimension
  this.maximumSize = dimension

}

object TMain {
  def main(a: Array[String]) {
    new TotalSale(21.2, null).main(null)
  }

}

