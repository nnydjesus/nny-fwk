package ar.edu.unq.tpi.base.bean

import java.util.ArrayList
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject
import javax.persistence.Basic
import javax.persistence.Entity
import javax.persistence.ManyToOne
import scala.collection.mutable.Buffer
import java.text.DecimalFormat
import ar.edu.unq.tpi.util.DecimalFormatUtil


object ProductSoldAtributes{
  val NAME : String = "nombre"
  val CODE : String = "codigo"
  val BUY_PRICE : String = "precioDeCompra"
  val SELL_PRICE: String = "precio"
  val CANT : String = "cantidad"
  val TOTAL_SOLD : String = "total"
  val PRODUCT_TYPE : String = "tipo"
    

}

@Entity
class ProductSold() extends IdentificablePersistentObject{

    
  @Basic var name: String = null
  @Basic var code: String = null
  @Basic var buyPrice: Double = -1
  @Basic var sellPrice: Double = -1
  @Basic var totalSold: Double = -1
  @Basic var cant: Int = 0
  @Basic var ganancia: Double = 0
  @ManyToOne var productType: ProductType = null

  def this(name: String, code: String, buyPrice: Double,
    sellPrice: Double, productType: ProductType, cant:Int) {
    this();
    this.name = name
    this.code = code
    this.cant = cant
    this.buyPrice = buyPrice
    this.productType = productType
    this.sellPrice = DecimalFormatUtil.format(sellPrice)
    this.totalSold = this.sellPrice * this.cant
    this.ganancia = totalSold - (buyPrice * this.cant)  
    
  }
  
  def this(product:Product, cant:Int) = {
    this(product.name, product.code, product.buyPrice, product.sellPrice(), product.productType, cant);
    this.totalSold = this.sellPrice * this.cant
  }
  
  
  override def equals(obj:Any):Boolean = {
    obj match {
      case sold:ProductSold => return sold.code.equals(this.code)
      case x => return false
    } 
  }

  override def toString(): String = {
    return "nombre: " + this.name +
      "\n precio de compra: " + this.buyPrice +
      "\n precio de venta: " + this.sellPrice
  }
  
  def getNombre = this.name
  def getCodigo = this.code
  def getCantidad = this.cant
  def getPrecioDeCompra = this.buyPrice
  def getPrecio = this.sellPrice
  def getTipo = this.productType
  def getTotal = this.totalSold
  
  def setNombre(value: String) = this.name = value
  def setCodigo(value: String) = this.code = value
  def setCantidad(value: Int) = this.cant = value
  def setPrecioDeCompra(value: Double) = this.buyPrice = value
  def setPrecio(value: Double) = this.sellPrice = value
  def setTipo(value: ProductType) = this.productType = value

  override def atributos(): Array[String] = {
   return Array(ProductSoldAtributes.NAME, ProductSoldAtributes.CODE, ProductSoldAtributes.CANT, 
       ProductSoldAtributes.SELL_PRICE, ProductSoldAtributes.PRODUCT_TYPE, ProductSoldAtributes.TOTAL_SOLD) 
  } 
  
}