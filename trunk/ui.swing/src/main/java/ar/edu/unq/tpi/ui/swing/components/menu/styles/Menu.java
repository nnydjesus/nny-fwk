package ar.edu.unq.tpi.ui.swing.components.menu.styles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;

import org.jvnet.substance.SubstanceLegacyDefaultLookAndFeel;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.skin.SubstanceMagellanLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel;
import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import org.jvnet.substance.skin.SubstanceSaharaLookAndFeel;
import org.jvnet.substance.skin.SubstanceTwilightLookAndFeel;

import ar.edu.unq.tpi.ui.swing.components.FrameLook;

public class Menu extends JMenuBar {
    private static final long serialVersionUID = 1L;

    private JMenu menuconfiguracion;

    private JMenu aparienciaItem;

    private JMenuItem lookTwilight;

    private JMenuItem lookLegacy;

    private JMenuItem lookBusinessBlackSteel;

    private JMenuItem lookEmeraldDus;

    private JMenuItem lookRavenGraphiteGlass;

    private FrameLook parent;

    private SubstanceLookAndFeel substanceTwilightLookAndFeel;

    private SubstanceLookAndFeel substanceLegacyDefaultLookAndFeel;

    private SubstanceLookAndFeel substanceRavenGraphiteGlassLookAndFeel;

    private SubstanceLookAndFeel substanceBusinessBlackSteelLookAndFeel;

    private SubstanceLookAndFeel substanceEmeraldDuskLookAndFeel;

    private JMenuItem lookMagellan;

    private SubstanceLookAndFeel substanceSaharaLokAndFeel;

    private SubstanceMagellanLookAndFeel substanceMagellanLokAndFeel;

    private SubstanceMagmaLookAndFeel substanceMagmaLokAndFeel;

    private JMenuItem lookMagma;

    private JMenuItem lookSahara;

    public Menu(final FrameLook parent) {
        this.parent = parent;
        menuconfiguracion = new JMenu("Configuracion");
        aparienciaItem = new JMenu("Apariencia");
        lookBusinessBlackSteel = new JMenuItem("BusinessBlackSteel");
        lookEmeraldDus = new JMenuItem("EsmeraldDus");
        lookLegacy = new JMenuItem("Legacy");
        lookRavenGraphiteGlass = new JMenuItem("RavenGraphiteGlass");
        lookTwilight = new JMenuItem("Twilight");
        lookMagellan = new JMenuItem("Magellan");
        lookMagma = new JMenuItem("Magma");
        lookSahara = new JMenuItem("Sahara");
        substanceBusinessBlackSteelLookAndFeel = new SubstanceBusinessBlackSteelLookAndFeel();
        substanceEmeraldDuskLookAndFeel = new SubstanceEmeraldDuskLookAndFeel();
        substanceLegacyDefaultLookAndFeel = new SubstanceLegacyDefaultLookAndFeel();
        substanceRavenGraphiteGlassLookAndFeel = new SubstanceRavenGraphiteGlassLookAndFeel();
        substanceTwilightLookAndFeel = new SubstanceTwilightLookAndFeel();
        substanceMagmaLokAndFeel = new SubstanceMagmaLookAndFeel();
        substanceMagellanLokAndFeel = new SubstanceMagellanLookAndFeel();
        substanceSaharaLokAndFeel = new SubstanceSaharaLookAndFeel();

        this.init();
        this.addActions();
        // lookBusinessBlackSteel.doClick();

    }

    private void init() {
        menuconfiguracion.add(aparienciaItem);
        aparienciaItem.add(lookBusinessBlackSteel);
        aparienciaItem.add(lookEmeraldDus);
        aparienciaItem.add(lookLegacy);
        aparienciaItem.add(lookRavenGraphiteGlass);
        aparienciaItem.add(lookTwilight);
        aparienciaItem.add(lookMagellan);
        aparienciaItem.add(lookMagma);
        aparienciaItem.add(lookSahara);
        
        updateFont();
        
        this.add(menuconfiguracion);
    }

	protected void updateFont() {
		lookBusinessBlackSteel.setFont(this.getFont());
        aparienciaItem.setFont(this.getFont());
        lookEmeraldDus.setFont(this.getFont());
        lookLegacy.setFont(this.getFont());
        lookRavenGraphiteGlass.setFont(this.getFont());
        lookTwilight.setFont(this.getFont());
        lookMagellan.setFont(this.getFont());
        lookMagma.setFont(this.getFont());
        lookSahara.setFont(this.getFont());
        menuconfiguracion.setFont(this.getFont());
        aparienciaItem.setFont(this.getFont());
	}

    public void addActions() {
        lookBusinessBlackSteel.addActionListener(new AparienciaListener(substanceBusinessBlackSteelLookAndFeel));
        lookEmeraldDus.addActionListener(new AparienciaListener(substanceEmeraldDuskLookAndFeel));
        lookLegacy.addActionListener(new AparienciaListener(substanceLegacyDefaultLookAndFeel));
        lookRavenGraphiteGlass.addActionListener(new AparienciaListener(substanceRavenGraphiteGlassLookAndFeel));
        lookTwilight.addActionListener(new AparienciaListener(substanceTwilightLookAndFeel));
        lookSahara.addActionListener(new AparienciaListener(substanceSaharaLokAndFeel));
        lookMagellan.addActionListener(new AparienciaListener(substanceMagellanLokAndFeel));
        lookMagma.addActionListener(new AparienciaListener(substanceMagmaLokAndFeel));

    }

    class AparienciaListener implements ActionListener {

        private LookAndFeel look;

        public AparienciaListener(final LookAndFeel look) {
            this.look = look;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            parent.setLook(look);
        }
    }

}
