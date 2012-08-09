package ar.com.sys.kiosco.util

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

import ar.edu.unq.tpi.ui.swing.components.autocomplete.AutoCompleteTextField
import ar.edu.unq.tpi.base.common.Observable
import java.util.List
import scala.collection.JavaConversions._


class AutoCompleteListTextField[A](var property: String, var data: List[A], any: Any) extends AutoCompleteTextField {

  def this(property: String, data: List[A]) = {
    this(property, data, null)

    this.addListDataAutoComplete();
    setText("")
  }

  def addListDataAutoComplete() {
    data.foreach(p => {  p match{
      case o:Observable => this.addToDictionary(property, o) 
    }})
  }

}