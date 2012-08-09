package ar.edu.unq.tpi.base.model.homes

import java.util.Date
import java.util.List
import scala.collection.JavaConversions._
import org.hibernate.criterion.Projections
import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.bean.Sale
import ar.edu.unq.tpi.base.persistence.criteria.CriteriaHibernateImpl
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.base.bean.GananciaResultTransformer
import ar.edu.unq.tpi.base.bean.GananciaPerType

class SaleHome extends Home[Sale](classOf[Sale], false) {

  def getGananciaOfDay(date: Date) = repository.createCriteria()
    .add(new CriteriaHibernateImpl().greaterOrEqual("date", date))
    .addProjection(Projections.sum("total"))
    .buildProjections()
    .list()

  def getGananciaByTypeOfDay(desde: Date, hasta: Date):java.util.List[GananciaPerType] = {
    repository.createCriteria()
      		.add(new CriteriaHibernateImpl()
      				.between("date", desde, hasta))
      		.createCriteria("soldProducts", "sold")
      		.addProjection(Projections.sum("sold.totalSold"))
      		.addProjection(Projections.sum("sold.ganancia"))
      		.addProjection(Projections.groupProperty("sold.productType"))
      		.buildProjections()
      		.setResultTransformer(new GananciaResultTransformer())
     		.list()
  }

}