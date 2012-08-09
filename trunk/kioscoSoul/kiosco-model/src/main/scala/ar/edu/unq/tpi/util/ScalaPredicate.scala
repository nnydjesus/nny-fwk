package ar.edu.unq.tpi.util
import org.apache.commons.collections15.Predicate


class ScalaPredicate[T](var method:(T)  => Boolean) extends Predicate[T] {
  
  override def evaluate(t:T):Boolean = method(t)

}