package ar.com.sys.kiosco.app

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.text.DecimalFormat
import java.text.NumberFormat

import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.FormattedTextField
import scala.swing.Label
import scala.swing.Orientation

import ar.com.sys.kiosco.util.AutoCompleteListTextField
import ar.com.sys.kiosco.util.JFontSize
import ar.com.sys.kiosco.util.MethodListener
import ar.com.sys.kiosco.util.NumberValidationFormat
import ar.com.sys.kiosco.util.TableFont
import ar.com.sys.kiosco.util.TotalSale
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductSold
import ar.edu.unq.tpi.base.bean.Sale
import ar.edu.unq.tpi.base.persistence.transaction.UseCaseManager
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.utils.Path
import ar.edu.unq.tpi.ui.swing.components.FormBuilder
import ar.edu.unq.tpi.ui.swing.components.GeneralTable
import ar.edu.unq.tpi.ui.swing.components.Generator
import ar.edu.unq.tpi.ui.swing.components.TableSelection
import ar.edu.unq.tpi.ui.swing.components.abms.ABMEdition
import ar.edu.unq.tpi.ui.swing.components.abms.ABMFrame
import ar.edu.unq.tpi.ui.swing.components.autocomplete.AutoCompleteTextField
import ar.edu.unq.tpi.ui.swing.components.search.TopPanel
import ar.edu.unq.tpi.util.commons.exeption.UserException
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JFormattedTextField
import javax.swing.JPanel
import javax.swing.table.JTableHeader

class SalesUI(model: Sale) extends ABMFrame[Sale](model, null, true) with TableSelection[Sale] {

  var totalText: FormattedTextField = null
  val soldProducts: GeneralTable[ProductSold] = Generator.GENERATE_TABLE(getEdicion().getModel().soldProducts, new ProductSold().atributos(), this);
  var productText: AutoCompleteTextField = addAutocompleteTextField("name")
  var cantText = createCantText()
  var topPanel: TopPanel = new TopPanel();

  def this() {
    this(new Sale())
    this.setName("")
    this.init()
  }

  def init() {
    this.add(topPanel, 0);
    getEdicion().setTransactional(false)
    getEdicion().add(soldProducts)
    var format = new DecimalFormat("#.#")
    format.setMaximumFractionDigits(2)
    totalText = new FormattedTextField(format) {
      preferredSize = new Dimension(300, 50)
      editable = false;
      font = new Font("Arial", Font.BOLD, 70)
      background = Color.BLACK
      foreground = Color.GREEN
      text = "0";
    }
    var totalPanel = new BorderPanel() {
      var panel = new BoxPanel(Orientation.Horizontal) {
        var totalLabel = new Label("Total: ") {
          font = new Font("Arial", Font.BOLD, 36)
        }
        peer.add(totalLabel.peer)
        peer.add(totalText.peer)
      }

      this.add(panel, BorderPanel.Position.West)
      this.preferredSize = new Dimension(500, 300)
    }
    getEdicion().add(totalPanel.peer)
    getEdicion().getBotonAceptar().getActionListeners().foreach(a => getEdicion().getBotonAceptar().removeActionListener(a))
    getEdicion().cleanButtons()
    getEdicion().setBotonAgregar(new JButton(new ImageIcon(Path.path() + "Images/confirmar.png")) with JFontSize {
      setText("Confirmar")
      addActionListener(new MethodListener(edicionAceptar))

    });
    getEdicion().configureButtons()
    getEdicion().add(getEdicion().getPanelDeBotones().getPanel())
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize())
    addComponents()

    topPanel.getBtCerrar().addActionListener(new MethodListener(this.close))
  }

  override def createForm(edicion: ABMEdition[Sale]) = {
    //    this.updateFocus()
    edicion.remove(edicion.getPanelDeBotones().getPanel())
  }

  override def edicionCancelar() {
    setEditionModel(KioscoApplication.kiosco.saleHome.createExample());
    this.update()
    updateDataTable()
    updateTotalText()
    this.updateFocus()
  }

  override def getHome(): Home[Sale] = KioscoApplication.kiosco.saleHome

  override def addActions() {

  }

  def edicionAceptar() {
    new TotalSale(getModel().total, this).main(null)
  }

  override def edicionAceptar(obj: Any) {
  }

  def confirmSale() = {
    this.getEdicion().onAccept();
    UseCaseManager.execute(new ScalaUseCase("Nueva Venta", this.newSale))
  }

  def newSale() = {
    KioscoApplication.kiosco.addSell(this.getEdicion().getModel())
    this.edicionCancelar()
  }

  def addAutocompleteTextField(property: String): AutoCompleteTextField = {
    val text = new AutoCompleteListTextField[Product](property, KioscoApplication.kiosco.productHome.getAll())
    text.addKeyListener(new KeyAdapter() {

      override def keyPressed(event: KeyEvent) = {
        if (event.getKeyCode().==(KeyEvent.VK_ENTER)) {
          selectedProduct()
        }
      }
    })

    text.addActionListener(new MethodListener(this.selectedProduct))
    return text
  }

  def selectedProduct() {
    cantText.requestFocusInWindow();
    cantText.selectAll()
  }

  def createCantText(): JFormattedTextField = {
    var textField: JFormattedTextField = new JFormattedTextField(NumberFormat.getInstance()) with NumberValidationFormat;
    textField.addActionListener(new ActionListener() {

      override def actionPerformed(event: ActionEvent) = {
        if (productText.getText().equals("")) {
          warningMessage("Informe", "Debes elegir un producto");
          updateFocus()
          cantText.setText("0")
        } else {
          try {
            val productSelected = KioscoApplication.kiosco.productHome.getByName(productText.getText())
            if (productSelected == null) {
              warningMessage("Informe", "Debes elegir un producto");
              updateFocus()
              cantText.setText("0")
            } else {
              getEdicion().getModel().addProduct(productSelected, textField.getText().toInt)
              updateDataTable()
              updateTotalText()
              productText.setText("")
              updateFocus()
            }
          } catch {
            case e: UserException => errorMessage("Error", e.getMessage())
          } finally {
            cantText.setText("0")
          }
        }
      }
    });
    return textField

  }

  def addComponents() = {
    productText.setSize(new Dimension(200, 100))
    cantText.setSize(new Dimension(200, 100))

    var form = new FormBuilder(this.getEdicion().getModel())

    //    var beanAdapter = new BeanAdapter[SalesUI](this)
    //    var valueModel = beanAdapter.getValueModel("name");
    //    Bindings.bind(productText, valueModel);
    form.append("Producto", productText);

    var form2 = new FormBuilder(this.getEdicion().getModel())
    form2.append("Cant", cantText)

    var panel = new JPanel(new GridLayout(1, 2))
    panel.add(form.build())
    panel.add(form2.build())
    panel.setSize(500, 100)

    getEdicion().getBuilder().append("", panel)

  }

  def updateDataTable() = soldProducts.update(getEdicion().getModel().soldProducts)
  def updateTotalText() = totalText.peer.setValue(getEdicion().getModel().total)
  def updateFocus() = productText.requestFocusInWindow()

  override def getFont() = TableFont.cellFont
  override def selectedTable(event: MouseEvent) = {}
  override def configureTableHeader(header: JTableHeader) = {
    header.setFont(TableFont.headerFont)
    header.setForeground(Color.BLUE)
  }

}

object ABMSale {
  def main(args: Array[String]) {
    new SalesUI().open()
  }

}