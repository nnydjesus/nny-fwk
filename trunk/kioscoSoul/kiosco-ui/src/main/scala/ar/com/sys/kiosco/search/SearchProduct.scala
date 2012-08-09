package ar.com.sys.kiosco.search
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.Font
import java.awt.Toolkit

import ar.com.sys.kiosco.abms.ABMProduct
import ar.com.sys.kiosco.util.AutoCompleteListTextField
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductAtributes
import ar.edu.unq.tpi.base.bean.ProductTypeAtributes
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.ui.swing.components.search.GeneralFrame
import ar.edu.unq.tpi.ui.swing.components.search.SearchPanel
import javax.swing.table.JTableHeader

class SearchProduct(any: Any) extends GeneralFrame[Product]("Product", classOf[Product]) {

  override def createHome(): Home[Product] = KioscoApplication.kiosco.productHome
  val productType = new AutoCompleteListTextField(ProductTypeAtributes.NAME, KioscoApplication.kiosco.productTypeHome.getAll())

  def this() = {
    this(null)
    createMyForm(getSearch())
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize())
  }

  def createMyForm(search: SearchPanel[Product]) {
    var codeText = search.addAutocompleteTextField(ProductAtributes.CODE, "Codigo")
    var nameText = search.addAutocompleteTextField(ProductAtributes.NAME, "Nombre")
    search.getBuilder().append("Tipo de Producto", productType)

    productType.addKeyListener(new KeyAdapter() {
      override def keyPressed(event: KeyEvent) {
        if (event.getKeyCode().equals(KeyEvent.VK_ENTER)) {
          getModel().productType = KioscoApplication.kiosco.productTypeHome.getByName(productType.getText())
        }
      }
    })

  }

  override def createSearchForm(search: SearchPanel[Product]) {

  }

  override def abmClass(): Class[ABMProduct] = {
    return classOf[ABMProduct];
  }

  override def getFont() = new Font("Arial", Font.PLAIN, 24)

  override def conconfigureTableHeader(header: JTableHeader) {
    header.setFont(new Font("Arial", Font.BOLD, 22))
  }

}

object SearchProductMain {

  def main(args: Array[String]) {
    new SearchProduct().open()
  }
}