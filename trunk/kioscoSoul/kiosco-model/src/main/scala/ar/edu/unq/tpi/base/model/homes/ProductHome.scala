package ar.edu.unq.tpi.base.model.homes
import java.util.List
import org.apache.commons.collections15.Predicate
import ar.edu.unq.tpi.base.bean.Product
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.persistence.criteria.CriteriaHibernateImpl
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.util.ScalaPredicate
import org.hibernate.criterion.Projections

class ProductHome extends Home[Product](classOf[Product], true) {

  def getByType(productType: ProductType) = repository.getsByProperty("productType", productType)

  def getProductsWithMinStock(): List[Any] = {
    return repository.createCriteria()
      .add(new CriteriaHibernateImpl().lessOrEqualProperty("stock", "minStock"))
      .list();
  }
  def getMoneyInvestedProducts():Double = {
   return repository.createCriteria()
      		.addProjection(Projections.sum("moneyInvested"))
      		.buildProjections()
      		.list().get(0)
    
  }
  def getByCode(code: String) = this.repository.getByProperty("code", code)
  
  
  override def getCriteria(product: Product): Predicate[Product] = {

      var code = product.code
      var name = product.name
      var pType = product.productType

      if (code != null && !code.equals("")) {
        return getCriterioByCode(product);
      }

      if (name != null && pType != null) {
        if (name.equals("")) {
          return getCriterioByType(product)
        } else {
          return getCriterioByNameAndType(product);
        }
      }

      if (name != null && !name.equals("")) {
        return getCriterioByName(product);
      }

      if (pType != null) {
        return getCriterioByType(product);
      }

      return getCriterioTodas();
  }

  def getCriterioByCode(product: Product): Predicate[Product] = new ScalaPredicate[Product](p => p.code.contains(product.code))

  def getCriterioByType(product: Product): Predicate[Product] = new ScalaPredicate[Product](p => product.productType.equals(p.productType))

  def getCriterioByName(product: Product): Predicate[Product] = new ScalaPredicate[Product](p => p.name.contains(product.name))

  def getCriterioByNameAndType(product: Product): Predicate[Product] = new ScalaPredicate[Product](p => {
    p.name.contains(product.name) && product.productType.equals(p.productType)
  })

}