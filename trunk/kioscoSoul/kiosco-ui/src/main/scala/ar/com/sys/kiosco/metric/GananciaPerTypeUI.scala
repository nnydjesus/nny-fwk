package ar.com.sys.kiosco.metric
import java.awt.event.MouseEvent
import java.awt.Color
import java.awt.FlowLayout
import java.awt.Font
import java.util.ArrayList
import java.util.Date
import java.util.HashMap
import scala.swing.BoxPanel
import scala.swing.Orientation
import com.toedter.calendar.JDateChooser
import ar.com.sys.kiosco.util.JFontSize
import ar.com.sys.kiosco.util.MethodListener
import ar.edu.unq.tpi.base.bean.GananciaPerType
import ar.edu.unq.tpi.base.utils.Path
import ar.edu.unq.tpi.base.KioscoApplication
import ar.edu.unq.tpi.ui.swing.components.search.TopPanel
import ar.edu.unq.tpi.ui.swing.components.CenteredJFrame
import ar.edu.unq.tpi.ui.swing.components.FormBuilder
import ar.edu.unq.tpi.ui.swing.components.Generator
import ar.edu.unq.tpi.ui.swing.components.TableSelection
import javax.swing.table.JTableHeader
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JPanel
import ar.edu.unq.tpi.util.services.jasper.Report

class GananciaPerTypeUI(any: Any) extends CenteredJFrame with TableSelection[GananciaPerType] {

  var topPanel = new TopPanel()
  var builder = new FormBuilder()
  var table = Generator.GENERATE_TABLE(new ArrayList[GananciaPerType](), GananciaPerType.ATRIBUTES, this);
  var saleHome = KioscoApplication.kiosco.saleHome
  var buttonSearch = new JButton("Buscar") with JFontSize
  var buttonClean = new JButton("Limpiar") with JFontSize
//  var report = new Report[GananciaPerType]("ganancias")

  var desdeDate:JDateChooser = createDateChooser()
  var hastaDate:JDateChooser = createDateChooser()
  var reportParameters = new HashMap[String, String]();

  def this() {
    this(null)
    addContend()
    reportParameters.put("Title", "Ganancias");
    
    this.addActions()

    this.setSize(800, 700)
    centerWindow()

  }

  def addActions() {
    buttonSearch.addActionListener(new MethodListener(this.search))
    buttonClean.addActionListener(new MethodListener(this.clean))
    
    topPanel.getBtImprimir().addActionListener(new MethodListener(this.exportPDF))
    topPanel.getBtCerrar().addActionListener(new MethodListener(this.close))
  }

  def addContend() {
    var panel = new BoxPanel(Orientation.Vertical)
    var buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT))
    
    buttonSearch.setIcon(new ImageIcon(Path.path + "Images/search.png"))
    buttonClean.setIcon(new ImageIcon(Path.path + "Images/cancel.png"))
    
    buttonPanel.add(buttonSearch);
    buttonPanel.add(buttonClean);

    builder.append("Desde", desdeDate)
    builder.append("Hasta", hastaDate)

    panel.peer.add(topPanel)
    panel.peer.add(builder.build())
    panel.peer.add(buttonPanel)
    panel.peer.add(table)
    this.add(panel.peer)
  }

  def createDateChooser(): JDateChooser = {
    var date = new JDateChooser();
    date.setDateFormatString("dd/M/yyyy")

    var iconURL = date.getClass().getResource("/com/toedter/calendar/images/JMonthChooserColor32.gif")
    var icon = new ImageIcon(iconURL)
    date.setIcon(icon)
    date.setDate(new Date())
    return date
  }

  def search() {
    table.update(saleHome.getGananciaByTypeOfDay(desdeDate.getDate(), hastaDate.getDate()));
  }

  def clean() {
    table.update(new ArrayList[GananciaPerType]())
    desdeDate.setDate(null)
    hastaDate.setDate(null)
  }
  
  def exportPDF(){
//    report.exportPDF(Report.PATH, table.getData(), reportParameters)
  }

  override def selectedTable(event: MouseEvent) {

  }

  override def getFont() = new Font("Arial", Font.BOLD, 24)

  override def configureTableHeader(header: JTableHeader) = {
    header.setFont(new Font("Arial", Font.BOLD, 22))
    header.setForeground(Color.BLUE)
  }

}

object GananciaMain{
  
  def main(a:Array[String]){
    new GananciaPerTypeUI().open();
//    JasperReportBasic.main(null)
  }
  
}