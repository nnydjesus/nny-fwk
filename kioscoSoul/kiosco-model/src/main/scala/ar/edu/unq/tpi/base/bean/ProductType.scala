package ar.edu.unq.tpi.base.bean

import ar.edu.unq.tpi.base.persistence.PersistentObject
import javax.persistence.Basic
import javax.persistence.Entity
import ar.edu.unq.tpi.base.generator.annotations.DataGenerator
import ar.edu.unq.tpi.base.model.initialData.ProductTypeGenerator
import ar.edu.unq.tpi.base.utils.IdentificablePersistentObject
import org.uqbar.commons.utils.Transactional

object ProductTypeName {
  val CIGARRETTE = "Cigarrillos"
  val CANDIES = "Golocinas"
  val COOKIES = "Galletitas"
  val GASEOSA = "Gaseosa"
  val ALFAJORES = "Alfajores"
  val Varios = "Varios"
  val PERFUMERIA = "Perfumeria"
  val BAZAR = "Bazar"

}

object ProductTypeAtributes {
  val NAME = "name"

}

@Entity
@DataGenerator(value = classOf[ProductTypeGenerator], order = 1)
@Transactional
class ProductType extends IdentificablePersistentObject {

  @Basic var name: String = null

  def this(name: String) = {
    this()
    this.name = name
  }

  def getName = this.name
  def setName(value: String) = this.name = value

  override def toString(): String = this.name
  
  override def atributos():Array[String] = Array(ProductTypeAtributes.NAME)

}