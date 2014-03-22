package ar.edu.unq.tpi.base.model.initialData

import java.util.Map
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.generator.annotations.DataGeneratorMethod
import ar.edu.unq.tpi.base.generator.InitialDataGenerator
import ar.edu.unq.tpi.base.model.homes.ProductHome
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.util.services.spreadsheets.Container
import ar.edu.unq.tpi.util.services.spreadsheets.Generator
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorRefreshStrategy
import ar.edu.unq.tpi.util.services.ScalaFunction
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorExcelStrategy
import ar.edu.unq.tpi.util.services.spreadsheets.SSConnectorCacheStrategy

class ProductTypeGenerator extends InitialDataGenerator[ProductType] {

  var productTypeHome = new Home(classOf[ProductType], false)

  @DataGeneratorMethod
  def generate() = {
//	  new Generator().map[Any](new ScalaFunction(makeProductType), new SSConnectorRefreshStrategy("nny.fwk@gmail.com", "sarasaza"), "Productos", "Tipos");
    new Generator().map[Any](new ScalaFunction(makeProductType), new SSConnectorCacheStrategy(), "Productos", "Tipos");
  }
  
    def makeProductType(obj: Any): Any = {
    obj match {
      case item: Map[String, Container] => {

        var nombre = item.get("nombre").asString();
        
        productTypeHome.saveOrUpdate(new ProductType(nombre)) 

      }
    }
  }
}