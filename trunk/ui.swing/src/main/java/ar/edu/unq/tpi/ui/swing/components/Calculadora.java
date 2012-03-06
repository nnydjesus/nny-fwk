/*
 */
package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Calculadora extends CenteredJFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;

	private static final Calculadora INSTANCE = new Calculadora();
	
	private boolean nuevo = true;
	private float resultado_total = 0.0f;
	private String ultimo = "=";
	private Label pantalla = null;
	private JButton b;
	private JPanel panel, panel2;

	public Calculadora() {
		setTitle("Calculadora");
		Pantalla();
		Teclado();
		this.setSize(500, 400);
		this.centerWindow();
	}

	private void Pantalla() {

		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		pantalla = new Label();

		pantalla.setText("0");
		pantalla.setAlignment(Label.RIGHT);
		pantalla.setForeground(Color.black);
		pantalla.setFont(new Font("Verdana", Font.BOLD, 25));
		pantalla.setBackground(Color.white);
		pantalla.addKeyListener(this);

		panel.add(pantalla);
		add("North", panel);
	}

	public void Teclado() {
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(4, 4));

		addBoton("7", Color.blue);
		addBoton("8", Color.blue);
		addBoton("9", Color.blue);
		addBoton(KeyCode.DIV.toString(), Color.red);
		addBoton(KeyCode.DELETE.toString(), Color.red);

		addBoton("4", Color.blue);
		addBoton("5", Color.blue);
		addBoton("6", Color.blue);
		addBoton(KeyCode.MULT.toString(), Color.red);
		addBoton(KeyCode.SQRT.toString(), Color.red);

		addBoton("1", Color.blue);
		addBoton("2", Color.blue);
		addBoton("3", Color.blue);
		addBoton(KeyCode.MINUS.toString(), Color.red);
		addBoton(KeyCode.BACK.toString(), Color.red);

		addBoton("0", Color.blue);
		addBoton(KeyCode.SIGN.toString(), Color.red);
		addBoton(KeyCode.POINT.toString(), Color.red);
		addBoton(KeyCode.ADD.toString(), Color.red);
		addBoton(KeyCode.EQUAL.toString(), Color.red);

		add("Center", panel2);
	}

	private void addBoton(String n, Color color) {
		b = new JButton(n);

		b.setForeground(color);
		b.setFont(new Font("Verdana", Font.BOLD, 20));
		panel2.add(b);

		b.addKeyListener(this);
		b.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		action(event.getActionCommand());		
	}
	
	
	public void action(String digit) {

		String s = pantalla.getText();

		float valor = 0;
		try {
			valor = new Float(s).floatValue();
		} catch (Exception e) {
			if (!KeyCode.DELETE.equals(digit))
				return;
		}

		if (isNumber(digit)) {
			addNumber(digit, s);

		} else if (KeyCode.POINT.equals(digit)) {
			addPoint(digit, s);

		} else if (KeyCode.SQRT.equals(digit)) {
			sqrt(valor);

		} else if (KeyCode.SIGN.equals(digit)) {
			changeSign(valor);

		} else if (isDelete(digit)) {
			clean();
			
		} else {

			KeyCode c = KeyCode.valueOfString(ultimo);
			switch (c) {
			case EQUAL:
				resultado_total = valor;
				break;
			case ADD:
				resultado_total += valor;
				break;
			case MINUS:
				resultado_total -= valor;
				break;
			case MULT:
				resultado_total *= valor;
				break;
			case DIV:
				resultado_total /= valor;
				break;
			default:
				break;
			}
			ultimo = digit;
			nuevo = true;
			pantalla.setText(String.valueOf(resultado_total));
		}
	}

	public void addNumber(String digit, String s) {
		if (nuevo) {
			nuevo = false;
			pantalla.setText(digit);
		} else {
			pantalla.setText(s + digit);
		}
	}

	public void addPoint(String digit, String s) {
		if (nuevo) {
			nuevo = false;
			pantalla.setText("0.");
		} else {
			pantalla.setText(s + digit);
		}
	}

	public void sqrt(float valor) {
		valor = (float) Math.sqrt(valor);
		pantalla.setText(String.valueOf(valor));
		nuevo = true;
	}

	public void changeSign(float valor) {
		valor = -valor;
		pantalla.setText(String.valueOf(valor));
		nuevo = true;
	}

	public void clean() {
		resultado_total = 0;
		pantalla.setText("0");
		ultimo = "=";
		nuevo = true;
	}

	protected boolean isDelete(String digit) {
		return KeyCode.DELETE.equals(digit) || digit.equals('');
	}

	protected boolean isNumber(String digit) {
		return "0123456789".indexOf(digit) != -1;
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		e.consume();
		switch (e.getKeyChar()) {
		case KeyEvent.VK_BACK_SPACE:
			action(KeyCode.BACK.toString());
			break;
		case KeyEvent.VK_DELETE:
			action(KeyCode.DELETE.toString());
			break;
		case KeyEvent.VK_ENTER:
			action(KeyCode.EQUAL.toString());
			break;
		default:
			action(e.getKeyChar()+"");
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	public static void OPEN(){
    	INSTANCE.clean();
    	INSTANCE.setVisible(true);
	}

	public static void main(String[] args) {
		 new Calculadora().open();
	}
}