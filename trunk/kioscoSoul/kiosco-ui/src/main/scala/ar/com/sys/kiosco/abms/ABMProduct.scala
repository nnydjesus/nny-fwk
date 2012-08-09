package ar.com.sys.kiosco.abms

import ar.edu.unq.tpi.ui.swing.components.abms.ABMFrame
import ar.edu.unq.tpi.ui.swing.components.AbstractBindingPanel
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.ui.swing.components.abms.ABMEdition
import ar.edu.unq.tpi.base.persistence.transaction.UseCaseManager
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.util.ScalaUseCase
import ar.edu.unq.tpi.ui.swing.components.MyJComboBox
import ar.edu.unq.tpi.base.bean.ProductType
import ar.com.sys.kiosco.util.MethodListener
import java.awt.Window
import ar.com.sys.kiosco.util.JFontSize
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.bean.ProductAtributes

class ABMProduct(product: Product, parent:Window, edit: java.lang.Boolean) extends ABMFrame[Product](product, parent, edit) with JFontSize {

  var cbType:MyJComboBox[ProductType] = new MyJComboBox[ProductType](KioscoApplication.kiosco.productTypeHome.getAll())
  
  def this(product: Product, edit: java.lang.Boolean){
    this(product, null, edit)
    this.createMyForm(getEdicion())
  }
  
  def this() = {
    this(new Product(),  true)
  }

  override def createForm(edicion: ABMEdition[Product]) = {
    
  }
  
  def createMyForm(edicion: ABMEdition[Product]) = {
    edicion.addBindingTextField(ProductAtributes.CODE, "Code")
    edicion.addBindingTextField(ProductAtributes.NAME, "Nombre")
    edicion.addBindingDoubleField(ProductAtributes.BUY_PRICE, "Precio de Compra")
    edicion.addBindingDoubleField("percentage", "Porcentaje De Ganancia")
    edicion.addComponent("Tipo", cbType);
    edicion.addBindingDateField(ProductAtributes.MATURITY, "Vencimiento")
    edicion.addBindingIntegerField(ProductAtributes.STOCK, "Stock")
    edicion.addBindingIntegerField("minStock", "Stock Minimo")
    
    cbType.addActionListener(new MethodListener(this.updateProductType))
    cbType.setSelectedItem(getModel().productType)

    this.setSize(700, 550)
    centerWindow()
  }
  
  override def edicionAceptar(obj: Any) {
    UseCaseManager.execute(new ScalaUseCase("Nueva Producto", this.newProduct))
    super.edicionAceptar(obj)
  }
  
  def updateProductType() = this.getModel().setProductType(cbType.getSelectedItem())

  def newProduct() = {
    KioscoApplication.kiosco.addProduct((this.getEdicion().getModel()))
    this.edicionCancelar()
  }
  
  override def getHome():Home[Product]={
    KioscoApplication.kiosco.productHome.refresh()
    return KioscoApplication.kiosco.productHome
  }
  

}

object A {

  def main(args: Array[String]) {
    new ABMProduct().open()
  }
}