package ar.edu.unq.tpi.ui.swing.components.autocomplete;

import java.awt.event.KeyEvent;

import javax.swing.text.Document;

public class KeyProcessingTextField extends LimeTextField {

    private static final long serialVersionUID = 1L;

    public KeyProcessingTextField() {
        super();
    }

    public KeyProcessingTextField(final String text) {
        super(text);
    }

    public KeyProcessingTextField(final int columns) {
        super(columns);
    }

    public KeyProcessingTextField(final String text, final int columns) {
        super(text, columns);
    }

    public KeyProcessingTextField(final Document doc, final String text, final int columns) {
        super(doc, text, columns);
    }

    // raise access
    @Override
    public void processKeyEvent(final KeyEvent e) {
        super.processKeyEvent(e);
    }
}