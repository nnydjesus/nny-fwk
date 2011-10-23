package ar.edu.unq.tpi.ui.swing.components;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ar.edu.unq.tpi.ui.swing.components.menu.styles.Menu;

public class FrameLook extends JFrame {

    private static final long serialVersionUID = 1L;

    protected LookAndFeel look;

    public FrameLook() {
        this.setJMenuBar(new Menu(this));
    }

    protected void setLoader(final Preloader loader) {
        loader.start();
    }

    public void setLook(final LookAndFeel look2) {
        look = look2;
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    FrameLook.this.updateUI();
                } catch (UnsupportedLookAndFeelException ex) {
                    new RuntimeException(ex);
                }
            }

        };
        SwingUtilities.invokeLater(runnable);
    }

    protected void updateUI() throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(look);
        SwingUtilities.updateComponentTreeUI(FrameLook.this.getParent());
    }

}
