package ar.com.sys.kiosco.util
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.Frame
import javax.swing.JDialog
import javax.swing.JFormattedTextField
import java.text.NumberFormat
import javax.swing.JLabel
import java.awt.FlowLayout

class DialogConfirm(parent:Frame, modal:Boolean) extends JDialog(parent, modal) {
    
  var textField:JFormattedTextField = new JFormattedTextField(NumberFormat.getInstance());

    def this(parent:Frame) = {

        // padre y modal
        this(parent, true);
        setTitle("");
        textField.setColumns(11)
        this.setLayout(new FlowLayout())
        getContentPane().add(new JLabel("Cantidad: "))
        getContentPane().add(textField);

        // Se oculta la ventana al pulsar <enter> sobre el textfield
        textField.addActionListener(new ActionListener() {

            override def actionPerformed(event:ActionEvent)= {
                setVisible(false);
            }
        });
        this.setLocationRelativeTo(parent)
        this.setLocation(200, 150)
        this.setUndecorated(true)
    }

    def  getText():String = textField.getText();
}