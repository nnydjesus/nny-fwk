package ar.edu.unq.tpi.base.model.initialData

import java.text.DateFormat
import java.util.Map
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.ProductTypeName
import ar.edu.unq.tpi.base.generator.annotations.DataGeneratorMethod
import ar.edu.unq.tpi.base.generator.InitialDataGenerator
import ar.edu.unq.tpi.base.model.homes.ProductHome
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.util.services.spreadsheets.Container
import ar.edu.unq.tpi.util.services.spreadsheets.Generator
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorRefreshStrategy
import ar.edu.unq.tpi.util.services.ScalaFunction
import java.util.Date
import java.text.SimpleDateFormat
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorExcelStrategy
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorCacheStrategy

class ProductGenerator extends InitialDataGenerator[Product] {

  var home = new ProductHome()
  var productTypeHome = new Home(classOf[ProductType], false)

  @DataGeneratorMethod
  def generate() = {
//    new Generator().map[Any](new ScalaFunction(makeProduct), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza"), "Productos");
    new Generator().map[Any](new ScalaFunction(makeProduct), new SSConnectorCacheStrategy(), "Productos")
  }

  def makeProduct(obj: Any): Any = {
    obj match {
      case item: Map[String, Container] => {

        var code = item.get("codigo").asString();
        var nombre = item.get("nombre").asString();
        var tipo= item.get("tipo").asString();
        var precioCompra= item.get("precioDeCompra").asDouble();
        var porcentage= item.get("porcentage").asDouble();
        var stock= item.get("stock").asInteger();
        var stockMinimo= item.get("stockMinimo").asInteger();
        var vencimiento= item.get("vencimiento").asString();
        
        home.saveOrUpdate(new Product(code, nombre, porcentage, precioCompra, 
        new SimpleDateFormat("dd/MM/yyyy").parse(vencimiento), productTypeHome.getByName(tipo), stock, stockMinimo))

      }
      case _ => throw new ClassCastException
    }
  }
}