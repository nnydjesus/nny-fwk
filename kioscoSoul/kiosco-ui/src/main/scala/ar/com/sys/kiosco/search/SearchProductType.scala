package ar.com.sys.kiosco.search
import java.awt.Font
import ar.com.sys.kiosco.abms.ABMProductType
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.ProductAtributes
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.ui.swing.components.search.GeneralFrame
import ar.edu.unq.tpi.ui.swing.components.search.SearchPanel
import javax.swing.table.JTableHeader
import ar.com.sys.kiosco.util.TableFont

class SearchProductType(any: Any) extends GeneralFrame[ProductType]("ProductType", classOf[ProductType]) {

  override def createHome(): Home[ProductType] = KioscoApplication.kiosco.productTypeHome

  def this() = {
    this(null)
    this.setSize(500, 600)
    centerWindow()
    
  }

  override def createSearchForm(search: SearchPanel[ProductType]) {
    var nameText = search.addAutocompleteTextField(ProductAtributes.NAME, "Nombre")

  }


  override def abmClass(): Class[ABMProductType] = {
    return classOf[ABMProductType];
  }

  override def getFont() = TableFont.cellFont

  override def conconfigureTableHeader(header: JTableHeader) {
    header.setFont(TableFont.headerFont)
  }

}
