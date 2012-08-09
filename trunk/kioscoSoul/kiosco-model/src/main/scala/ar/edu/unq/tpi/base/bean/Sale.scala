package ar.edu.unq.tpi.base.bean

import java.util.ArrayList
import java.util.Date
import scala.collection.JavaConversions.asScalaBuffer
import ar.edu.unq.tpi.base.persistence.PersistentObject
import javax.persistence.Basic
import javax.persistence.Entity
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.persistence.OneToMany
import javax.persistence.CascadeType
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject
import ar.edu.unq.tpi.util.commons.exeption.UserException
import org.uqbar.commons.utils.Transactional

@Entity
@Transactional
class Sale() extends IdentificablePersistentObject {
  
  @OneToMany(cascade = Array(CascadeType.ALL))
  var soldProducts: java.util.List[ProductSold] = new ArrayList[ProductSold]()
  
  @Basic var total: Double = 0 
  
  @Temporal(TemporalType.DATE) 
  var date: Date = null
  
  def addProduct(product:Product, cant:Int):Unit = {
    if(product.stock < cant){
      throw new UserException("El stock para el producto " + product.name + " es " + product.stock + " y queres vender " + cant )
    }
    this.addProduct(new ProductSold(product, cant))
  }
  
  def addProduct(product:ProductSold):Unit = {
    this.soldProducts.add(product)
    this.total += product.totalSold
  }
  
  def removeProduct(product:Product)={
    this.soldProducts.remove(new ProductSold(product, 0))
  }
  
  def confirm() = {
    this.date = new Date()
  }
  
  def getTotal = this.total
  def getDate = this.date
  def getSoldProducts = this.soldProducts
  
  def setTotal(value:Double) = this.total = value
  def setDate(value: Date) = this.date= value
  def setSoldProducts(value: ArrayList[ProductSold]) = this.soldProducts = value

}