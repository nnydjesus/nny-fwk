package ar.com.sys.kiosco.app
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.EventQueue
import scala.swing.BorderPanel.Position
import scala.swing.BorderPanel
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel
import ar.com.sys.kiosco.abms.ABMProduct
import ar.com.sys.kiosco.abms.ABMProductType
import ar.com.sys.kiosco.menu.KioscoMenu
import ar.com.sys.kiosco.metric.GananciaPerTypeUI
import ar.com.sys.kiosco.search.SearchProduct
import ar.com.sys.kiosco.search.SearchProductType
import ar.com.sys.kiosco.util.ButtonBox
import ar.edu.unq.tpi.ui.swing.components.FrameLook
import ar.edu.unq.tpi.ui.swing.components.Preloader
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.JOptionPane
import ar.edu.unq.tpi.base.KioscoApplication

class KioscoAppUI(preloader: Preloader) extends FrameLook with Runnable {
  var contains: BorderPanel = null
  
  var buttonBox : ButtonBox = new ButtonBox(this)
  
  def this() = {
    this(null)
    this.setJMenuBar(new KioscoMenu(this));
    contains = new BorderPanel{
     add(buttonBox, Position.West) 
    }
    
    this.add(contains.peer)
//    this.setLoader(new Preloader(this, "Kiosco"))
    this.fullScreen();
    this.setVisible(false)

    this.addActions()

  }
  
  def addActions() = {
    this.addWindowListener(new WindowAdapter() {
      override def windowClosing(arg0: WindowEvent) {
        System.exit(0);
      }
    });
  }

  override def run() = {
    UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
    SwingUtilities.updateComponentTreeUI(this)
//    Thread.sleep(1000 * 10)
    this.setVisible(true)
    this.closeLoader()
  }
  
  def openSales() =  new SalesUI().open()
  
  def openAddProduct() = new ABMProduct().open()
  def openAddProductType() = new ABMProductType().open()
  
  def openSearchProduct() = new SearchProduct().open()
  def openSearchProductType() = new SearchProductType().open()
  def openGananciaPerType() =  new GananciaPerTypeUI().open()
  def infoMoneyInvestedAllProduct() {
    val message = "<html> <head> " +
    		"<style type='text/css'>" +
    		"h1 {color:red; font-size: 20; }" +
    		"</style> </head><body>" +
    		"Total de Dinero <h1>"+
    		KioscoApplication.kiosco.productHome.getMoneyInvestedProducts()+"<h1>" +
    		"</body></html>"
    		
   		JOptionPane.showMessageDialog(this, message , "Dinero Total Invetido" , JOptionPane.INFORMATION_MESSAGE);
	
  }
  
}

object KioscoAppUIMain {

  def run() {
    var main = new KioscoAppUI();
    EventQueue.invokeLater(main);
  }
  
  def main(ar:Array[String]){
    this.run();
  }

}