package ar.edu.unq.tpi.base.model.homes

import ar.edu.unq.tpi.base.bean.ProductType
import ar.edu.unq.tpi.base.search.Home
import ar.edu.unq.tpi.util.ScalaPredicate
import org.apache.commons.collections15.Predicate

class ProductTypeHome extends Home[ProductType](classOf[ProductType], true) {

  override def getCriteria(productType: ProductType):Predicate[ProductType] =  {
    
    if(productType != null && productType.name != null && !productType.name.equals("")){
    	return new ScalaPredicate[ProductType](p => p.name.equals(productType.name))
    }else{
    	return getCriterioTodas()
    }
  }
  
 
  
}