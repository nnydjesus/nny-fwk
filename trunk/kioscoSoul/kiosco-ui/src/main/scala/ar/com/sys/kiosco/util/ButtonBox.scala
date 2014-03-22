package ar.com.sys.kiosco.util

import scala.swing.Dimension
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Orientation
import com.jgoodies.forms.builder.ButtonStackBuilder
import ar.edu.unq.tpi.base.utils.Path
import javax.swing.ImageIcon
import ar.com.sys.kiosco.app.KioscoAppUI


class ButtonBox(var kiosco: KioscoAppUI, any:Any) extends BoxPanel(Orientation.Vertical){
  
  
  def this(kiosco:KioscoAppUI) ={
    this(kiosco, null)
    
    this.maximumSize_=(new Dimension(200,400))
    this.minimumSize_=(new Dimension(100,300))
    
    var ventas= new Button("ventas") with FontSize with ButtonSize{
      icon_=(new ImageIcon(Path.path + "Images/sale.png"))
    }
    
    
    var searchProduct = new Button("productos")with FontSize with ButtonSize{
      icon_=(new ImageIcon(Path.path + "Images/products.png"))
    }
    
    var stack:ButtonStackBuilder = new ButtonStackBuilder()
    stack.addRelatedGap()
    stack.addRelatedGap()
    stack.addGridded(ventas.peer)
    stack.addRelatedGap()
    stack.addRelatedGap()
    stack.addGridded(searchProduct.peer)
    
    peer.add(stack.getContainer())
    
    ventas.peer.addActionListener(new MethodListener(this.kiosco.openSales))
    searchProduct.peer.addActionListener(new MethodListener(this.kiosco.openSearchProduct))
    
  }
  
}
	
trait ButtonSize extends Button{
  var dimension = new Dimension(250, 70)
  this.minimumSize = dimension
  this.preferredSize = dimension
  this.maximumSize = dimension
  
}