package ar.edu.unq.tpi.ui.swing.components.search;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import ar.edu.unq.tpi.base.utils.Path;
import ar.edu.unq.tpi.ui.swing.components.GeneralPanel;

public class TopPanel extends GeneralPanel {
	private static final long serialVersionUID = 1L;
	private JButton btImprimir;
	private JButton btCerrar;
	private JButton btcalculadora;

	public TopPanel() {
		// btImprimir = new JButton(new ImageIcon(Path.path()
		// + "Images/assignments.jpg"));
		// btCerrar = new JButton(new ImageIcon(Path.path() +
		// "Images/cancel.jpg"));
		// setCalculadora(new JButton(
		// new ImageIcon(Path.path() + "Images/calculadora.png")));
		// // this.add(btImprimir, BorderLayout.CENTER);
		// // this.add(btCerrar, BorderLayout.WEST);
		// // this.add(getCalculadora(), BorderLayout.EAST);
		//
		//
		// GroupLayout layout = new GroupLayout(this);
		// layout.setHorizontalGroup(
		// layout.createSequentialGroup()
		// .addComponent(btImprimir,50,50,50)
		// .addGap(10)
		// .addComponent(btCerrar,50,50,50)
		// .addGap(10)
		// .addComponent(getCalculadora(),50,50,50));
		// layout.setVerticalGroup(
		// layout.createParallelGroup(Alignment.TRAILING)
		// .addComponent(btImprimir,50,50,50)
		// .addGap(10)
		// .addComponent(btCerrar,50,50,50)
		// .addGap(10)
		// .addComponent(getCalculadora(),50,50,50));
		//
		// setLayout(layout);
		initComponents();
	}

	private void initComponents() {
		btImprimir = new JButton(new ImageIcon(Path.path()
				+ "Images/assignments.jpg"));
		btCerrar = new JButton(new ImageIcon(Path.path() + "Images/cancel.jpg"));
		setCalculadora(new JButton(new ImageIcon(Path.path()
				+ "Images/calculadora.png")));



		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 11,	12, 12));
		this.setPreferredSize(new java.awt.Dimension(1024, 68));


		GroupLayout mainPanelLayout = new GroupLayout(
				this);
		this.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				mainPanelLayout.createSequentialGroup()
						.addComponent(btCerrar,50, 50, 50)
						.addGap(600, 600, 600).addComponent(btImprimir,50, 50, 50).addGap(7, 7, 7)
						.addComponent(btcalculadora,50, 50, 50)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(btCerrar,50, 50, 50)
				.addComponent(btImprimir,50, 50, 50)
				.addComponent(btcalculadora,50, 50, 50));

	}
	public void setBtCerrar(JButton btCerrar) {
		this.btCerrar = btCerrar;
	}

	public JButton getBtCerrar() {
		return btCerrar;
	}

	public void setBtImprimir(JButton btImprimir) {
		this.btImprimir = btImprimir;
	}

	public JButton getBtImprimir() {
		return btImprimir;
	}

	public void setCalculadora(JButton calculadora) {
		this.btcalculadora = calculadora;
	}

	public JButton getCalculadora() {
		return btcalculadora;
	}

}
