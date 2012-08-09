package ar.com.sys.kiosco.abms
import java.awt.Window

import ar.com.sys.kiosco.util.JFontSize
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.ProductTypeAtributes
import ar.edu.unq.tpi.base.persistence.transaction.UseCaseManager
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.ui.swing.components.abms.ABMFrame
import ar.edu.unq.tpi.ui.swing.components.abms.ABMEdition
import ar.edu.unq.tpi.util.ScalaUseCase

class ABMProductType(productType: ProductType, parent: Window, edit: java.lang.Boolean) extends ABMFrame[ProductType](productType, parent, edit) with JFontSize {

  def this(productType: ProductType, edit: java.lang.Boolean) {
    this(productType, null, edit)
    this.setSize(450, 300)
    centerWindow()
  }

  def this() = {
    this(new ProductType(), true)
  }

  override def createForm(edicion: ABMEdition[ProductType]) = {
       edicion.addBindingTextField(ProductTypeAtributes.NAME, "Nombre")
  }


  override def edicionAceptar(obj: Any) {
    UseCaseManager.execute(new ScalaUseCase("Nuevo tipo de producto", this.newProductType))
    super.edicionAceptar(obj)
  }


  def newProductType() = {
    KioscoApplication.kiosco.addProductType((this.getEdicion().getModel()))
    this.edicionCancelar()
  }

  override def getHome(): Home[ProductType] = {
   	 KioscoApplication.kiosco.productTypeHome.refresh()
    return KioscoApplication.kiosco.productTypeHome
  }

}