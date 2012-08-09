package ar.edu.unq.tpi.base.bean
import util._
import ar.edu.unq.tpi.base.model.homes.ProductHome
import ar.edu.unq.tpi.base.persistence.transaction.UseCaseManager
import ar.edu.unq.tpi.base.Kiosco
import ar.edu.unq.tpi.util.ScalaUseCase
import java.util.Date

object Main {
  var kiosco = new Kiosco()

  def load() {
    kiosco.productHome.refresh();

    println("Tamanooo       " + kiosco.productHome.getAll().size());
    //       List.fromArray(home.getAll().toArray()).foreach(p=> p match{
    //         case pro: Product => println(pro.name) 
    //       }) 

    List.fromArray(kiosco.productHome.getProductsWithMinStock().toArray()).foreach(p => p match {
      case pro: Any => println(pro)
    })
  }

  def addSale() = {
    var sale = new Sale()
    var product = kiosco.productHome.getByCode("4")
    sale.addProduct(product, 5)

    product = kiosco.productHome.getByCode("2")
    sale.addProduct(product, 4)

    product = kiosco.productHome.getByCode("3")
    sale.addProduct(product, 10)

    kiosco.addSell(sale)

  }

  def ganancias() = {
    val ganancia = kiosco.cigaretteProfitsToday(new Date(), new Date())
    println("ganancias de cigarrillos " + ganancia)
  }

  def main(args: Array[String]) {
    UseCaseManager.execute(new ScalaUseCase("Add Sale ", addSale))
    ganancias()
  }

}