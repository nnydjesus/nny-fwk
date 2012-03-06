package ar.edu.unq.tpi.ui.swing.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import ar.edu.unq.tpi.base.utils.Path;

public class Preloader extends CenteredJFrame implements Runnable {
    private static final long serialVersionUID = 1L;

    private static final int ANCHO = 315, ALTO = 347;

    private ImageIcon fondo = null;

    private Image icono = null;

    private int progreso = 0;

    private Container jframe;

	private boolean close = false;

	private Graphics fGraphics;

//    @Override
//    public void paint(final Graphics fGraphics) {
//    		fGraphics.drawImage(fondo.getImage(), 0, 0, this);
//        fGraphics.setColor(Color.GREEN);
//        fGraphics.setFont(new Font("Arial", Font.BOLD, 19));
//        fGraphics.drawString("Cargando ...", ANCHO / 2 - 95, ALTO / 2);
//        fGraphics.fillRect(ANCHO / 2 - 100, ALTO / 2 + 30, progreso, 30);
//        fGraphics.setColor(Color.BLACK);
//        fGraphics.drawRect(ANCHO / 2 - 101, ALTO / 2 + 29, 201, 32);
//    }

    public Preloader() {
        this.loadImages();
        super.setIconImage(icono);
        JLabel background = new JLabel(fondo);
        background.setSize(ANCHO, ALTO);
        fondo.setImageObserver(background);
		this.add(background);
		super.setResizable(false);
		super.setUndecorated(true);
		this.setSize(ANCHO, ALTO);
		this.centerWindow();
		this.setVisible(true);
        this.start();
    }

    public Preloader(final String title) {
        this();
        this.setTitle(title);
    }

    protected String getLogo() {
        return Path.path() + "Images/logo.jpg";
    }

    public Preloader(final Container jframe, final String title) {
        this(title);
        this.setJframe(jframe);

    }

    public void run() {
        // AudioClip inicio = null;
        // try {
        // inicio = Applet.newAudioClip(new URL("file:"
        // + UICommonUtils.PATH_MUSIC + "inicio.wav"));
        // } catch (MalformedURLException e1) {
        // throw new RuntimeException(e1);
        // }
        // inicio.play();
//        while(!close){
//            try {
//            	repaint();
////            	this.paint(fGraphics);
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        this.getJframe().setVisible(true);
//        this.setVisible(false);
//        this.dispose();
    }
    
    @Override
    public void close() {
    	super.close();
    	this.close = true;
    }

    protected void loadImages() {
        fondo = new ImageIcon(this.getImageLoader());
        icono = new ImageIcon(this.iconImage()).getImage();
    }

    protected String getImageLoader() {
        return Path.path() + "Images/loader.gif";
    }

    public void start() {
        SwingUtilities.invokeLater(this);
    }

    public String iconImage() {
        return this.getLogo();
    }

    public void setJframe(final Container jframe) {
        this.jframe = jframe;
    }

    public Container getJframe() {
        return jframe;
    }

    public static void main(final String[] args) {
        new Preloader(null, "Sample");
    }
}
