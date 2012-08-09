package ar.edu.unq.tpi.base
import java.util.Date
import scala.collection.JavaConversions.asScalaBuffer
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductTypeName
import ar.edu.unq.tpi.base.bean.Sale
import ar.edu.unq.tpi.base.generator.annotations.DataGenerator
import ar.edu.unq.tpi.base.model.homes.ProductHome
import ar.edu.unq.tpi.base.model.homes.SaleHome
import ar.edu.unq.tpi.base.search.Home
import javax.persistence.Entity
import ar.edu.unq.tpi.base.bean.ProductSold
import ar.edu.unq.tpi.base.model.homes.ProductTypeHome

object KioscoApplication{
  
  val kiosco = new Kiosco()
  
}

class Kiosco() {

  var productHome = new ProductHome()
  var productTypeHome = new ProductTypeHome()
  var productSoldHome = new Home(classOf[ProductSold], true)
  var saleHome = new SaleHome()

  def addSell(sale: Sale) = {
    sale.confirm();
    var product: Product = null
    sale.soldProducts.foreach(sold => {
      product = productHome.getByCode(sold.code)
      product.sell(sold.cant)
      productHome.update(product);
    })
    saleHome.save(sale)

  }
  
  def addProduct(product:Product) = productHome.saveOrUpdate(product)
  
  def addProductType(productType:ProductType) = productTypeHome.saveOrUpdate(productType)
  
  def cigaretteProfitsToday(desde:Date, hasta:Date)= {
    saleHome.getGananciaByTypeOfDay(desde, hasta);
  }

}