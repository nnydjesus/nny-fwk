package ar.edu.unq.tpi.base.bean

import java.util.Date
import ar.edu.unq.tpi.base.generator.annotations.DataGenerator
import ar.edu.unq.tpi.base.model.initialData.ProductGenerator
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject
import javax.persistence.Basic
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType
import org.hibernate.mapping.UniqueKey
import javax.persistence.Column
import ar.edu.unq.tpi.util.DecimalFormatUtil
import org.uqbar.commons.utils.Transactional

@Entity
@DataGenerator(classOf[ProductGenerator])
@Transactional
class Product() extends IdentificablePersistentObject {

  @Basic @Column(unique = true) var name: String = null
  @Basic @Column(unique = true) var code: String = null
  @Basic var percentage: Double = 0
  @Basic var buyPrice: Double = 0
  @Basic var stock: Int = 0
  @Basic var minStock: Int = 0
  @Temporal(TemporalType.TIMESTAMP) var maturity: Date = null
  @ManyToOne var productType: ProductType = null
  @Basic var moneyInvested: Double = 0

  def this(code: String, name: String, percentage: Double, buyPrice: Double,
    maturity: Date, productType: ProductType, stock: Int, minStock: Int) {
    this();
    this.name = name
    this.code = code
    this.percentage = percentage
    this.buyPrice = buyPrice
    this.stock = stock
    this.maturity = maturity
    this.productType = productType
    this.minStock = minStock;
    this.calculateMoneyInvested()

  }

  def sellPrice(): Double = DecimalFormatUtil.format(this.buyPrice + (this.buyPrice * this.percentage) / 100)

  def sell(cant: Int) = {
	  this.stock -= cant
	  this.calculateMoneyInvested()
  }

  def buy(cant: Int) = {
    this.stock += cant
    this.calculateMoneyInvested()
  }

  def calculateMoneyInvested() = this.moneyInvested = DecimalFormatUtil.format(this.buyPrice * this.stock)

  override def toString(): String = {
    return "nombre: " + this.name +
      "\n precio de compra: " + this.buyPrice +
      "\n precio de venta: " + this.sellPrice() +
      "\n porcentage de ganancia: " + this.percentage +
      "\n stock: " + this.stock +
      "\n minStock: " + this.minStock
    "\n dineroInvertido: " + this.moneyInvested
  }

  override def atributos(): Array[String] = {
    return Array(ProductAtributes.NAME, ProductAtributes.CODE, ProductAtributes.BUY_PRICE, ProductAtributes.SELL_PRICE,
      ProductAtributes.PRODUCT_TYPE, ProductAtributes.STOCK, ProductAtributes.MATURITY)
  }

  def getName = this.name
  def getCode = this.code
  def getBuyPrice = this.buyPrice
  def getPercentage = this.percentage
  def getStock = this.stock
  def getMaturity = this.maturity
  def getMinStock = this.minStock
  def getSellPrice = this.sellPrice()
  def getProductType = this.productType
  def getMoneyInvested = this.moneyInvested

  def setName(value: String) = this.name = value
  def setCode(value: String) = this.code = value
  def setBuyPrice(value: Double) = this.buyPrice = value
  def setPercentage(value: Double) = this.percentage = value
  def setStock(value: Int) = this.stock = value
  def setMaturity(value: Date) = this.maturity = value
  def setMinStock(value: Int) = this.minStock = value
  def setProductType(value: ProductType) = this.productType = value
  def setMoneyInvested(value: Double) = this.moneyInvested = value
  
}

object ProductAtributes {

  val NAME: String = "name"
  val CODE: String = "code"
  val BUY_PRICE: String = "buyPrice"
  val SELL_PRICE: String = "sellPrice"
  val STOCK: String = "stock"
  val MATURITY: String = "maturity"
  val PRODUCT_TYPE: String = "productType"
  val MONEYINVESTED: String = "moneyInvested"
}