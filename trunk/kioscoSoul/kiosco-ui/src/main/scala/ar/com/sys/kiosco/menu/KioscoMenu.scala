package ar.com.sys.kiosco.menu
import ar.com.sys.kiosco.app.KioscoAppUI
import ar.com.sys.kiosco.util.JFontSize
import ar.com.sys.kiosco.util.MethodListener
import ar.edu.unq.tpi.base.generator.DDLGenerator
import ar.edu.unq.tpi.ui.swing.components.menu.styles.Menu
import javax.swing.JMenu
import javax.swing.JMenuItem

class KioscoMenu(var kiosco: KioscoAppUI, any: Any) extends Menu(kiosco) with JFontSize {

  def this(kiosco: KioscoAppUI) = {
    this(kiosco, null)
    this.updateFont()
  }

  override def addActions() = {
    super.addActions()
    var altas = new JMenu("Altas") with JFontSize
    var contabilidad = new JMenu("Contabilidad") with JFontSize
    var ventas = new JMenu("Ventas") with JFontSize
    var busqueda = new JMenu("Busquedas") with JFontSize
    var generator = new JMenu("Generator") with JFontSize

    var addProducto = new JMenuItem("Nuevo Producto") with JFontSize;
    var addProductType = new JMenuItem("Nuevo tipo de producto") with JFontSize

    var searchProducto = new JMenuItem("Buscar Producto") with JFontSize;
    var searchProductType = new JMenuItem("Buiscar tipo de producto") with JFontSize

    var facturacion = new JMenuItem("Facturacion") with JFontSize
    var ddlGenerator = new JMenuItem("Generador de datos") with JFontSize

    var gananciaPerType = new JMenuItem("Ganancia Por tipo de producto") with JFontSize
    var dineroInvested = new JMenuItem("Dinero invertido Total de Productos") with JFontSize
    
    
    dineroInvested.addActionListener(new MethodListener(this.kiosco.infoMoneyInvestedAllProduct));
    
    addProducto.addActionListener(new MethodListener(this.kiosco.openAddProduct));
    addProductType.addActionListener(new MethodListener(this.kiosco.openAddProductType));

    searchProducto.addActionListener(new MethodListener(this.kiosco.openSearchProduct));
    searchProductType.addActionListener(new MethodListener(this.kiosco.openSearchProductType));

    facturacion.addActionListener(new MethodListener(this.kiosco.openSales))

    gananciaPerType.addActionListener(new MethodListener(this.kiosco.openGananciaPerType))
    ddlGenerator.addActionListener(new MethodListener(DDLGenerator.main))

    altas.add(addProducto)
    altas.add(addProductType)

    busqueda.add(searchProducto)
    busqueda.add(searchProductType)

    ventas.add(facturacion)

    contabilidad.add(gananciaPerType)
    contabilidad.add(dineroInvested)
    generator.add(ddlGenerator)

    this.add(ventas)
    this.add(altas)
    this.add(busqueda)
    this.add(contabilidad)
//    this.add(generator)
  }

}