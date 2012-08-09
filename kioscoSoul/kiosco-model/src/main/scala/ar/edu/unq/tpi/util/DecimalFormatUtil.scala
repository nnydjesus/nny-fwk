package ar.edu.unq.tpi.util
import java.text.DecimalFormat

object DecimalFormatUtil {

  var decimalformat = new DecimalFormat("#.##")
  decimalformat.setMaximumFractionDigits(2)
  decimalformat.setMinimumFractionDigits(2)
  
//  def format(number:Double):Double = decimalformat.parse(number.toString()).doubleValue()
    def format(number:Double):Double = number

}